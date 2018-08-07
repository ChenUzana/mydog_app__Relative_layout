package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;

 // input validations
    String valuser2="^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){2,8}[a-zA-Z0-9]$";  //username 4-10 chars
    String passregex="^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";
    String pohneregex="^0?(5[024])(\\-)?\\d{7}$";       //israel cellular number
    String normalname="[a-zA-Z]+";
    String Emailval = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    int x=0;
    private static final String TAG = "MainActivity";

    Button si;
    EditText name,lname,usern,pass,email,phone ,dogidd;

    ImageView imageView;

    Boolean nameok=false;
    Boolean lnameok=false;
    Boolean userok=false;
    Boolean passok=false;
    Boolean emailok=false;
    Boolean phoneok=false;
    Boolean dogidok=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sp = getSharedPreferences("key", 0);
        SPeditor = sp.edit();

        si=(Button) findViewById(R.id.signB);

        imageView=(ImageView)findViewById(R.id.imageView1);

        name =(EditText) findViewById(R.id.fnameput);
        lname=(EditText) findViewById(R.id.lnameput);
        usern=(EditText) findViewById(R.id.userput);
        pass=(EditText) findViewById(R.id.passwordput);
        email=(EditText) findViewById(R.id.emailput);
        phone=(EditText) findViewById(R.id.phoneput);
        dogidd=(EditText) findViewById(R.id.dogidput);


        //--------------------------------------------------------------------------

 // input check name
        name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(name.getText().toString().matches(normalname))
                {
                    //toast just for check
                  //  Toast.makeText(Signup.this, "name ok", Toast.LENGTH_LONG).show();
                    nameok=true;
                }
                else{
                    if(name.getText().toString().equals("")){
                       // name.requestFocus();
                       // name.setError("empty");
                        nameok = false;
                    }
                    else {
                        name.requestFocus();
                        name.setError("name - 1 word only)");
                        nameok = false;
                    }

                }
            }
        });

        //--------------------------------------------------------------------------

        // input check last name
        lname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(lname.getText().toString().matches(normalname))
                {
                   // Toast.makeText(Signup.this, "name ok", Toast.LENGTH_LONG).show();
                    lnameok=true;
                }
                else{
                    if(lname.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        lnameok = false;
                    }
                    else {
                        lname.requestFocus();
                        lname.setError("last name- 1 word)");
                        lnameok = false;
                    }

                }
            }
        });

        //--------------------------------------------------------------------------

        // input check username
        usern.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(usern.getText().toString().matches(valuser2))
                {
                    //toast just for check
                 //   Toast.makeText(Signup.this, "username ok", Toast.LENGTH_LONG).show();
                    userok=true;
                }
                else{
                    if(usern.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        userok = false;
                    }
                    else {
                        usern.requestFocus();
                        usern.setError("1 word - 4-10 chars");
                        userok = false;
                    }

                }
            }
        });

//--------------------------------------------------------------------------
        // input check email
        email.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) { }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(email.getText().toString().matches(Emailval))
                    {
                        //toast just for check
                       // Toast.makeText(Signup.this, "email ok", Toast.LENGTH_LONG).show();
                        emailok=true;
                    }
                    else{
                        if(email.getText().toString().equals("")){
                            // name.requestFocus();
                            // name.setError("empty");
                            emailok = false;
                        }
                        else {
                            email.requestFocus();
                            email.setError("uncorrect email format");
                            emailok = false;
                        }

                    }
                }
        });

        //--------------------------------------------------------------------------
        // input check pass
        pass.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(pass.getText().toString().matches(passregex))
                {
                    //toast just for check
                 //   Toast.makeText(Signup.this, "pass ok", Toast.LENGTH_LONG).show();
                    passok=true;
                }
                else{
                    if(pass.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        passok = false;
                    }
                    else {
                        pass.requestFocus();
                        pass.setError("mi 6 EN letters & numbers ");
                        passok = false;
                    }

                }
            }
        });

        //--------------------------------------------------------------------------
        // input check email
        phone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(phone.getText().toString().matches(pohneregex))
                {
                    //toast just for check
                  //  Toast.makeText(Signup.this, "phone ok", Toast.LENGTH_LONG).show();
                    phoneok=true;
                }
                else{
                    if(phone.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        phoneok = false;
                    }
                    else {
                            phone.requestFocus();
                            phone.setError("10 digits,israel cellular number");
                            phoneok = false;
                    }

                }
            }
        });

    }  // end of onCreate--------------------------------



    public void checkuser(View v){

        // check if fields are ok-validaition

        if (!( nameok.equals(true) &&
                lnameok.equals(true) &&
                userok.equals(true) &&
                passok.equals(true) &&
                emailok.equals(true) &&
                phoneok.equals(true)
        ))

        {
            Toast.makeText(Signup.this, "Check all fields please!"
                            +nameok+","
                            +lnameok+","
                            +userok+","
                            +passok+","
                            +emailok+","
                            +phoneok,
                    Toast.LENGTH_LONG).show();
        }
        else{
           // Toast.makeText(Signup.this, "fields are good",Toast.LENGTH_LONG).show();

            db.collection("Users").document(email.getText().toString())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Toast.makeText(Signup.this, "this Email already signed to app",
                            Toast.LENGTH_LONG).show();
                }
                    else{
                    Toast.makeText(Signup.this, "good,can continue", Toast.LENGTH_LONG).show();

                    //locks the edit textsfor changes after checked
                    lockAllET();

                   //enable and color change to sign button
                    si.setVisibility(View.VISIBLE);
                    si.setEnabled(true);
                }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

             }
            });
        }
    }
//---------------------------------------------------------------------------
    public void signup(View v){
        //creates the user
            Map<String, Object> user = new HashMap<>();
            user.put("Firstname", name.getText().toString());
            user.put("Lastname", lname.getText().toString());
            user.put("Username", usern.getText().toString());
            user.put("Password", pass.getText().toString());
            user.put("Email", email.getText().toString());
            user.put("Phone", phone.getText().toString());
            user.put("Dogid", dogidd.getText().toString());

            //add user to DB
            db.collection("Users").document(email.getText().toString())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(Signup.this, "user :"+email.getText().toString()+" -created!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Signup.this, "Error creating user..contact Admin", Toast.LENGTH_LONG).show();
                        }
                    });


            senssmsORemail();   //sends user email

        clearSPandleave();

    }

    public void senssmsORemail(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("idabida777@gmail.com","baida123");
                    sender.sendMail("WELCOME TO MYDOG APP","## MYDOG APP ## \n\n" +
                                    "Your Password is :  "+pass.getText().toString() +
                                    "\n\n enjoy the app and feel free to contact if you find any app bugs ," +
                                    " \n Chen Uzana \n chenuzana@gmail.com",
                            "ChenUzana", email.getText().toString());
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();

    }

    public void clearSPandleave(){

        SPeditor.putString("firstnameSP","empty");
        SPeditor.putString("lastnameSP","empty");
        SPeditor.putString("usernameSP","empty");
        SPeditor.putString("passSP","empty");
        SPeditor.putString("emailSP","empty");
        SPeditor.putString("phoneSP","empty");
        SPeditor.putString("dogidSP","empty");

        SPeditor.commit();

        Intent intent = new Intent(Signup.this, logIn.class);
        startActivity(intent);
        finish();
    }

    public void showmepass(View v){
//show and hide password when clicked
        if(x==0) {
            pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            x=1;
            imageView.setImageResource(R.drawable.ic_dontshowpass);
        }
        else{
            pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            x=0;
            imageView.setImageResource(R.drawable.ic_showpass);
        }
    }

    public void lockAllET(){

    name.setFocusable(false);
    name.setBackgroundColor(Color.TRANSPARENT);

    lname.setFocusable(false);
    lname.setBackgroundColor(Color.TRANSPARENT);

    usern.setFocusable(false);
    usern.setBackgroundColor(Color.TRANSPARENT);

    pass.setFocusable(false);
    pass.setBackgroundColor(Color.TRANSPARENT);

    email.setFocusable(false);
    email.setBackgroundColor(Color.TRANSPARENT);

    phone.setFocusable(false);
    phone.setBackgroundColor(Color.TRANSPARENT);


}



}
