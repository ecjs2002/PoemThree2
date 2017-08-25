package com.fireegg1991.poemthree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class MenuActivity extends AppCompatActivity {

    ImageView tuto, gamego, board, glory;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        tuto= (ImageView) findViewById(R.id.iv_tuto);
        gamego= (ImageView) findViewById(R.id.iv_gamego);
        board= (ImageView) findViewById(R.id.iv_board);
        glory= (ImageView) findViewById(R.id.iv_glory);



        Glide.with(this).load(R.drawable.iv_tuto).into(tuto);
        Glide.with(this).load(R.drawable.iv_gamego).into(gamego);
        Glide.with(this).load(R.drawable.iv_board).into(board);
        Glide.with(this).load(R.drawable.iv_glory).into(glory);

        tuto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    tuto.setColorFilter(0xaa1A237E);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    tuto.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        gamego.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    gamego.setColorFilter(0xaa1A237E);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    gamego.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    board.setColorFilter(0xaa1A237E);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    board.setColorFilter(0x00000000);
                }

                return false;
            }
        });

        glory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    glory.setColorFilter(0xaa1A237E);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    glory.setColorFilter(0x00000000);
                }

                return false;
            }
        });

    }






    public void clickTutorial(View v){
        Intent intent= new Intent(this, TutorialActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.viewchange, 0);
        finish();


    }

    public void clickBoard(View v){
        Intent intent= new Intent(this, BoardActivity.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.fade_in, 0);
        finish();

    }

    public void clickGlory(View v){
        Toast.makeText(this, "서비스 준비중 입니다...", Toast.LENGTH_SHORT).show();
    }

    public void clickGo(View v){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        //overridePendingTransition(android.R.anim.slide_in_left, 0);
        finish();

    }

}
