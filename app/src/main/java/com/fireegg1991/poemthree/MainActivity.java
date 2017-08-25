package com.fireegg1991.poemthree;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    char[] strings= new char[3];

    Random random= new Random();

    TextView tv1, tv2, tv3;
    TextView word1, word2, word3;
    TextView ans1, ans2, ans3;
    int num;

    int count=-1;

    GameThread gameThread;
    TextToSpeech tts;

    String utteranceId=this.hashCode() + "";

    Intent intent;
    SpeechRecognizer speechRecognizer;
    public static String INSERT_DB="http://ecjs2002.dothome.co.kr/ThreePoem/insertDB.php";
    public static String GET_NUM="http://ecjs2002.dothome.co.kr/ThreePoem/GetNum.php";
    SoundPool soundPool;
    int countdown;
    float vol=0.05f;

    ImageView btnRe, btnUp;
    ImageView ivback, ivgamego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(listener);

        tv1= (TextView) findViewById(R.id.word1);
        tv2= (TextView) findViewById(R.id.word2);
        tv3= (TextView) findViewById(R.id.word3);

        word1= (TextView) findViewById(R.id.tv1);
        word2= (TextView) findViewById(R.id.tv2);
        word3= (TextView) findViewById(R.id.tv3);

        ans1= (TextView) findViewById(R.id.ans1);
        ans2= (TextView) findViewById(R.id.ans2);
        ans3= (TextView) findViewById(R.id.ans3);

        btnRe= (ImageView) findViewById(R.id.btn_Re);
        btnUp= (ImageView) findViewById(R.id.btn_Up);

        Glide.with(this).load(R.drawable.newflower).into(btnRe);
        Glide.with(this).load(R.drawable.uploadflower).into(btnUp);

        btnRe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    btnRe.setColorFilter(0xaaFF7043);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btnRe.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        btnUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    btnUp.setColorFilter(0xaaFF7043);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btnUp.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        ivback= (ImageView) findViewById(R.id.iv_back);
        Glide.with(this).load(R.drawable.iv_menu).into(ivback);

        ivgamego= (ImageView) findViewById(R.id.iv_gamego);
        Glide.with(this).load(R.drawable.iv_gamego).into(ivgamego);

        ivback.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    ivback.setColorFilter(0xaaD500F9);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    ivback.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        ivgamego.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    ivgamego.setColorFilter(0xaaD500F9);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    ivgamego.setColorFilter(0x00000000);
                }

                return false;
            }
        });




        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        soundPool= new SoundPool(1, AudioManager.STREAM_ALARM, 0);
        countdown=soundPool.load(this, R.raw.countdown, 1);

        new Thread(){
            @Override
            public void run() {
                try {
                    FileInputStream fis= openFileInput("words.json");
                    BufferedReader bf= new BufferedReader(new InputStreamReader(fis));
                    String line= bf.readLine();
                    StringBuffer buffer= new StringBuffer();
                    while (line!=null){
                        buffer.append(line);
                        line= bf.readLine();
                    }
                    JSONObject jsonObject= new JSONObject(buffer.toString());
                    JSONArray jsonArray= jsonObject.getJSONArray("words");
                    String word= jsonArray.getString(random.nextInt(jsonArray.length()));
                    for(int i=0; i<3; i++){
                        strings[i]= word.charAt(i);
                    }

                } catch (FileNotFoundException e) {
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public void clickStart(View v){
        if (gameThread==null){
            gameThread= new GameThread();
            gameThread.start();
            ivgamego.setVisibility(View.INVISIBLE);
            ivback.setVisibility(View.INVISIBLE);


        }
    }

    RecognitionListener listener= new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d("MIC", "준비");
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

            Log.d("MIC", "끝");
            count++;

            gameThread.unpauseThread();
        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> result = results.getStringArrayList(key);
            String[] answer = new String[result.size()];
            result.toArray(answer);

            switch (count){
                case 0: ans1.setText(answer[0]);
                    Log.d("ANSWER", answer[0]);
                    break;
                case 1: ans2.setText(answer[0]);
                    Log.d("ANSWER", answer[0]);
                    break;
                case 2: ans3.setText(answer[0]);
                    Log.d("ANSWER", answer[0]);
                    break;
            }

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };

    class GameThread extends Thread{


        boolean isPaused=false;
        public GameThread() {
            //soundPool.play(countdown, vol, vol, 1, 0, 1);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv1.setText(strings[0]+"");
                    tv2.setText(strings[1]+"");
                    tv3.setText(strings[2]+"");
                }
            });
        }

        @Override
        public void run() {


            try {sleep(3000);} catch (InterruptedException e) {
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {word1.setText(strings[0]+"");}
            });
            tts.speak(strings[0]+"", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    speechRecognizer.startListening(intent);
                }
            });

            pauseThread();


            try {sleep(2000);} catch (InterruptedException e) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {word2.setText(strings[1]+"");}
            });
            tts.speak(strings[1]+"", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    speechRecognizer.startListening(intent);
                }
            });
            pauseThread();


            try {sleep(2000);} catch (InterruptedException e) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {word3.setText(strings[2]+"");}
            });
            tts.speak(strings[2]+"", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    speechRecognizer.startListening(intent);
                }
            });
            pauseThread();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //요기 이미지뷰로 수정
                    btnRe.setVisibility(View.VISIBLE);
                    btnUp.setVisibility(View.VISIBLE);
                }
            });

        }

        public void pauseThread(){

            synchronized (this){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void unpauseThread(){
            synchronized (this){
                notify();
            }
        }

//        public void exit(){
//            return;
//        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        tts.shutdown();
        speechRecognizer.destroy();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null){
            tts.stop();
            tts = null;
        }
        if(speechRecognizer != null){
            speechRecognizer.stopListening();
            speechRecognizer = null;
        }
    }

    public int getNum(){

        myInsertDB();
//        new Thread(){
//            @Override
//            public void run() {
//                URL url= null;
//                try {
//
//                    url = new URL(GET_NUM);
//                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setDoInput(true);
//                    conn.setUseCaches(false);
//                    InputStream is= conn.getInputStream();
//                    InputStreamReader isr= new InputStreamReader(is);
//                    BufferedReader reader= new BufferedReader(isr);
//                    String line= reader.readLine();
//                    num=Integer.parseInt(line);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (ProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                finally {
//
//                }
//
//
//
//
//
//
//            }
//        }.start();

        return 0;
    }

    public void myInsertDB(){

        new Thread(){
            @Override
            public void run() {
                try {
                    URL url= new URL(INSERT_DB);

                    HttpURLConnection conn= (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);


                    String d= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//                    int date=Integer.parseInt(d);


                    String data= "num="+num+
                            "&title="+strings[0]+strings[1]+strings[2]+
                            "&name="+LoginActivity.USER_NAME+
                            "&date="+d+
                            "&id="+LoginActivity.USER_ID+
                            "&con1="+ans1.getText()+
                            "&con2="+ans2.getText()+
                            "&con3="+ans3.getText();

                    OutputStream os= conn.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();


                    InputStream is= conn.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    final StringBuffer buffer= new StringBuffer();
                    String line= reader.readLine();
                    while (line!=null){
                        buffer.append(line);
                        line= reader.readLine();
                    }
                    Log.i("asd",buffer.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    public void clickRetry(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    public void clickUpload(View v){

        //업로드 버튼 한번
        if(ans1.getText().equals("")||ans2.getText().equals("")||ans3.getText().equals("")){
            Toast.makeText(this, "3행시를 전부 입력 해주세요", Toast.LENGTH_SHORT).show();
        }else {

            makeProgress();
            getNum();
            Intent intent= new Intent(this, BoardActivity.class);
            startActivity(intent);
            finish();

        }



    }

    public void makeProgress(){

    }

    public void clickBack(View v){
        Intent intent= new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickNext(View v){
        Intent intent= new Intent(this, BoardActivity.class);
        startActivity(intent);
        finish();

    }




}
