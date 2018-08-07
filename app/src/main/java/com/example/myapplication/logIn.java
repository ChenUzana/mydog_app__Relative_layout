package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


public class logIn extends AppCompatActivity {

    String passregex="^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";
    String Emailval = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    int x=0;

    ImageView imageView;

    EditText userEmailET;
    EditText passwordET;

    String usernameSP;
    String nameSP;

    Boolean userok=false;
    Boolean passok=false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("key", 0);
        SPeditor = sp.edit();

        imageView=(ImageView)findViewById(R.id.imageView1);

//--------------------------------------------------------
        //check if user connected
        //  'NoConnectedUser' is like deleted SP
      if(sp.contains("usernameSP")) {
        if(sp.getString("usernameSP","").equals("empty")) {
            Toast.makeText(logIn.this, "emailSP IS: " + sp.getString("emailSP", ""),
                    Toast.LENGTH_LONG).show();
        }
        else{
              updatSPfromDB();

              Intent intent = new Intent(logIn.this, welcomeMenu.class);
              startActivity(intent);
              finish();

              Toast.makeText(logIn.this, "connected: " + sp.getString("emailSP", ""),
                      Toast.LENGTH_LONG).show();
        }
      }
      else{ // this case is first time app install,sp doesnt exist
          Toast.makeText(logIn.this, "first time use,no SP:  " + sp.getString("usernameSP", ""),
                  Toast.LENGTH_LONG).show();
      }
//------------------------------------------
        userEmailET=(EditText)findViewById(R.id.inputuser);
        passwordET=(EditText)findViewById(R.id.inputpass);

        // input check USERNAME
        userEmailET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(userEmailET.getText().toString().matches(Emailval))
                {
                    //toast just for check
                    //   Toast.makeText(Signup.this, "username ok", Toast.LENGTH_LONG).show();
                    userok=true;
                }
                else{
                    if(userEmailET.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        userok = false;
                    }
                    else {
                        userEmailET.requestFocus();
                      //  usernameET.setError("uncorrect format(1 word)");
                        userok = false;
                    }
                }
            }

        });
        // input check PASSWORD
        passwordET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(passwordET.getText().toString().matches(passregex))
                {
                    //toast just for check
                    //   Toast.makeText(Signup.this, "pass ok", Toast.LENGTH_LONG).show();
                    passok=true;
                }
                else{
                    if(passwordET.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        passok = false;
                    }
                    else {
                       // passwordET.requestFocus();
                        //passwordET.setError("at least 6 letters and numbers ");
                        passok = false;
                    }

                }
            }
        });


    } // end of oncreate


    public void iforgot(View b){
        if(userok==true ){

            //check if this email exist
            db.collection("Users").document(userEmailET.getText().toString())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {

                        final String passtyped=documentSnapshot.getString("Password");
                        final String phonenumber=documentSnapshot.getString("Phone"); //phone save in sp

                      //  Toast.makeText(logIn.this, "great: " + passtyped +phonenumber, Toast.LENGTH_LONG).show();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(logIn.this);
                        builder1.setMessage("Password will be sent to you: ");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "EMAIL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //sending mail
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    GMailSender sender = new GMailSender("idabida777@gmail.com","baida123");
                                                    sender.sendMail("MYDOG APP PASSWORD RESET","## MYDOG APP PASSWORD RESET ## \n\n" +
                                                                    "Your Password is :  "+passtyped +
                                                                    "\n\n enjoy the app and feel free to contact if you find any app bugs ," +
                                                                    " \n Chen Uzana \n chenuzana@gmail.com",
                                                            "ChenUzana", userEmailET.getText().toString());
                                                } catch (Exception e) {
                                                    Log.e("SendMail", e.getMessage(), e);
                                                }
                                            }
                                        }).start();

                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                    else{
                        Toast.makeText(logIn.this, "no such email registered", Toast.LENGTH_LONG).show();
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }
        else {
            Toast.makeText(logIn.this, "Type your email first", Toast.LENGTH_LONG).show();
        }

}

    public void login(View v){

        if(userok==true && passok==true) {

             //check in database if exist and match
            db.collection("Users").document(userEmailET.getText().toString())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {

                        String passtyped=documentSnapshot.getString("Password");
                        if(passtyped.equals(passwordET.getText().toString()))
                        {
                            //here is if login is succesful
                        Toast.makeText(logIn.this, "great,loging in: " + passtyped, Toast.LENGTH_LONG).show();

                     //SP save only if the database check is true

                            SPeditor.putString("firstnameSP", documentSnapshot.getString("Firstname"));  //firstname save in sp
                            SPeditor.putString("lastnameSP", documentSnapshot.getString("Lastname")); //lastname save in sp

                            SPeditor.putString("usernameSP", documentSnapshot.getString("Username")); //username save in sp

                            SPeditor.putString("passSP", documentSnapshot.getString("Password"));  //password save in sp
                            SPeditor.putString("emailSP", documentSnapshot.getString("Email"));  //email save in sp

                            SPeditor.putString("phoneSP", documentSnapshot.getString("Phone")); //phone save in sp
                            SPeditor.putString("dogidSP", documentSnapshot.getString("Dogid")); //Dogid save in sp

                            SPeditor.commit();


                        // move to welcome page
                               Intent intent = new Intent(logIn.this, welcomeMenu.class);
                               startActivity(intent);
                            finish();


                        }else{
                            Toast.makeText(logIn.this, "password is not correct", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(logIn.this, "no such email registered", Toast.LENGTH_LONG).show();

                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        else{
            Toast.makeText(logIn.this, "user/pass-not correct format", Toast.LENGTH_LONG).show();
        }

    }

    public void showmepass(View v){
        if(x==0) {
            passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            x=1;
            imageView.setImageResource(R.drawable.ic_dontshowpass);
        }
        else{
            passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            x=0;
            imageView.setImageResource(R.drawable.ic_showpass);
        }
    }


    public void newuser(View v){
        Intent intent = new Intent(logIn.this, Signup.class);
        startActivity(intent);
        // finish();
    }

    public void updatSPfromDB() {

        db.collection("Users").document(sp.getString("emailSP",""))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    Toast.makeText(logIn.this, "UPDATING FEILDS FOR USER: "+sp.getString("emailSP",""),
                            Toast.LENGTH_LONG).show();

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
        });


    }

    public void showSP(View v){


      /*  SPeditor.putString("firstnameSP","empty");
        SPeditor.putString("lastnameSP","empty");
        SPeditor.putString("usernameSP","empty");
        SPeditor.putString("passSP","empty");
        SPeditor.putString("emailSP","empty");
        SPeditor.putString("phoneSP","empty");
        SPeditor.putString("dogidSP","empty");
        SPeditor.commit();*/

        Map<String, ?> allEntries = sp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }


    }

    public void menu(View v){

        Intent intent = new Intent(logIn.this, welcomeMenu.class);
        startActivity(intent);
        // finish();

    }


}
