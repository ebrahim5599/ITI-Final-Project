package com.ititraining.rahlati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ititraining.rahlati.ui.home.UpComingTrips;

import static com.ititraining.rahlati.ui.home.HomeFragment.adapter;
import static com.ititraining.rahlati.ui.home.HomeFragment.arrayList;
import static com.ititraining.rahlati.ui.home.HomeFragment.upComingTrips;

public class SetTripActivity extends AppCompatActivity {

    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_trip);

        EditText edt_trip_name = (EditText) findViewById(R.id.trip_name);
        EditText edt_start = (EditText) findViewById(R.id.start);
        EditText edt_end = (EditText) findViewById(R.id.end);

        Button addTrip = (Button) findViewById(R.id.add);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upComingTrips = new UpComingTrips(edt_trip_name.getText().toString(),  edt_start.getText().toString(), edt_end.getText().toString());
                arrayList.add(upComingTrips);
                adapter.notifyDataSetChanged();
                finish();
            }
        });
    }
}