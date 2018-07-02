package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class dogid_page extends AppCompatActivity {

LinearLayout linidchange,lindogcreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogid_page);

    linidchange=(LinearLayout)findViewById(R.id.linid);
        lindogcreate=(LinearLayout)findViewById(R.id.lincreate);


    }


    public void changedog(View v){

        linidchange.setVisibility(View.VISIBLE);
        lindogcreate.setVisibility(View.GONE);

    }

    public void createdog(View v){

        lindogcreate.setVisibility(View.VISIBLE);
        linidchange.setVisibility(View.GONE);

    }
}
