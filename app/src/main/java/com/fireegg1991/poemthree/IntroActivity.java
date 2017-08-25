package com.fireegg1991.poemthree;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alfo6-17 on 2017-07-20.
 */

public class IntroActivity extends AppCompatActivity {

    ImageView iv;

    Timer timer = new Timer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        iv = (ImageView) findViewById(R.id.logo_iv);

        Animation ani = AnimationUtils.loadAnimation(this, R.anim.appear_logo);

        iv.startAnimation(ani);
        테스트();

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 10);
        } else {

            timer.schedule(task, 2500);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                timer.schedule(task, 2500);
            } else {
                finish();

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            try {
                URL url = new URL("http://ecjs2002.dothome.co.kr/ThreePoem/words.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = bf.readLine();
                StringBuffer buffer = new StringBuffer();
                while (line != null) {
                    buffer.append(line);
                    line = bf.readLine();
                }
                connection.disconnect();

                Log.d("JSON", buffer.toString());
                FileOutputStream fos = openFileOutput("words.json", MODE_PRIVATE);
                PrintWriter pw = new PrintWriter(fos);
                pw.print(buffer.toString());
                pw.flush();
                pw.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }
    };

    public void 테스트() {

        try {
            PackageInfo Info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : Info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("키", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {


        }
    }
}



























