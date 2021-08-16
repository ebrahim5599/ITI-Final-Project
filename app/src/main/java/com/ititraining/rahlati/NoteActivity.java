package com.ititraining.rahlati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ititraining.rahlati.ui.home.UpComingTrips;

import static com.ititraining.rahlati.MainActivity.historyRef;
import static com.ititraining.rahlati.MainActivity.upComingRef;

public class NoteActivity extends AppCompatActivity {

    private EditText note;
    private String noteTitle, id, text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        note = (EditText) findViewById(R.id.note_title);
        Button btn_save = (Button) findViewById(R.id.save);

        Intent n = getIntent();
        id = n.getStringExtra("ID");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteTitle = note.getText().toString();
                upComingRef.child(id).child("note").setValue(noteTitle);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        upComingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text = snapshot.child(id).child("note").getValue(String.class);
                note.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}