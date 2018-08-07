package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayReport extends AppCompatActivity {

    FirebaseFirestore db;
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;


    TextView todayDate;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    TextView Morningtime,Noontime,Nighttime;
    TextView KakiMorning,kakiNoon,kakiNight;
    TextView byMoring,byNoon,byNight;

    TextView dogname;

    TextView Food1time,Food2time,Food3time;
    TextView Foodby1,Foodby2,Foodby3;

    String id;
    String myFormat;

    String dateToSee;
    String Today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_reports);

        myCalendar = Calendar.getInstance();

        db=FirebaseFirestore.getInstance();
        sp=getSharedPreferences("key",0);

        id=sp.getString("dogidSP", "");

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

    }  // end of oncreate

    protected void onStart() {
        super.onStart();

      //  updateFood();
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
                    if(MorningTime==null){Morningtime.setText("empty");}
                    else{{Morningtime.setText(MorningTime);}}

                    String Morningkaki=documentSnapshot.getString(dateToSee+"Morning-kakiTR");
                    if(Morningkaki==null){KakiMorning.setText("empty");}
                    else{{KakiMorning.setText(Morningkaki);}}

                    String Morningby=documentSnapshot.getString(dateToSee+"Morning-reporterNameTR");
                    if(Morningby==null){byMoring.setText("empty");}
                    else{{byMoring.setText(Morningby);}}
                //----------------------------------------
                    String NoonTime=documentSnapshot.getString(dateToSee+"Noon-tripHourTR");
                    if(NoonTime==null){Noontime.setText("empty");}
                    else{{Noontime.setText(NoonTime);}}

                    String Noonkaki=documentSnapshot.getString(dateToSee+"Noon-kakiTR");
                    if(Noonkaki==null){kakiNoon.setText("empty");}
                    else{{kakiNoon.setText(Noonkaki);}}


                    String Noonby=documentSnapshot.getString(dateToSee+"Noon-reporterNameTR");
                    if(Noonby==null){byNoon.setText("empty");}
                    else{{byNoon.setText(Noonby);}}
                    //----------------------------------------
                    String NightTime=documentSnapshot.getString(dateToSee+"Night-tripHourTR");
                    if(NightTime==null){Nighttime.setText("empty");}
                    else{{Nighttime.setText(NightTime);}}

                    String Nightkaki=documentSnapshot.getString(dateToSee+"Night-kakiTR");
                    if(Nightkaki==null){kakiNight.setText("empty");}
                    else{{kakiNight.setText(Nightkaki);}}

                    String Nightby=documentSnapshot.getString(dateToSee+"Night-reporterNameTR");
                    if(Nightby==null){byNight.setText("empty");}
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
                            Food1time.setText("empty");
                        } else {
                            {
                                Food1time.setText(foo1);
                            }
                        }

                        String foo2 = documentSnapshot.getString(dateToSee + "Meal-2-FoodHourFR");
                        if (foo1 == null) {
                            Food2time.setText("empty");
                        } else {
                            {
                                Food2time.setText(foo1);
                            }
                        }

                        String foo3 = documentSnapshot.getString(dateToSee + "Meal-3-FoodHourFR");
                        if (foo3 == null) {
                            Food3time.setText("empty");
                        } else {
                            {
                                Food3time.setText(foo3);
                            }
                        }
                        //----------------------------------------
                        String fooby1 = documentSnapshot.getString(dateToSee + "Meal-1-reporterNameFR");
                        if (fooby1 == null) {
                            Foodby1.setText("empty");
                        } else {
                            {
                                Foodby1.setText(fooby1);
                            }
                        }

                        String fooby2 = documentSnapshot.getString(dateToSee + "Meal-2-reporterNameFR");
                        if (fooby2 == null) {
                            Foodby2.setText("empty");
                        } else {
                            {
                                Foodby2.setText(fooby2);
                            }
                        }

                        String fooby3 = documentSnapshot.getString(dateToSee + "Meal-3-reporterNameFR");
                        if (fooby3 == null) {
                            Foodby3.setText("empty");
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
            todayDate.setText(sdf.format(myCalendar.getTime())+"(Today)");}
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


}
