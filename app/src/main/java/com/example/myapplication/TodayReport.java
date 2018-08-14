package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayReport extends AppCompatActivity {

    FirebaseFirestore db;
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;
    //------------
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference dogprofilephoto;

//--------------------------------------
    ImageView currentdogphoto;
    TextView todayDate;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    TextView Morningtime,Noontime,Nighttime;
    TextView KakiMorning,kakiNoon,kakiNight;
    TextView byMoring,byNoon,byNight;
    //----------------------------------------for translate
    TextView tripHead,foodHead,foodhour,foodby,tripmor,tripnoon,tripnight,timeTV,kakiTV,byTV;
    Button gomenu;
    //---------------------------------------
    TextView Food1time,Food2time,Food3time,dogname;
    TextView Foodby1,Foodby2,Foodby3;

    String id,todayplus="Today",dateToSee,Today,myFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_reports);

        myCalendar = Calendar.getInstance();

        db=FirebaseFirestore.getInstance();
        sp=getSharedPreferences("key",0);

        id=sp.getString("dogidSP", "");
        //-----------------------------------------------for translation
        tripHead=findViewById(R.id.tripsHeader);
        tripmor=findViewById(R.id.empty2);
        tripnoon=findViewById(R.id.empty3);
        tripnight=findViewById(R.id.empty4);
        timeTV=findViewById(R.id.timeHead);
        kakiTV=findViewById(R.id.KakiHead);
        byTV=findViewById(R.id.ByHead);
        //--------
        foodHead=findViewById(R.id.foodHeader);
        foodhour=findViewById(R.id.foodhour);
        foodby=findViewById(R.id.foodby);

        gomenu=findViewById(R.id.gotomenu);
        //-----------------------------------------------
        currentdogphoto=findViewById(R.id.imageView);
        dogname=(TextView)findViewById(R.id.dogname);
        dogname.setText(sp.getString("dogidSP",""));

        todayDate=(TextView)findViewById(R.id.todayDateHere);
        //-----------------------------------------------
        Morningtime=(TextView)findViewById(R.id.trip1);
        Noontime=(TextView)findViewById(R.id.trip2);
        Nighttime=(TextView)findViewById(R.id.trip3);

        KakiMorning=(TextView)findViewById(R.id.yesno1);
        kakiNoon=(TextView)findViewById(R.id.yesno2);
        kakiNight=(TextView)findViewById(R.id.yesno3);

        byMoring=(TextView)findViewById(R.id.by1);
        byNoon=(TextView)findViewById(R.id.by2);
        byNight=(TextView)findViewById(R.id.by3);
        //-----------------------------------------------
        Food1time=(TextView)findViewById(R.id.food1);
        Food2time=(TextView)findViewById(R.id.food2);
        Food3time=(TextView)findViewById(R.id.food3);

        Foodby1=(TextView)findViewById(R.id.foodBy1);
        Foodby2=(TextView)findViewById(R.id.foodBy2);
        Foodby3=(TextView)findViewById(R.id.foodBy3);
        //-----------------------------------------------
        myFormat = "dd-MM-yyyy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        todayDate.setText(sdf.format(myCalendar.getTime())+"(Today)");
        dateToSee=sdf.format(myCalendar.getTime()).toString();
        Today=todayDate.getText().toString();
//-----------------------------------------------
        updatephoto();
        updateTrips();
        updateFood();
//-----------------------------------------------
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

        if(sp.getString("appLang","").equals("heb")){ goHeb(); todayplus="היום";}
        else if(sp.getString("appLang", "").equals("eng")){ goeng(); todayplus="Today";}

    }  // end of oncreate

    protected void onStart() {
        super.onStart();

      //  updateFood();
    }

    public void updatephoto(){

        dogprofilephoto = storageRef.child(sp.getString("dogidSP", ""));

        if(dogprofilephoto!=null) {
            final long ONE_MEGABYTE = 1024 * 1024;
            dogprofilephoto.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.loadingphoto);
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                    requestOptions.error(R.drawable.nophoto);

                    Glide.with(TodayReport.this)
                            .setDefaultRequestOptions(requestOptions)
                            .asDrawable()
                            .load(bytes)
                            .into(currentdogphoto);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        else{
            currentdogphoto.setImageResource(R.drawable.nophoto);

        }

    }
    public void updateTrips() {
        String id = sp.getString("dogidSP", "");

        db.collection("Trips2")
                .document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    String MorningTime=documentSnapshot.getString(dateToSee+"Morning-tripHourTR");
                    if(MorningTime==null){Morningtime.setText("------");}
                    else{{Morningtime.setText(MorningTime);}}

                    String Morningkaki=documentSnapshot.getString(dateToSee+"Morning-kakiTR");
                    if(Morningkaki==null){
                        KakiMorning.setText("------");KakiMorning.setTextColor(Color.BLACK);}
                    else{
                         if(Morningkaki.equals("No")){
                                KakiMorning.setText(Morningkaki);
                                KakiMorning.setTextColor(Color.RED); }
                            else {
                                KakiMorning.setText(Morningkaki);
                             KakiMorning.setTextColor(Color.BLACK);
                         }
                        }

                    String Morningby=documentSnapshot.getString(dateToSee+"Morning-reporterNameTR");
                    if(Morningby==null){byMoring.setText("------");}
                    else{{byMoring.setText(Morningby);}}
                //----------------------------------------
                    String NoonTime=documentSnapshot.getString(dateToSee+"Noon-tripHourTR");
                    if(NoonTime==null){Noontime.setText("------");}
                    else{{Noontime.setText(NoonTime);}}

                    String Noonkaki=documentSnapshot.getString(dateToSee+"Noon-kakiTR");
                    if(Noonkaki==null){
                        kakiNoon.setText("------");
                    kakiNoon.setTextColor(Color.BLACK);}
                    else{
                        if(Noonkaki.equals("No")){
                            kakiNoon.setText(Noonkaki);
                            kakiNoon.setTextColor(Color.RED); }
                        else {
                            kakiNoon.setText(Noonkaki);
                            kakiNoon.setTextColor(Color.BLACK);
                        }
                    }


                    String Noonby=documentSnapshot.getString(dateToSee+"Noon-reporterNameTR");
                    if(Noonby==null){byNoon.setText("------");}
                    else{{byNoon.setText(Noonby);}}
                    //----------------------------------------
                    String NightTime=documentSnapshot.getString(dateToSee+"Night-tripHourTR");
                    if(NightTime==null){Nighttime.setText("------");}
                    else{{Nighttime.setText(NightTime);}}

                    String Nightkaki=documentSnapshot.getString(dateToSee+"Night-kakiTR");
                    if(Nightkaki==null){
                        kakiNight.setText("------");
                    kakiNight.setTextColor(Color.BLACK);}
                    else{
                        if(Nightkaki.equals("No")){
                            kakiNight.setText(Nightkaki);
                            kakiNight.setTextColor(Color.RED); }
                        else {
                            kakiNight.setText(Nightkaki);
                            kakiNight.setTextColor(Color.BLACK);
                        }

                    }

                    String Nightby=documentSnapshot.getString(dateToSee+"Night-reporterNameTR");
                    if(Nightby==null){byNight.setText("------");}
                    else{{byNight.setText(Nightby);}}

                } else {
                    Toast.makeText(TodayReport.this, "no doc of your dog exist", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateFood(){

        String id = sp.getString("dogidSP", "");

        db.collection("Food2")
                .document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                        String foo1 = documentSnapshot.getString(dateToSee + "Meal-1-FoodHourFR");
                        if (foo1 == null) {
                            Food1time.setText("------");
                        } else {
                            {
                                Food1time.setText(foo1);
                            }
                        }

                        String foo2 = documentSnapshot.getString(dateToSee + "Meal-2-FoodHourFR");
                        if (foo2 == null) {
                            Food2time.setText("------");
                        } else {
                            {
                                Food2time.setText(foo2);
                            }
                        }

                        String foo3 = documentSnapshot.getString(dateToSee + "Meal-3-FoodHourFR");
                        if (foo3 == null) {
                            Food3time.setText("------");
                        } else {
                            {
                                Food3time.setText(foo3);
                            }
                        }
                        //----------------------------------------
                        String fooby1 = documentSnapshot.getString(dateToSee + "Meal-1-reporterNameFR");
                        if (fooby1 == null) {
                            Foodby1.setText("------");
                        } else {
                            {
                                Foodby1.setText(fooby1);
                            }
                        }

                        String fooby2 = documentSnapshot.getString(dateToSee + "Meal-2-reporterNameFR");
                        if (fooby2 == null) {
                            Foodby2.setText("------");
                        } else {
                            {
                                Foodby2.setText(fooby2);
                            }
                        }

                        String fooby3 = documentSnapshot.getString(dateToSee + "Meal-3-reporterNameFR");
                        if (fooby3 == null) {
                            Foodby3.setText("------");
                        } else {
                            {
                                Foodby3.setText(fooby3);
                            }
                        }

                } else {
                    Toast.makeText(TodayReport.this, "no doc of your dog exist", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void setDateReport(View v){

        new DatePickerDialog(TodayReport.this,date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateToSee=sdf.format(myCalendar.getTime()).toString();

        if(Today.equals(sdf.format(myCalendar.getTime()))){
            todayDate.setText(sdf.format(myCalendar.getTime())+"("+todayplus+")");}
        else{
        todayDate.setText(sdf.format(myCalendar.getTime()));}

        updateTrips();
        updateFood();
    }

    public void goToMenu(View v){

        Intent intent = new Intent(TodayReport.this, welcomeMenu.class);
        startActivity(intent);
        finish();
    }

    public void setTodayDate(){

        String dateToSee = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        todayDate.setText(dateToSee);
    }

    public void goHeb(){

        todayplus="היום";
        tripHead.setText("טיולים");
        tripmor.setText("בוקר");
        tripnoon.setText("צהריים");
        tripnight.setText("לילה");
        timeTV.setText("שעה");
        kakiTV.setText("קקי");
        byTV.setText("מדווח");
        //--------
        foodHead.setText("אוכל");
        foodhour.setText(":שעה");
        foodby.setText(":מדווח");

        gomenu.setText("חזרה לתפריט");
    }

    public void goeng(){

        todayplus="Today";
        tripHead.setText("TRIPS");
        tripmor.setText("Morning");
        tripnoon.setText("Noon");
        tripnight.setText("Night");
        timeTV.setText("Hour");
        kakiTV.setText("Kaki");
        byTV.setText("By");
        //--------
        foodHead.setText("Food");
        foodhour.setText("Hour");
        foodby.setText("By:");

        gomenu.setText("Go To Menu");


    }


}
