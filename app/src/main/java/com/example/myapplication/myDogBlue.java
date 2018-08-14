package com.example.myapplication;

import android.content.Intent;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class myDogBlue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydogblue);

        final MediaPlayer dogbark=MediaPlayer.create(this,R.raw.dogbarkinshort);
        final MediaPlayer dogbark2=MediaPlayer.create(this,R.raw.wh);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                       // dogbark2.start(); //sound on

                        wait(3000);  //time that shows the photo

                            Intent intent = new Intent(myDogBlue.this, logIn.class);
                            startActivity(intent);
                            finish();
                        }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
