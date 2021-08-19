package com.ititraining.rahlati.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ititraining.rahlati.R;

import static com.ititraining.rahlati.MainActivity.tripID;
import static com.ititraining.rahlati.MainActivity.upComingRef;

public class NoteActivityDialog extends AppCompatActivity {

    private EditText note;
    private String noteTitle, id, text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // added 11:34

        note = (EditText) findViewById(R.id.note_dialog);
        Button btn_save = (Button) findViewById(R.id.save_dialog);

        Intent n = getIntent();
        id = n.getStringExtra("ID");
        tripID = id;
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