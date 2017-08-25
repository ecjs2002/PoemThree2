package com.fireegg1991.poemthree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class TutorialActivity extends AppCompatActivity {

    ImageView tuto;
    ImageView back, next;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);

        back= (ImageView) findViewById(R.id.iv_back);
        next= (ImageView) findViewById(R.id.iv_next);

        Glide.with(this).load(R.drawable.iv_rainbow_left).into(back);
        Glide.with(this).load(R.drawable.iv_rainbow_right).into(next);

        tuto= (ImageView) findViewById(R.id.tuto_img);
        if(tuto == null){
            Toast.makeText(this, "NULL...", Toast.LENGTH_SHORT).show();
        }else Glide.with(this).load(R.drawable.tutorial).into(tuto);

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    back.setColorFilter(0xaaFFEB3B);

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
                    next.setColorFilter(0xaa03A9F4);

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
        overridePendingTransition(R.anim.viewchange_out, 0);
        finish();

    }

}
