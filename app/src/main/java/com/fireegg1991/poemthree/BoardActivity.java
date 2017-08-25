package com.fireegg1991.poemthree;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Item> items= new ArrayList<>();
    RecyclerAdapter adapter;
    public static final String TASK_DB="http://ecjs2002.dothome.co.kr/ThreePoem/loadDBTask.php";
    LoadDBTask dbTask;

    ImageView back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbTask=new LoadDBTask();
        try {
            dbTask.execute(new URL(TASK_DB));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_board);

        back= (ImageView) findViewById(R.id.iv_back);
        next= (ImageView) findViewById(R.id.iv_next);

        Glide.with(this).load(R.drawable.iv_rainbow_left).into(back);
        Glide.with(this).load(R.drawable.iv_rainbow_right).into(next);

        recyclerView= (RecyclerView) findViewById(R.id.recycler);

        adapter= new RecyclerAdapter(items, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    back.setColorFilter(0xaaFF5722);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    back.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        next.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    next.setColorFilter(0xaaFF5722);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    next.setColorFilter(0x00000000);
                }

                return false;
            }
        });

    }

    public void clickNext(View v){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, 0);
        finish();


    }

    public void clickBack(View v){
        Intent intent= new Intent(this, MenuActivity.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.fade_out, 0);
        finish();
    }

    public void updateRecycler(Item item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }

    public void textAct(Item item){
        Intent intent=new Intent(this,BoardTextActivity.class);

        intent.putExtra("testdata", item);

        startActivity(intent);
        finish();

    }


    public class LoadDBTask extends AsyncTask<URL,Item,Void> {



        @Override
        protected Void doInBackground(URL... params) {
            try {
                HttpURLConnection connections = (HttpURLConnection) params[0].openConnection();
                connections.setRequestMethod("POST");
                connections.setDoInput(true);
                connections.setUseCaches(false);


                InputStreamReader isr=new InputStreamReader(connections.getInputStream());
                BufferedReader reader=new BufferedReader(isr);

                String line =reader.readLine();
                StringBuffer buffer=new StringBuffer();


                while (line!=null){

                    String[]strings=line.split(";");
                    Item item=new Item();
                    item.setDate(strings[6]);
                    item.setNum(Integer.parseInt(strings[0]));
                    item.setTitle(strings[1]);
                    item.setUserName(strings[5]);
                    item.setCon1(strings[2]);
                    item.setCon2(strings[3]);
                    item.setCon3(strings[4]);
                    item.setId(Long.parseLong(strings[7]));


                    publishProgress(item);

                    line=reader.readLine();

                }



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Item... values) {
            Log.i("asd","업데이트안");
            updateRecycler(values[0]);
            super.onProgressUpdate(values);
        }


    }

}

