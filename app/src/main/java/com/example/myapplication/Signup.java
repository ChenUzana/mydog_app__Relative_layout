package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

 // input validations
    String valuser2="^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){2,8}[a-zA-Z0-9]$";
    String valuser="^[a-z0-9._-]$";
    String normalname="[a-zA-Z]+";
    String Emailval = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    private static final String TAG = "MainActivity";
    FirebaseFirestore db;

    Button si;

    TextView er;
    EditText fname,lname,usern,pass,email,phone ,dogidd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        si=(Button) findViewById(R.id.signB);

        fname =(EditText) findViewById(R.id.fnameput);
        lname=(EditText) findViewById(R.id.lnameput);
        usern=(EditText) findViewById(R.id.userput);
        pass=(EditText) findViewById(R.id.passwordput);
        email=(EditText) findViewById(R.id.emailput);
        phone=(EditText) findViewById(R.id.phoneput);
        dogidd=(EditText) findViewById(R.id.dogidput);

        db = FirebaseFirestore.getInstance();

        //--------------------------------------------------------------------------


 // input check name
        fname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(fname.getText().toString().equals("")  || !fname.getText().toString().matches(normalname))
                {
                    if(fname.getText().toString().equals(""))
                    {
                   // er.setText("name field id empty");
                   // er.setVisibility(View.VISIBLE);
                    fname.requestFocus();
                    fname.setError("FIELD CANNOT BE EMPTY");
                    }
                    else
                        {fname.requestFocus();
                    fname.setError("english only- no space");}
                }
            }
        });

        //--------------------------------------------------------------------------

        // input check last name
        lname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(lname.getText().toString().equals("")  || !lname.getText().toString().matches("[a-zA-Z]+"))
                {
                    if(lname.getText().toString().equals(""))
                    {
                        // er.setText("name field id empty");
                        // er.setVisibility(View.VISIBLE);
                        lname.requestFocus();
                        lname.setError("FIELD CANNOT BE EMPTY");
                    }
                    else
                    {lname.requestFocus();
                        lname.setError("english only-no space");}
                }
            }
        });


        //--------------------------------------------------------------------------

        // input check username
        usern.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                 if(!usern.getText().toString().matches(valuser))
                {
                    usern.requestFocus();
                    usern.setError("english+digits only-no space");}

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(usern.getText().toString().equals("") )// || !usern.getText().toString().matches(valuser))
                {
                    if(usern.getText().toString().equals(""))
                    {
                        // er.setText("name field id empty");
                        // er.setVisibility(View.VISIBLE);
                        usern.requestFocus();
                        usern.setError("FIELD CANNOT BE EMPTY");
                    }
               /*     else
                    {usern.requestFocus();
                        usern.setError("english+digits only-no space");} */
                }
            }
        });

//--------------------------------------------------------------------------
        // input check email
        usern.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(!usern.getText().toString().matches(valuser))
                {
                    usern.requestFocus();
                    usern.setError("english+digits only-no space");}

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(usern.getText().toString().equals("") )// || !usern.getText().toString().matches(valuser))
                {
                    if(usern.getText().toString().equals(""))
                    {
                        // er.setText("name field id empty");
                        // er.setVisibility(View.VISIBLE);
                        usern.requestFocus();
                        usern.setError("FIELD CANNOT BE EMPTY");
                    }
               /*     else
                    {usern.requestFocus();
                        usern.setError("english+digits only-no space");} */
                }
            }
        });

//--------------------------------------------------------------------------
        // the check button
        findViewById(R.id.checkB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        si.setBackgroundColor(Color.GREEN);

                 si.setEnabled(true);
            }
        });

//--------------------------------------------------------------------------
        //creates user in database button
        findViewById(R.id.signB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // check if fields are empty
                if ( fname.getText().toString().equals("") ||
                        lname.getText().toString().equals("") ||
                        usern.getText().toString().equals("") ||
                        pass.getText().toString().equals("") ||
                        email.getText().toString().equals("") ||
                        phone.getText().toString().equals("")

                        ){

                    Toast.makeText(Signup.this, "Check all fields please!",
                            Toast.LENGTH_LONG).show();


                }
                //else if feilds are not empty
                else {


                    si.setBackgroundColor(Color.GREEN);


                    Toast.makeText(Signup.this, "ok :"+ fname.getText().toString(),
                            Toast.LENGTH_LONG).show();


                    Map<String, Object> user = new HashMap<>();
                    user.put("Firstname", fname.getText().toString());
                    user.put("Lastname", lname.getText().toString());
                    user.put("Username", usern.getText().toString());
                    user.put("Password", pass.getText().toString());
                    user.put("Email", email.getText().toString());
                    user.put("Phone", phone.getText().toString());
                    user.put("Dogid", dogidd.getText().toString());

                    db.collection("Users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                    //sends the user sms with username and password




                }

            }
        });
        //onclick button end
//--------------------------------------------------------------------------




    }

    public void senssms(){
        String messege="Dear "+fname.getText().toString()+
                ", \n welcome to my dog! ,your username is : "+usern.getText().toString()+"\n   password is :"+ pass.getText().toString();


        SmsManager.getDefault().sendTextMessage(phone.getText().toString(), null, messege, null,null);   // sending sms

    }
}
