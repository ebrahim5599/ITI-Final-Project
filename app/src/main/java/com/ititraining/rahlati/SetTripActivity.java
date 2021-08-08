package com.ititraining.rahlati;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class SetTripActivity extends AppCompatActivity {
    //Initialize variable.
    EditText editText1,editText2,editText3;
    Button btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_trip);
        // Assign Variables.
        editText1 =findViewById(R.id.edit_name);
        editText2=findViewById(R.id.edt_search_st);
        editText3=findViewById(R.id.edt_search_end);
        btnadd=findViewById(R.id.btn_add);
        //Initialize places.
        Places.initialize(getApplicationContext(),"AIzaSyD7qztjBaBRKNSYqnuB3Nf55uGE1uQYJpA");
        editText2.setFocusable(false);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        ,fieldList).build(SetTripActivity.this);
                //noinspection deprecation
                startActivityForResult(intent,100);
            }

        });
        editText3.setFocusable(false);
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.NAME);
                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        ,fieldList).build(SetTripActivity.this);
                //noinspection deprecation
                startActivityForResult(intent,100);
            }
        });
        //get the spinner_repeat from the xml.
        Spinner dropdown = findViewById(R.id.spiner_repeat);
        //create a list of items for the spinner.
        String[] items = new String[]{"No Repeat", "Repeat Daily", "Repeat Weekly","Repeat Monthly"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        ////////////////////////////////////////////
        //get the spinner_type from the xml.
        Spinner dropdown2 = findViewById(R.id.spiner_type);
        String[] items2 = new String[]{"One Way Trip", "Round Trip"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        /////////////////////////////////////////
        //set the add trip button.
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Place place= Autocomplete.getPlaceFromIntent(data);
            editText2.setText(place.getAddress());
            editText3.setText(place.getAddress());
        }else if(resultCode== AutocompleteActivity.RESULT_ERROR){
            Status status= Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}