package com.ititraining.rahlati;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.ititraining.rahlati.ui.home.UpComingTrips;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.ititraining.rahlati.ui.home.HomeFragment.adapter;
import static com.ititraining.rahlati.ui.home.HomeFragment.arrayList;
import static com.ititraining.rahlati.ui.home.HomeFragment.upComingTrips;

public class SetTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private MainActivity activity;
    public static final String FILE_NAME = "main file";
    private FileOutputStream fos;
    private FileInputStream fis;
    EditText edt_end,edt_start;
    ImageButton calender;
    TextView txt_date,txt_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_trip);

        EditText edt_trip_name = (EditText) findViewById(R.id.trip_name);
        edt_start = (EditText) findViewById(R.id.start);
        edt_end = (EditText) findViewById(R.id.end);
        calender = findViewById(R.id.calbtm);
        //////////////////////////////////
        //Calender.
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePikerDialog();
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
// فاضل تعديل التاريخ والوقت
        Button addTrip = (Button) findViewById(R.id.add);
        if(edit_intent.getStringExtra("TRIP_NAME") != null){
            addTrip.setText("EDIT TRIP INFO");
            addTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upComingTrips = new UpComingTrips(txt_date.getText().toString(),txt_time.getText().toString(),
                            edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString());
                    arrayList.set(edit_intent.getIntExtra("POSITION",0),upComingTrips);
                    adapter.notifyDataSetChanged();
                    finish();
                }
            });
        }else{
            addTrip.setText("ADD TRIP");
            addTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upComingTrips = new UpComingTrips(txt_date.getText().toString(),txt_time.getText().toString(),
                            edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString());
                    arrayList.add(upComingTrips);
                    adapter.notifyDataSetChanged();
                    finish();
                }
            });
        }
    }
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
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =""+ dayOfMonth + '/' + (month+1) + '/' + year;
        txt_date.setText(date);
    }
}
