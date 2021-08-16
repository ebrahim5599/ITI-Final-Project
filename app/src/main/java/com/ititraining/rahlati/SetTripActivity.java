package com.ititraining.rahlati;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.ServerValue;
import com.ititraining.rahlati.ui.home.UpComingTrips;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.ititraining.rahlati.MainActivity.mDatabase;
import static com.ititraining.rahlati.ui.home.HomeFragment.adapter;
import static com.ititraining.rahlati.ui.home.HomeFragment.arrayList;
import static com.ititraining.rahlati.ui.home.HomeFragment.upComingTrips;

public class SetTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private MainActivity activity;
    public static final String FILE_NAME = "main file";
    private FileOutputStream fos;
    private FileInputStream fis;
    EditText edt_end,edt_start;
    ImageButton calender,alarm;
    TextView txt_date,txt_time;
    int hour,minute;
    ////////////////////////////////////////MARINA
    ///alarm defen
    TimePicker timePicker;
    ToggleButton toggleButton;
    DigitalClock digitalClock;
    AlarmManager alarmManager;
    AlarmManager alarmManager1;
    PendingIntent pendingIntent;

    ////////////////////////////////////////////////MARINA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_trip);

        EditText edt_trip_name = (EditText) findViewById(R.id.trip_name);
        edt_start = (EditText) findViewById(R.id.start);
        edt_end = (EditText) findViewById(R.id.end);
        calender = findViewById(R.id.calbtm);
        alarm = findViewById(R.id.alarmbtn);
        //////////////////////////////////
        //Calender.
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePikerDialog();
            }
        });
        //////////////////////////////////
        ///Alarm.
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////MARINA

////alarm ana rington





               /* setContentView(R.layout.main_alarmbox);
                timePicker=findViewById(R.id.timepicker);
                toggleButton=findViewById(R.id.togglebtn);
                digitalClock= findViewById(R.id.digitalclock);
                alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                toggleButton.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        if(toggleButton.isChecked()){
                            Toast.makeText(SetTripActivity.this,"Alarm is ON",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SetTripActivity.this,AlarmReceiver.class);
                            pendingIntent=PendingIntent.getBroadcast(SetTripActivity.this,0,intent,0);
                            Calendar calendar=Calendar. getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                            calendar.set(Calendar.MINUTE,timePicker.getMinute());
                            long time = calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000);

                            if(System.currentTimeMillis()>time){
                                if(calendar.AM_PM==0){time=time+(1000*60*60*12);}
                                else {time=time+(1000*60*60*24);}}
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,0,pendingIntent);

                        }
                        else {
                            alarmManager.cancel(pendingIntent);
                            Toast.makeText(SetTripActivity.this,"Alarm is OFF",Toast.LENGTH_SHORT).show(); }}});*/

                ///////////////////////////////////////MARINA

               // popTimePiker();

            }
        });
        //////////////////////////////////
        // Spinner 1 [Repetition].
        Spinner repetition_spinner = (Spinner) findViewById(R.id.repetition_spinner);
        String repetition_array[] = {"No Repeat", "Repeat Daily", "Repeat Weekly", "Repeat Monthly"};
        ArrayAdapter<String> repetition_adapter = new ArrayAdapter<String>(SetTripActivity.this, android.R.layout.simple_list_item_1, repetition_array);
        repetition_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repetition_spinner.setAdapter(repetition_adapter);

        // Spinner 2 [Ways].
        Spinner way_spinner = (Spinner) findViewById(R.id.way_spinner);
        String way_array[] = {"One Way", "Two Ways"};
        ArrayAdapter<String> way_adapter = new ArrayAdapter<String>(SetTripActivity.this, android.R.layout.simple_list_item_1, way_array);
        way_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        way_spinner.setAdapter(way_adapter);

        // Date and Time TextViews.
         txt_date = (TextView) findViewById(R.id.date);
         txt_time = (TextView) findViewById(R.id.time);
        //////////////////////////
        //initialize Places.
        Places.initialize(getApplicationContext(),"AIzaSyD7qztjBaBRKNSYqnuB3Nf55uGE1uQYJpA");
        //start point.
        edt_start.setFocusable(false);
        edt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS);
                Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(SetTripActivity.this);
                //noinspection deprecation
                startActivityForResult(intent,100);
            }
        });
        /////////////////////////
        //end point.
        edt_end.setFocusable(false);
        edt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList1 = Arrays.asList(Place.Field.ADDRESS);
                Intent intent1= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList1).build(SetTripActivity.this);
                //noinspection deprecation
                startActivityForResult(intent1,110);
            }
        });
        //////////////////////////////

        TextView txt_date = (TextView) findViewById(R.id.date);
        TextView txt_time = (TextView) findViewById(R.id.time);
// Button addTrip = ... raw 61
        Intent edit_intent = getIntent();
        edt_trip_name.setText(edit_intent.getStringExtra("TRIP_NAME"));
        edt_start.setText(edit_intent.getStringExtra("START_POINT"));
        edt_end.setText(edit_intent.getStringExtra("END_POINT"));
        txt_date.setText(edit_intent.getStringExtra("DATE"));
        txt_time.setText(edit_intent.getStringExtra("TIME"));
        String ID = edit_intent.getStringExtra("POSITION");
// فاضل تعديل التاريخ والوقت
        Button addTrip = (Button) findViewById(R.id.add);
        if(edit_intent.getStringExtra("TRIP_NAME") != null){
            addTrip.setText("EDIT TRIP INFO");
            addTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpComingTrips editedTrips = new UpComingTrips(ID, txt_date.getText().toString(),txt_time.getText().toString(),
                            edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString(), "");
//                    arrayList.set(edit_intent.getIntExtra("POSITION",0),upComingTrips);
//                    adapter.notifyDataSetChanged();
                    mDatabase.child("UpComing").child(ID).setValue(editedTrips);
                    finish();
                }
            });
        }else{
            addTrip.setText("ADD TRIP");
            addTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = mDatabase.push().getKey();
                    upComingTrips = new UpComingTrips(id, txt_date.getText().toString(),txt_time.getText().toString(),
                            edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString(), "");
                    mDatabase.child("UpComing").child(id).setValue(upComingTrips);
                    finish();
                }
            });
        }
    }
///////////////////////////////////////////////////////////////////////////////////////MARINA
    private void popTimePiker() {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeSet(TimePicker timePicker1, int selectedHour, int selectedMinute) {

                hour=selectedHour;
                minute=selectedMinute;
                String Time=""+hour+":"+minute;
                txt_time.setText(Time);
//                Toast.makeText(SetTripActivity.this,"Alarm is ON",Toast.LENGTH_SHORT).show();
//               Intent intent=new Intent(SetTripActivity.this,AlarmReceiver.class);
//                pendingIntent=PendingIntent.getBroadcast(SetTripActivity.this,0,intent,0);
//                Calendar calendar=Calendar. getInstance();
//                calendar.set(Calendar.HOUR_OF_DAY,timePicker1.getHour());
//                calendar.set(Calendar.MINUTE,timePicker1.getMinute());
//               long time = calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000);
//                if(System.currentTimeMillis()>time){
//                    if(calendar.AM_PM==0){time=time+(1000*60*60*12);}
//                   else {time=time+(1000*60*60*24);}}
//                alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,time,0,pendingIntent);

            }  };
        TimePickerDialog timePickerDialog= new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
        //////////////////////////////////

        /////////////////////////////////////////////////////////////////////////

    }
///////////////////////////////////////////////////////////////////////////////////MARINA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Place place=Autocomplete.getPlaceFromIntent(data);
            //edt_end.setText(place.getAddress());
            edt_start.setText(place.getAddress());
        }else if(requestCode==110 && resultCode==RESULT_OK){
            Place place=Autocomplete.getPlaceFromIntent(data);
            edt_end.setText(place.getAddress());
        } else if(resultCode== AutocompleteActivity.RESULT_ERROR){
            Status status=Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showDatePikerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this::onDateSet,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }
//////////////////////////////////////////////////////////////////////////////////MARINA
    private void showTimePikerDialog(){
      TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                      txt_time.setText( selectedHour + ":" + selectedMinute);
                  }
              }, hour, minute, true);//Yes 24 hour time
       timePickerDialog.setTitle("Select Time");
       ////////////////////////////////////////////////////////////////////////MARINA
       timePickerDialog.show(); }
////////////////////////////////////////////////////////////////////////////MARINA



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =""+ dayOfMonth + '/' + (month+1) + '/' + year;
        txt_date.setText(date);
    }




}
