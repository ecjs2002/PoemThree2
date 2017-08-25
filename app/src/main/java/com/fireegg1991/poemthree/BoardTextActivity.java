package com.fireegg1991.poemthree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BoardTextActivity extends AppCompatActivity {

    public static final String UPDATE_DB = "http://ecjs2002.dothome.co.kr/ThreePoem/updateDB.php";
    public static final String LOAD_DB_LIKE = "http://ecjs2002.dothome.co.kr/ThreePoem/loadDBLike.php";
    TextView word1, word2, word3;
    TextView tv1, tv2, tv3;
    TextView ans1, ans2, ans3;
    ImageView like;
    TextView likeCount;
    Item item;
    int likeNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_text);
        con(0);






        item = getIntent().getParcelableExtra("testdata");
        참조();
        값설정();



    }

    public void 참조() {
        like = (ImageView) findViewById(R.id.like);
        word1 = (TextView) findViewById(R.id.word1);
        word2 = (TextView) findViewById(R.id.word2);
        word3 = (TextView) findViewById(R.id.word3);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        ans1 = (TextView) findViewById(R.id.ans1);
        ans2 = (TextView) findViewById(R.id.ans2);
        ans3 = (TextView) findViewById(R.id.ans3);
        likeCount = (TextView) findViewById(R.id.like_count);


    }

    public void 값설정() {
//        ans1.setText(item.getCon1());
//        ans2.setText(item.getCon2());
//        ans3.setText(item.getCon3());

        word1.setText(item.getTitle().charAt(0) + "");
        word2.setText(item.getTitle().charAt(1) + "");
        word3.setText(item.getTitle().charAt(2) + "");

        tv1.setText(item.getTitle().charAt(0) + "");
        tv2.setText(item.getTitle().charAt(1) + "");
        tv3.setText(item.getTitle().charAt(2) + "");


        //좋아요가 눌려져있냐?
        if(getPreferences(item.num+";")){
            like.setImageResource(R.drawable.like);
        }else {
            like.setImageResource(R.drawable.hate2);
        }


    }

    public void clickAns1(View v) {
        ans1.setText(item.getCon1());
        //ans1.setVisibility(View.VISIBLE);
    }

    public void clickAns2(View v) {
        ans2.setText(item.getCon2());
        //ans2.setVisibility(View.VISIBLE);
    }

    public void clickAns3(View v) {
        ans3.setText(item.getCon3());
        //ans3.setVisibility(View.VISIBLE);

    }

    public void clickLike(View v) {

        //이즈 라이크의 값을 불러와서 확인
        //근데 이즈라이크에 대한 키값이 없으면 리턴하는 값이 false
        //만약 있다면 있는값 리턴
        boolean isLike = getPreferences(item.num+";");

        if (isLike == false) {
            //클릭했는데 클릭이 되어있지 않은 상황 이때는 플러스해주고 is값을 트루로 바꿔준다
            Toast.makeText(this, "좋아요!", Toast.LENGTH_SHORT).show();
            savePreferences(item.num+";",!isLike);
            con(1);
            like.setImageResource(R.drawable.like);


        } else {
            Toast.makeText(this, "좋아요 취소", Toast.LENGTH_SHORT).show();
            con(-1);
            savePreferences(item.num+";",!isLike);
            like.setImageResource(R.drawable.hate2);

        }

        //클릭시 내 id값을 php 문서로 가져가 값을 비교해 라이크값을 얻어 오고 1을 더해서 업데이트
    }

    public void con(final int myLike) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(UPDATE_DB);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    String data = "id=" + item.getId() + "&title=" +item.getTitle()+"&like="+myLike;
                    OutputStream os = connection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();



                    InputStream is= connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);
                    String line= reader.readLine();

                    likeNum=Integer.parseInt(line);

                    Log.i("asd",line+"");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            likeCount.setText(""+likeNum);
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

    // 값 불러오기
    public boolean getPreferences(String s){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getBoolean(s,false);
    }

    // 값 저장하기
    public void savePreferences(String s,boolean isLike){

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(s,isLike);
        editor.commit();
    }

    public void clickBack(View v){
        Intent intent= new Intent(this, BoardActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickNext(View v){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }





}
