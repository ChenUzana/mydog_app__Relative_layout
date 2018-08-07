package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class dogid_page extends AppCompatActivity {

    String currentuser;

    String valuser2="^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){2,8}[a-zA-Z0-9]$";  //username 4-10 chars ,for dog id
    String normalname="[a-zA-Z]+";

LinearLayout linIdChange,linDogCreate;
EditText dNameET,DidET,dAgeET,setIdET;

Boolean nameok=false,idOk=false;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogid_page);


        myCalendar = Calendar.getInstance();
        sp = getSharedPreferences("key", 0);
        SPeditor = sp.edit();
        currentuser=sp.getString("emailSP","");

        linIdChange=(LinearLayout)findViewById(R.id.linid);
        linDogCreate=(LinearLayout)findViewById(R.id.lincreate);

        dNameET=(EditText)findViewById(R.id.newdognameput);
        DidET=(EditText)findViewById(R.id.newdogidput);
        dAgeET=(EditText)findViewById(R.id.newdogageput);

        setIdET=(EditText)findViewById(R.id.changedogput);
//------------------------------------------------
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };

//------------------------------------------------
        dNameET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(dNameET.getText().toString().matches(normalname))
                {
                    //  Toast.makeText(Signup.this, "name ok", Toast.LENGTH_LONG).show();
                    nameok=true;
                }
                else{
                    if(dNameET.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        nameok = false;
                    }
                    else {
                        dNameET.requestFocus();
                        dNameET.setError("one word,EN letters");
                        nameok = false;
                    }
                }
            }
        });

        DidET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(DidET.getText().toString().matches(valuser2))
                {
                    //toast just for check
                    //   Toast.makeText(Signup.this, "username ok", Toast.LENGTH_LONG).show();
                    idOk=true;
                }
                else{
                    if(DidET.getText().toString().equals("")){
                        // name.requestFocus();
                        // name.setError("empty");
                        idOk = false;
                    }
                    else {
                        DidET.requestFocus();
                        DidET.setError("4-10 | EN letters and numbers");
                        idOk = false;
                    }

                }
            }
        });

    }  //-----on create end

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dAgeET.setText(sdf.format(myCalendar.getTime()));
    }

    public void DateFromCalender(View v){

        // TODO Auto-generated method stub
        new DatePickerDialog(dogid_page.this,date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void headbuttons(View v){
        if(v.getId()==R.id.changeb) {
            linIdChange.setVisibility(View.VISIBLE);
            linDogCreate.setVisibility(View.GONE);
        }
        else{
            linDogCreate.setVisibility(View.VISIBLE);
            linIdChange.setVisibility(View.GONE);
        }
    }

    public void setDogId(View v) {

        if (setIdET.getText().toString().equals(sp.getString("dogidSP", "")))
        {
            Toast.makeText(dogid_page.this, "this dog is already set,no change needed",
                    Toast.LENGTH_LONG).show();
        }
        else {

            db.collection("Dogs").document(setIdET.getText().toString())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Toast.makeText(dogid_page.this, "good,can continue",
                                Toast.LENGTH_LONG).show();
                        SPeditor.putString("dogidSP", setIdET.getText().toString()); //Dogid save in sp
                        SPeditor.commit();
                        setDogId2();
                    } else {
                        Toast.makeText(dogid_page.this, "this dog id doesnt exist, go to create", Toast.LENGTH_LONG).show();
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

    public void setDogId2(){

        Map<String, Object> setdogid = new HashMap<>();

        setdogid.put("Dogid", setIdET.getText().toString());

        db.collection("Users")
                .document(sp.getString("emailSP",""))
                .set(setdogid, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {

                        Toast.makeText(dogid_page.this, "dogid updated!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(dogid_page.this, "Error..contact Admin", Toast.LENGTH_LONG).show();
                    }
                });

        Intent intent = new Intent(dogid_page.this, welcomeMenu.class);
        startActivity(intent);
        finish();

    }

    public void createdog(View v) {

        if (idOk == true && nameok == true) {

            db.collection("Dogs").document(DidET.getText().toString())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Toast.makeText(dogid_page.this, "this dog id already exists,try other",
                                Toast.LENGTH_LONG).show();
                    } else {
                        //-------------------------
                        Map<String, Object> newdog = new HashMap<>();
                        newdog.put("DogName", dNameET.getText().toString());
                        newdog.put("DogidDG", DidET.getText().toString());
                        newdog.put("Dogbirth", dAgeET.getText().toString());

                        db.collection("Dogs").document(DidET.getText().toString())
                                .set(newdog)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void avoid) {
                                        Toast.makeText(dogid_page.this, "new dog"+dNameET.getText().toString()+" Created!",
                                                Toast.LENGTH_LONG).show();

                                        senssmsORemail();

                                        Intent intent = new Intent(dogid_page.this, welcomeMenu.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {


                                    }
                                });
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
            Toast.makeText(dogid_page.this, "Check fields please",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void gomenu(View v){
        Intent intent = new Intent(dogid_page.this, welcomeMenu.class);
        startActivity(intent);
        finish();
    }

    public void senssmsORemail(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("idabida777@gmail.com","baida123");
                    sender.sendMail("MYDOG APP - Dog created",
                            "## new Dod created! ## \n\n" +
                                    "Your dog id is :  "+DidET.getText().toString()
                                    +"\nif you want other people to connect to your dog info you need to send them this id." +
                                    "\n\n enjoy the app and feel free to contact if you find any app bugs ," +
                                    " \n Chen Uzana \n chenuzana@gmail.com",
                            "ChenUzana", sp.getString("emailSP",""));
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();

    }

}
