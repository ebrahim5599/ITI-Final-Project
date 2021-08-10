package com.ititraining.rahlati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ititraining.rahlati.ui.home.UpComingTrips;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.ititraining.rahlati.ui.home.HomeFragment.adapter;
import static com.ititraining.rahlati.ui.home.HomeFragment.arrayList;
import static com.ititraining.rahlati.ui.home.HomeFragment.upComingTrips;

public class SetTripActivity extends AppCompatActivity {

    private MainActivity activity;
    public static final String FILE_NAME = "main file";
    private FileOutputStream fos;
    private FileInputStream fis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_trip);

        EditText edt_trip_name = (EditText) findViewById(R.id.trip_name);
        EditText edt_start = (EditText) findViewById(R.id.start);
        EditText edt_end = (EditText) findViewById(R.id.end);

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
}