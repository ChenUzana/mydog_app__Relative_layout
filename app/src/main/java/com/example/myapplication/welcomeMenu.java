package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcomeMenu extends AppCompatActivity {

    Button report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomemenu);



        findViewById(R.id.tripReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomeMenu.this, TripReport.class);
                startActivity(intent);
                // finish();
            }
        });


        findViewById(R.id.dogGardenB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomeMenu.this, DogsGarden.class);
                startActivity(intent);
                // finish();
            }
        });




    }
}
