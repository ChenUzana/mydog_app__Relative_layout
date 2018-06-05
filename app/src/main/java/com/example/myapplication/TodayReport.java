package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodayReport extends AppCompatActivity {

    Button gomenu;
    TextView todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_reports);

        todayDate=(TextView)findViewById(R.id.todayDateHere);

        String c = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        todayDate.setText(c);


        findViewById(R.id.gotomenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TodayReport.this, welcomeMenu.class);
                startActivity(intent);
                // finish();
            }
        });


    }


}
