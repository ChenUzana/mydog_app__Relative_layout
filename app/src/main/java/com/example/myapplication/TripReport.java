package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TripReport extends AppCompatActivity {

    int x=0;
    int c1=0,c2=0,c3=0,c4=0;

    CheckBox kakoon;
    EditText tripDate,tripHour,tNotes;
    EditText FoodDate2,FoodHour2;

    RelativeLayout trip;
    RelativeLayout food;
    RelativeLayout mainlay;

    String mealNum="Meal-1";
//---------------------
    TextView THead,pageHead,FHead;
    Button tripAddPageB,foodPageB,todayTripB,nowTripB,sendtripB,todayFoodB,nowFoodB,addFoodRep;

    RadioButton morningT,noonT,nightT,meal1RT,meal2RT,meal3RT;


//---------------------
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor SPeditor;

    String name,dogid;

    String kakiAnswer="No";
    String tripType="Morning";

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_report);

        sp=getSharedPreferences("key",0);
        SPeditor=sp.edit();

        myCalendar = Calendar.getInstance();
        //-------------------------------
        pageHead=(TextView)findViewById(R.id.repHead);
        tripAddPageB=(Button)findViewById(R.id.tripADD);
        THead=(TextView)findViewById(R.id.TripHead);
        todayTripB =(Button)findViewById(R.id.todayB);
        nowTripB=(Button)findViewById(R.id.nowB);
        sendtripB=(Button)findViewById(R.id.addReport);

        morningT=(RadioButton)findViewById(R.id.RBtrip1);
        noonT=(RadioButton)findViewById(R.id.RBtrip2);
        nightT=(RadioButton)findViewById(R.id.RBtrip3);
        tNotes=(EditText)findViewById(R.id.tripNotes);

        FHead=(TextView)findViewById(R.id.foodHead);
        meal1RT=(RadioButton)findViewById(R.id.RDmeal1);
        meal2RT=(RadioButton)findViewById(R.id.RDmeal2);
        meal3RT=(RadioButton)findViewById(R.id.RDmeal3);



        foodPageB=(Button)findViewById(R.id.foodAdd);
        todayFoodB =(Button)findViewById(R.id.todayB2);
        nowFoodB=(Button)findViewById(R.id.nowB2);
        addFoodRep=(Button)findViewById(R.id.addReport2);

//---------------------
        tripDate=(EditText)findViewById(R.id.tripDate);
        tripDate.setText("");
        tripHour=(EditText)findViewById(R.id.tripHour);
        tripHour.setText("");

        FoodDate2=(EditText) findViewById(R.id.FoodDate2);
        FoodDate2.setText("");
        FoodHour2=(EditText) findViewById(R.id.FoodHour2);
        FoodHour2.setText("");

        tripDate.setText("");
        tripHour.setText("");

        FoodDate2.setText("");
        FoodHour2.setText("");

        kakoon=(CheckBox)findViewById(R.id.kakiCB);

        trip=(RelativeLayout)findViewById(R.id.relr);
        food=(RelativeLayout)findViewById(R.id.relFood);
        mainlay=(RelativeLayout)findViewById(R.id.mainlay);

//--------------------------------------------
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

        if(sp.getString("appLang","").equals("heb")){ goHeb(); }
        else if(sp.getString("appLang", "").equals("eng")){ goeng(); }

    } //end of oncreate---------------------------------------------------------

    public void DateFromCalender(View v){
        c1++;
        // TODO Auto-generated method stub
        new DatePickerDialog(TripReport.this,date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setHourDialog2(View v){

        c3++;

        if(v.getId()==R.id.tripHour)
        {

            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(TripReport.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    if(selectedHour<10)
                        tripHour.setText(  "0"+selectedHour + ":" + selectedMinute);
                    if(selectedMinute<10)
                        tripHour.setText( selectedHour + ":" +  "0"+selectedMinute);
                    if(selectedHour<10 && selectedMinute<10)
                        tripHour.setText( "0"+selectedHour + ":" + "0"+ selectedMinute);
                    if(selectedHour>9 && selectedMinute>9 )
                        tripHour.setText( selectedHour + ":" + selectedMinute);

                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
        else{
        myCalendar = Calendar.getInstance();
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(TripReport.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10)
                            FoodHour2.setText(  "0"+selectedHour + ":" + selectedMinute);
                    if(selectedMinute<10)
                        FoodHour2.setText( selectedHour + ":" +  "0"+selectedMinute);
                    if(selectedHour<10 && selectedMinute<10)
                        FoodHour2.setText( "0"+selectedHour + ":" + "0"+ selectedMinute);
                    if(selectedHour>9 && selectedMinute>9 )
                        FoodHour2.setText( selectedHour + ":" + selectedMinute);

                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
    }
        }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        FoodDate2.setText(sdf.format(myCalendar.getTime()));
        tripDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void getTripType(View v){

        if(v.getId()==R.id.RBtrip1){
            tripType="Morning";
           // Toast.makeText(TripReport.this, " type trip morning", Toast.LENGTH_LONG).show();
        }
        if(v.getId()==R.id.RBtrip2){
            tripType="Noon";
          //  Toast.makeText(TripReport.this, " type trip Noon", Toast.LENGTH_LONG).show();
        }
        if(v.getId()==R.id.RBtrip3){
            tripType="Night";
          //  Toast.makeText(TripReport.this, " type trip Night", Toast.LENGTH_LONG).show();
        }
    }

    public void getMealType2(View v){
       if(v.getId()==R.id.RDmeal1){
           mealNum="Meal-1";
           Toast.makeText(TripReport.this, "Meal-1"+mealNum, Toast.LENGTH_LONG).show();
       }
        if(v.getId()==R.id.RDmeal2){
            mealNum="Meal-2";
            Toast.makeText(TripReport.this, "Meal-2"+mealNum, Toast.LENGTH_LONG).show();
        }
        if(v.getId()==R.id.RDmeal3){
            mealNum="Meal-3";
            Toast.makeText(TripReport.this, "Meal-3"+mealNum, Toast.LENGTH_LONG).show();
        }

    }

    public void kakiClick(View v){
        if(kakoon.isChecked())
            kakiAnswer="Yes";
        else
            kakiAnswer="No";
    }

    public void setTodayDate(View v){
        c2++;
        String c = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tripDate.setText(c);
        FoodDate2.setText(c);
    }

    public void setNowTime(View v){
c4++;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String tryit=sdf.format(Calendar.getInstance().getTime());

        tripHour.setText(tryit);
        FoodHour2.setText(tryit);
    }

    public void showTripPage(View v){

        tripDate.setText("");
        tripHour.setText("");
        c1=0;
        c2=0;
        c3=0;
        c4=0;

        trip.setVisibility(View.VISIBLE);
        food.setVisibility(View.GONE);
    }

    public void showFoodPage(View v){

        c1=0;
        c2=0;
        c3=0;
        c4=0;
        FoodDate2.setText("");
        FoodHour2.setText("");

        food.setVisibility(View.VISIBLE);
        trip.setVisibility(View.GONE);
    }

    public void sendTripReport(View v){

        if((c1>0 || c2>0) && (c3>0 || c4>0)) {


            String id = sp.getString("dogidSP", "");

             Map<String, Object> newTrip = new HashMap<>();

            if(tripType.equals("Morning")) {

                newTrip.put(tripDate.getText().toString() + "Morning-tripDateTR", tripDate.getText().toString());  //date
                newTrip.put(tripDate.getText().toString() + "Morning-tripHourTR", tripHour.getText().toString());  //hour
                newTrip.put(tripDate.getText().toString() + "Morning-reporterNameTR", sp.getString("firstnameSP", ""));   // the reporter name
                newTrip.put(tripDate.getText().toString() + "Morning-dogidTR", sp.getString("dogidSP", ""));   //dogname or id
                // newTrip.put("notesTR", email.getText().toString());     // notes by walker
                newTrip.put(tripDate.getText().toString() + "Morning-kakiTR", kakiAnswer);    //kaki
                newTrip.put(tripDate.getText().toString() + "Morning-triptypeTR", tripType);    // 1 of 3
            }
            if(tripType.equals("Noon")) {

                newTrip.put(tripDate.getText().toString() + "Noon-tripDateTR", tripDate.getText().toString());  //date
                newTrip.put(tripDate.getText().toString() + "Noon-tripHourTR", tripHour.getText().toString());  //hour
                newTrip.put(tripDate.getText().toString() + "Noon-reporterNameTR", sp.getString("firstnameSP", ""));   // the reporter name
                newTrip.put(tripDate.getText().toString() + "Noon-dogidTR", sp.getString("dogidSP", ""));   //dogname or id
                // newTrip.put("notesTR", email.getText().toString());     // notes by walker
                newTrip.put(tripDate.getText().toString() + "Noon-kakiTR", kakiAnswer);    //kaki
                newTrip.put(tripDate.getText().toString() + "Noon-triptypeTR", tripType);    // 1 of 3
            }

            if(tripType.equals("Night")) {

                newTrip.put(tripDate.getText().toString() + "Night-tripDateTR", tripDate.getText().toString());  //date
                newTrip.put(tripDate.getText().toString() + "Night-tripHourTR", tripHour.getText().toString());  //hour
                newTrip.put(tripDate.getText().toString() + "Night-reporterNameTR", sp.getString("firstnameSP", ""));   // the reporter name
                newTrip.put(tripDate.getText().toString() + "Night-dogidTR", sp.getString("dogidSP", ""));   //dogname or id
                // newTrip.put("notesTR", email.getText().toString());     // notes by walker
                newTrip.put(tripDate.getText().toString() + "Night-kakiTR", kakiAnswer);    //kaki
                newTrip.put(tripDate.getText().toString() + "Night-triptypeTR", tripType);    // 1 of 3
            }

            db.collection("Trips2").document(id)
                    .set(newTrip, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(TripReport.this, "trip report added to db!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TripReport.this, "Error adding report..contact Admin", Toast.LENGTH_LONG).show();

                        }
                    });

            c1=0;
            c2=0;
            c3=0;
            c4=0;

            finish();
        }
        else{
            Toast.makeText(TripReport.this, "date and hour missing..", Toast.LENGTH_LONG).show();

        }

    }

    public void addFoodReport(View v) {

        if ((c1 > 0 || c2 > 0) && (c3 > 0 || c4 > 0)) {

            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            String id = sp.getString("dogidSP", "");


            Map<String, Object> foodRep = new HashMap<>();

            if(mealNum.equals("Meal-1")) {
                foodRep.put(FoodDate2.getText().toString()+"Meal-1-FoodDateFR", FoodDate2.getText().toString());  //date
                foodRep.put(FoodDate2.getText().toString()+"Meal-1-FoodHourFR", FoodHour2.getText().toString());  //hour
                foodRep.put(FoodDate2.getText().toString()+"Meal-1-reporterNameFR", sp.getString("firstnameSP", ""));   // the reporter name
                foodRep.put(FoodDate2.getText().toString()+"Meal-1-dogidFR", sp.getString("dogidSP", ""));   //dogname or id
                // newTrip.put("notesTR", email.getText().toString());     // notes by walker
                foodRep.put(FoodDate2.getText().toString()+"Meal-1-mealNumFR", mealNum);    // 1 of 3
            }
            if(mealNum.equals("Meal-2")) {
                foodRep.put(FoodDate2.getText().toString()+"Meal-2-FoodDateFR", FoodDate2.getText().toString());  //date
                foodRep.put(FoodDate2.getText().toString()+"Meal-2-FoodHourFR", FoodHour2.getText().toString());  //hour
                foodRep.put(FoodDate2.getText().toString()+"Meal-2-reporterNameFR", sp.getString("firstnameSP", ""));   // the reporter name
                foodRep.put(FoodDate2.getText().toString()+"Meal-2-dogidFR", sp.getString("dogidSP", ""));   //dogname or id
                // newTrip.put("notesTR", email.getText().toString());     // notes by walker
                foodRep.put(FoodDate2.getText().toString()+"Meal-2-mealNumFR", mealNum);    // 1 of 3
            }
            if(mealNum.equals("Meal-3")) {
                foodRep.put(FoodDate2.getText().toString()+"Meal-3-FoodDateFR", FoodDate2.getText().toString());  //date
                foodRep.put(FoodDate2.getText().toString()+"Meal-3-FoodHourFR", FoodHour2.getText().toString());  //hour
                foodRep.put(FoodDate2.getText().toString()+"Meal-3-reporterNameFR", sp.getString("firstnameSP", ""));   // the reporter name
                foodRep.put(FoodDate2.getText().toString()+"Meal-3-dogidFR", sp.getString("dogidSP", ""));   //dogname or id
                // newTrip.put("notesTR", email.getText().toString());     // notes by walker
                foodRep.put(FoodDate2.getText().toString()+"Meal-3-mealNumFR", mealNum);    // 1 of 3
            }

            db.collection("Food2")
                    .document(id)
                    .set(foodRep,SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {

                            Toast.makeText(TripReport.this, "Food report added to db!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TripReport.this, "Error adding report..contact Admin", Toast.LENGTH_LONG).show();
                        }
                    });

            c1 = 0;
            c2 = 0;
            c3 = 0;
            c4 = 0;

            finish();
        } else {
            Toast.makeText(TripReport.this, "date or hour missing..check again", Toast.LENGTH_LONG).show();

        }
    }

    public void goHeb(){

        pageHead.setText("הוספת דיווחים");
        tripAddPageB.setText("טיול");
        kakoon.setText("קקי");

        THead.setText("דיווח על טיול");
        morningT.setText("בוקר");
        noonT.setText("צהריים");
        nightT.setText("לילה");

        todayTripB.setText("היום");
        nowTripB.setText("עכשיו");

        tripDate.setHint("תאריך טיול");
        tripHour.setHint("שעת טיול");

        sendtripB.setText("שמור");
        tNotes.setHint("הערות מיוחדות על הטיול?");

//---------------------------------
        foodPageB.setText("אוכל");
        FHead.setText("דיווח על אוכל");
        todayFoodB.setText("היום");
        nowFoodB.setText("עכשיו");

        meal1RT.setText("ארוחה1");
        meal2RT.setText("ארוחה2");
        meal3RT.setText("ארוחה3");

        FoodDate2.setHint("תאריך");
        FoodHour2.setHint("שעה");
        addFoodRep.setText("שמור דיווח");
    }

    public void goeng(){

        tripAddPageB.setText("TRIP");


    }

}
