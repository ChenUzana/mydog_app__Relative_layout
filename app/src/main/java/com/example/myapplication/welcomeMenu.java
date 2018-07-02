package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class welcomeMenu extends AppCompatActivity {

    Button report;
    TextView welhead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomemenu);


        welhead=(TextView)findViewById(R.id.welcomehead);

        SharedPreferences sp=getSharedPreferences("key",0);
        String tv=sp.getString("textvalue","");
        welhead.setText("Welcome "+tv+" !");




        findViewById(R.id.tripReport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomeMenu.this, TripReport.class);
                startActivity(intent);
                // finish();
            }
        });

        findViewById(R.id.today_reports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomeMenu.this, TodayReport.class);
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

        findViewById(R.id.Food_Report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomeMenu.this, food_report.class);
                startActivity(intent);
                // finish();
            }
        });

        findViewById(R.id.dogidbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomeMenu.this, dogid_page.class);
                startActivity(intent);
                // finish();
            }
        });




    }
}
