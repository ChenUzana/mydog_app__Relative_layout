package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TripReport extends AppCompatActivity {

    Button today;
    EditText todaydate,todaytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_report);

        todaydate=(EditText) findViewById(R.id.tripDate);
        todaytime=(EditText) findViewById(R.id.tripHour);


        findViewById(R.id.todayB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                todaydate.setText(c.toString());
            }
        });


        findViewById(R.id.nowB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date currentTime = Calendar.getInstance().getTime();

                int min=Calendar.getInstance().getTime().getMinutes();
                int hou=Calendar.getInstance().getTime().getHours();

                String time=hou+":"+min;

                todaytime.setText(time);
            }
        });





    }
}
