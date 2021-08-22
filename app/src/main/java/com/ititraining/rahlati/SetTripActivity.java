package com.ititraining.rahlati;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.ititraining.rahlati.ui.alarm.Alarm;
import com.ititraining.rahlati.ui.alarm.AlarmReceiver;
import com.ititraining.rahlati.ui.home.UpComingTrips;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.ititraining.rahlati.MainActivity.mDatabase;
import static com.ititraining.rahlati.MainActivity.uId;
import static com.ititraining.rahlati.ui.home.HomeFragment.upComingTrips;

public class SetTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText edt_end, edt_start;
    ImageButton calender, alarm;
    TextView txt_date, txt_time;
    int hour, minute;
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int sHour, sMinute, sYear, sMonth, sDay;
    private String roundId, name, start, end;
    public static final String ALARM_ID = "trip";
    public int alarmId;
    public static Alarm newAlarm;


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
        SharedPreferences sh = getSharedPreferences(ALARM_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();


        //Calender.
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePikerDialog();
            }
        });

        ///Alarm.
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePiker();
            }
        });

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

        //initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyD7qztjBaBRKNSYqnuB3Nf55uGE1uQYJpA");
        //start point.
        edt_start.setFocusable(false);
        edt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(SetTripActivity.this);
                //noinspection deprecation
                startActivityForResult(intent, 100);
            }
        });

        //end point.
        edt_end.setFocusable(false);
        edt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList1 = Arrays.asList(Place.Field.ADDRESS);
                Intent intent1 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList1).build(SetTripActivity.this);
                //noinspection deprecation
                startActivityForResult(intent1, 110);
            }
        });

        TextView txt_date = (TextView) findViewById(R.id.date);
        TextView txt_time = (TextView) findViewById(R.id.time);

        Intent edit_intent = getIntent();
        edt_trip_name.setText(edit_intent.getStringExtra("TRIP_NAME"));
        edt_start.setText(edit_intent.getStringExtra("START_POINT"));
        edt_end.setText(edit_intent.getStringExtra("END_POINT"));
        txt_date.setText("");
        txt_time.setText("");
        String ID = edit_intent.getStringExtra("POSITION");
        String note = edit_intent.getStringExtra("NOTE");
        int alarm = edit_intent.getIntExtra("ALARM", 0);


        Button addTrip = (Button) findViewById(R.id.add);
        Button addRoundTrip = (Button) findViewById(R.id.add_round_trip);

        if (edit_intent.getStringExtra("TRIP_NAME") != null) {
            addTrip.setText("EDIT TRIP INFO");
            addTrip.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if (edt_trip_name.getText().toString().trim().isEmpty() || edt_start.getText().toString().trim().isEmpty() ||
                            edt_end.getText().toString().trim().isEmpty() || txt_date.getText().toString().trim().isEmpty() ||
                            txt_time.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please, Edit data and time and required data.", Toast.LENGTH_SHORT).show();
                    } else {
                        UpComingTrips editedTrips = new UpComingTrips(ID, txt_date.getText().toString(), txt_time.getText().toString(),
                                edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString(), note, alarm);

                        mDatabase.child(uId).child("UpComing").child(ID).setValue(editedTrips);
                        setAlarm1(editedTrips.getAlarmId());
                        finish();
                    }
                }
            });
        } else {
            addTrip.setText("ADD TRIP");
            addTrip.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if (edt_trip_name.getText().toString().trim().isEmpty() || edt_start.getText().toString().trim().isEmpty() ||
                            edt_end.getText().toString().trim().isEmpty() || txt_date.getText().toString().trim().isEmpty() ||
                            txt_time.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please, Complete required data.", Toast.LENGTH_SHORT).show();
                    } else {
                        checkOverlayPermission();
                        String id = mDatabase.push().getKey();
                        alarmId = sh.getInt(ALARM_ID, 0) + 1;
                        editor.putInt(ALARM_ID, alarmId);
                        editor.commit();

                        upComingTrips = new UpComingTrips(id, txt_date.getText().toString(), txt_time.getText().toString(),
                                edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString(), "", alarmId);
                        mDatabase.child(uId).child("UpComing").child(id).setValue(upComingTrips);
//                    setAlarm();
                        setAlarm1(alarmId);

                        String text = way_spinner.getSelectedItem().toString();
                        if (text == "One Way")
                            finish();
                        else {
                            addTrip.setVisibility(View.GONE);
                            repetition_spinner.setVisibility(View.GONE);
                            way_spinner.setVisibility(View.GONE);
                            addRoundTrip.setVisibility(View.VISIBLE);
                            roundId = mDatabase.push().getKey();
                            name = edt_trip_name.getText().toString() + " (Back)";
                            start = edt_end.getText().toString();
                            end = edt_start.getText().toString();
                            edt_trip_name.setText(name);
                            edt_start.setText(start);
                            edt_end.setText(end);
                            txt_date.setText("");
                            txt_time.setText("");
                        }
                    }
                }
            });

            addRoundTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String d = txt_date.getText().toString();
                    String t = txt_time.getText().toString();

                    alarmId = sh.getInt(ALARM_ID, 0) + 1;
                    editor.putInt(ALARM_ID, alarmId);
                    editor.commit();


                    if (edt_trip_name.getText().toString().trim().isEmpty() || edt_start.getText().toString().trim().isEmpty() ||
                            edt_end.getText().toString().trim().isEmpty() || txt_date.getText().toString().trim().isEmpty() ||
                            txt_time.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please, Complete required data.", Toast.LENGTH_SHORT).show();
                    }else {
                        upComingTrips = new UpComingTrips(roundId, d, t, name, start, end, "", alarmId);
                        mDatabase.child(uId).child("UpComing").child(roundId).setValue(upComingTrips);
//                        setAlarm();
                        setAlarm1(alarmId);
                        finish();
                    }
                }
            });
        }

//        }
    }

    private void popTimePiker() {
        Calendar cal = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
//                hour = selectedHour;
//                minute = selectedMinute;
                cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                cal.set(Calendar.MINUTE, selectedMinute);
                sHour = cal.get(Calendar.HOUR_OF_DAY);
                sMinute = cal.get(Calendar.MINUTE);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
//                String Time = "" + hour + ":" + minute;
                String Time = simpleDateFormat.format(cal.getTime());
                txt_time.setText(Time);

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }

    private void setAlarm1(int ID) {
        newAlarm = new Alarm(ID, sHour, sMinute, sDay, sMonth, sYear, true);
        newAlarm.schedule(getApplicationContext());
    }

    private void editAlarm(int ID) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(SetTripActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SetTripActivity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        long time = calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000);

        if (System.currentTimeMillis() > time) {
            if (calendar.AM_PM == 0) {
                time = time + (1000 * 60 * 60 * 12);
            } else {
                time = time + (1000 * 60 * 60 * 24);
            }
        }
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,0,pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    //Put Places in edittext start & end.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            edt_start.setText(place.getAddress());
        } else if (requestCode == 110 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            edt_end.setText(place.getAddress());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //Show dialog to pick Date.
    private void showDatePikerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this::onDateSet,

                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        sYear = year;
        sMonth = month;
        sDay = dayOfMonth;
        String date = "" + dayOfMonth + '/' + (month + 1) + '/' + year;
        txt_date.setText(date);
    }

    //Checking Overlay permission.
    public void checkOverlayPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(myIntent);
            }
        }
    }

}
