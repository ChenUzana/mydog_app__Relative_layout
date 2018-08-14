package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class dogid_page extends AppCompatActivity {

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;


    String currentuser;
    String valuser2="^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){2,8}[a-zA-Z0-9]$";  //username 4-10 chars ,for dog id
    String normalname="[a-zA-Z]+";

    Button changeB,editB,createB,uploadphoto;
    LinearLayout linIdChange,linDogCreate,linphoto;
    EditText dNameET,DidET,dAgeET,setIdET;
    TextView curdog;
    Boolean nameok=false,idOk=false;

    ImageView profiledog;
    Bitmap out;
    public static final int REQUEST_CODE = 20;
    Uri imageUri;
    //--------
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference dogprofilephoto;
    //--------
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

        curdog=findViewById(R.id.currentdogid);
        profiledog=(ImageView)findViewById(R.id.dogprofile);
        changeB=(Button) findViewById(R.id.changeb);
        editB=(Button) findViewById(R.id.editb);
        createB=(Button) findViewById(R.id.createb);
        uploadphoto=(Button) findViewById(R.id.choosephotob);

        linIdChange=(LinearLayout)findViewById(R.id.linid);
        linDogCreate=(LinearLayout)findViewById(R.id.lincreate);
        linphoto=(LinearLayout)findViewById(R.id.linephoto);

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

        if(sp.getString("appLang","").equals("heb")){ goHeb(); }
        else if(sp.getString("appLang", "").equals("eng")){ goeng(); }

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

            changeB.setBackgroundResource(R.drawable.border33);
            editB.setBackgroundResource(R.drawable.border3);
            createB.setBackgroundResource(R.drawable.border3);

            linIdChange.setVisibility(View.VISIBLE);
            linDogCreate.setVisibility(View.GONE);
            linphoto.setVisibility(View.GONE);
        }
        if(v.getId()==R.id.createb) {
            changeB.setBackgroundResource(R.drawable.border3);
            editB.setBackgroundResource(R.drawable.border3);
            createB.setBackgroundResource(R.drawable.border33);

            linDogCreate.setVisibility(View.VISIBLE);
            linIdChange.setVisibility(View.GONE);
            linphoto.setVisibility(View.GONE);
        }
        if(v.getId()==R.id.editb) {

            if(sp.getString("dogidSP","").equals("") ||sp.getString("dogidSP","").equals("empty")){

                Toast.makeText(dogid_page.this, "Must connect to a dog first..", Toast.LENGTH_LONG).show();
            }
            else{

                curdog.setText("Dog: "+sp.getString("dogidSP",""));
            changeB.setBackgroundResource(R.drawable.border3);
            editB.setBackgroundResource(R.drawable.border33);
            createB.setBackgroundResource(R.drawable.border3);

            linphoto.setVisibility(View.VISIBLE);
            linDogCreate.setVisibility(View.GONE);
            linIdChange.setVisibility(View.GONE);
            }
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
                        newdog.put("DogCreator", sp.getString("emailSP",""));

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

    public void uploadphoto(View v){

        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // if we are here, everything processed successfully.
            if (requestCode == REQUEST_CODE) {
                imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    out = Bitmap.createScaledBitmap(image, 300, 220, false);

                    profiledog.setImageBitmap(out);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public void saveprofilephoto(View v) {
        dogprofilephoto = storageRef.child(sp.getString("dogidSP", ""));

        profiledog.setDrawingCacheEnabled(true);
        profiledog.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) profiledog.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = dogprofilephoto.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Intent intent = new Intent(dogid_page.this, welcomeMenu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //---------------------------------------

    public void goHeb(){



    }

    public void goeng(){


    }
}
