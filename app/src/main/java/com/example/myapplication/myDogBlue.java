package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class myDogBlue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydogblue);

        final MediaPlayer dogbark=MediaPlayer.create(this,R.raw.dogbarkinshort);

      //  SharedPreferences sp=getSharedPreferences("key",0);
      //  String usernameSP=sp.getString("userSP","");
      //  String nameSP=sp.getString("nameSP","");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                     //   dogbark.start(); //sound on

                        wait(2000);  //time that shows the photo

                      /*  if(!(usernameSP.equals(null))){

                            Intent intent = new Intent(myDogBlue.this, welcomeMenu.class);
                            startActivity(intent);
                            finish();

                        }
                            else{ */

                            Intent intent = new Intent(myDogBlue.this, logIn.class);
                            startActivity(intent);
                            finish();
                        }

               //     }  'else' closing

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
