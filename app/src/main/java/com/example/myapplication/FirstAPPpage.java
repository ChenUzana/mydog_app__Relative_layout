package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstAPPpage extends AppCompatActivity {

    Button gomenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_apppage);


        //gomenu=(Button)findViewById(R.id.gotomenu);


        findViewById(R.id.gotomenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstAPPpage.this, MainActivity.class);
                startActivity(intent);
                // finish();
            }
        });


    }


}
