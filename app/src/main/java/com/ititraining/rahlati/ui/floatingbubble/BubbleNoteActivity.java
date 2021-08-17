package com.ititraining.rahlati.ui.floatingbubble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ititraining.rahlati.R;

import static com.ititraining.rahlati.MainActivity.historyRef;
import static com.ititraining.rahlati.MainActivity.tripID;
import static com.ititraining.rahlati.MainActivity.uId;

public class BubbleNoteActivity extends AppCompatActivity {

    private String id, text = null;
    private TextView text_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_note);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // added 11:34

        text_note = (TextView) findViewById(R.id.bubble_note_dialog);
    }


    @Override
    protected void onStart() {
        super.onStart();

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                text = snapshot.child(tripID).child("note").getValue(String.class);
                text_note.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
