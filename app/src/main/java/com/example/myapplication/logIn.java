package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class logIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findViewById(R.id.loginb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText t1= (EditText)findViewById(R.id.input);

                SharedPreferences sp=getSharedPreferences("key",0);
                SharedPreferences.Editor sedt=sp.edit();
                sedt.putString("textvalue",t1.getText().toString());
                sedt.commit();

                Toast.makeText(logIn.this,"u clicked :"+t1.getText().toString(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(logIn.this, takephoto.class);
                startActivity(intent);
               // finish();

            }
        });

        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this, welcomeMenu.class);

                startActivity(intent);
                // finish();
            }
        });

        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this, Signup.class);

                startActivity(intent);
                // finish();
            }
        });



        findViewById(R.id.newuserB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this, Signup.class);
                startActivity(intent);
                // finish();
            }
        });


        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this, TripReport.class);
                startActivity(intent);
                // finish();
            }
        });

        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this, TodayReport.class);
                startActivity(intent);
                // finish();
            }
        });

        findViewById(R.id.loginb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this, welcomeMenu.class);
                startActivity(intent);
                // finish();
            }
        });




    }
}
