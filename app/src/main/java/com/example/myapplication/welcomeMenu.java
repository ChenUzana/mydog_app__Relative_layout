package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class welcomeMenu extends AppCompatActivity {

    TextView welhead,weldog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;

    Button addReportB,todayRportB,dogGardenB,dogIdB,theShopB,commB,dicoB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomemenu);


        sp=getSharedPreferences("key",0);
        SPeditor=sp.edit();

        addReportB=(Button)findViewById(R.id.tripReport);
        todayRportB=(Button)findViewById(R.id.today_reports);
        dogGardenB=(Button)findViewById(R.id.dogGardenB);
        dogIdB=(Button)findViewById(R.id.dogidbutton);
        theShopB=(Button)findViewById(R.id.buyFood);
        commB=(Button)findViewById(R.id.Community);
        dicoB=(Button)findViewById(R.id.disconnectB);

        if(sp.getString("appLang","").equals("heb")){ goHeb(); }
        else if(sp.getString("appLang", "").equals("eng")){ goeng(); }

        updateSP();

        welhead=(TextView)findViewById(R.id.welcomehead);
        weldog=(TextView)findViewById(R.id.dogIs);

        getname();  //sets the current user name in the head title

    }  // end of on create


    public void updateSP(){

        db.collection("Users").document(sp.getString("emailSP",""))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {

                  //  Toast.makeText(welcomeMenu.this, "update SP from welcome page ", Toast.LENGTH_LONG).show();

                    //SP save only if the database check is true
                    SPeditor.putString("firstnameSP", documentSnapshot.getString("Firstname"));  //firstname save in sp
                    SPeditor.putString("lastnameSP", documentSnapshot.getString("Lastname")); //lastname save in sp

                    SPeditor.putString("usernameSP", documentSnapshot.getString("Username")); //username save in sp

                    SPeditor.putString("passSP", documentSnapshot.getString("Password"));  //password save in sp
                    SPeditor.putString("emailSP", documentSnapshot.getString("Email"));  //email save in sp

                    SPeditor.putString("phoneSP", documentSnapshot.getString("Phone")); //phone save in sp
                    SPeditor.putString("dogidSP", documentSnapshot.getString("Dogid")); //Dogid save in sp


                    SPeditor.commit();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void addReport(View v){
        if(sp.getString("dogidSP","").equals("") ||sp.getString("dogidSP","").equals("empty"))
        {
            Toast.makeText(welcomeMenu.this, "dogid: " + sp.getString("dogidSP",""), Toast.LENGTH_LONG).show();
            needDogId();
        }
        else{
            Intent intent = new Intent(welcomeMenu.this, TripReport.class);
            startActivity(intent);
            // finish();
        }
    }

    public void todayReports(View v){
        if(sp.getString("dogidSP","").equals("") ||sp.getString("dogidSP","").equals("empty"))
        {
            Toast.makeText(welcomeMenu.this, "dogid: " + sp.getString("dogidSP",""), Toast.LENGTH_LONG).show();
            needDogId();
        }
        else {
            Intent intent = new Intent(welcomeMenu.this, TodayReport.class);
            startActivity(intent);
           //  finish();
        }
    }

    public void dogGarden(View v){
        if(sp.getString("dogidSP","").equals("") ||sp.getString("dogidSP","").equals("empty"))
        {
            Toast.makeText(welcomeMenu.this, "dogid: " + sp.getString("dogidSP",""), Toast.LENGTH_LONG).show();
            needDogId();
        }
        else {
            Intent intent = new Intent(welcomeMenu.this, DogsGarden.class);
            startActivity(intent);
            // finish();
        }
    }

    public void dogid(View v){

        Intent intent = new Intent(welcomeMenu.this, dogid_page.class);
        startActivity(intent);
        // finish();
    }

    public void theShop(View v){
        Intent intent = new Intent(welcomeMenu.this, theShop.class);
        startActivity(intent);
        // finish();
    }

    public void comunitypage(View v){
        Intent intent = new Intent(welcomeMenu.this, comunity.class);
        startActivity(intent);
        // finish();
    }

//---------------------------------------
    public void needDogId(){

            AlertDialog.Builder builder2 = new AlertDialog.Builder(welcomeMenu.this);
            builder2.setMessage("go first to 'DOG ID' on menu\n to set your dog");
            builder2.setCancelable(false);

            builder2.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder2.create();
            alert11.show();
    }

    public void disconnectUser(View v){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(welcomeMenu.this);
        builder1.setMessage("LOG OUT \n Are you sure?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        deleteAllSP();

                        Intent intent = new Intent(welcomeMenu.this, logIn.class);
                        startActivity(intent);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void deleteAllSP(){

        SPeditor.putString("firstnameSP","empty");
        SPeditor.putString("lastnameSP","empty");
        SPeditor.putString("usernameSP","empty");
        SPeditor.putString("passSP","empty");
        SPeditor.putString("emailSP","empty");
        SPeditor.putString("phoneSP","empty");
        SPeditor.putString("dogidSP","empty");
        SPeditor.commit();

    }

    public void getname() {

        welhead.setText("Welcome "+sp.getString("firstnameSP", "")+"!");

        if(sp.getString("dogidSP", "")=="" || sp.getString("dogidSP", "")=="empty"){
            weldog.setTextColor(Color.RED);
            weldog.setText("No Dog connected-go to 'DOG ID'");}
            else
        weldog.setText("Dog : "+sp.getString("dogidSP", ""));
    }

    //---------------------------------------

    public void goHeb(){

       // Toast.makeText(welcomeMenu.this, "heb", Toast.LENGTH_LONG).show();
//        welhead.setText("שלום "+sp.getString("firstnameSP", "")+"!");
    //    weldog.setText("כלב : "+sp.getString("dogidSP", ""));

        //buttons
        addReportB.setText("דווח על \n טיול / אוכל");
                todayRportB.setText("דיווחי היום");
                dogGardenB.setText("גינת כלבים");
                dogIdB.setText("זיהוי כלב");
                theShopB.setText("החנות");
                commB.setText("קהילה");
        dicoB.setText("התנתק");
    }

    public void goeng(){

      //  Toast.makeText(welcomeMenu.this, "eng", Toast.LENGTH_LONG).show();

      //  welhead.setText("Welcome "+sp.getString("firstnameSP", "")+"!");
       // weldog.setText("Dog : "+sp.getString("dogidSP", ""));
        //buttons
        addReportB.setText("Add Report \nTrip/Food ");
        todayRportB.setText("Today's Reports");
        dogGardenB.setText("Dog's Garden");
        dogIdB.setText("Dog id");
        theShopB.setText("The Shop");
        commB.setText("Community");
        dicoB.setText("disconnectB");

    }





}
