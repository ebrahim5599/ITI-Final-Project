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

        Button addTrip = (Button) findViewById(R.id.add);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*  TODO: this code to send data through Stream to MainActivity. [Internal Storage]

                try {
                    File f = new File(getFilesDir(), FILE_NAME);
                    if(!f.exists()){
                        f.createNewFile();
                    }
                    fos = new FileOutputStream(f, true);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.println(edt_trip_name.getText()+"."+edt_start.getText()+"."+edt_end.getText());
                    fos.write(edt_trip_name.getText().toString().getBytes());
                    pw.close();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

 */
                upComingTrips = new UpComingTrips(txt_date.getText().toString(),txt_time.getText().toString(),
                        edt_trip_name.getText().toString(), edt_start.getText().toString(), edt_end.getText().toString());
                arrayList.add(upComingTrips);
                adapter.notifyDataSetChanged();
                finish();
            }
        });
    }
}