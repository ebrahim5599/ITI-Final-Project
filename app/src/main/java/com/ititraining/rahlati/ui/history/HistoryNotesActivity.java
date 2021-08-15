package com.ititraining.rahlati.ui.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ititraining.rahlati.R;

import static com.ititraining.rahlati.MainActivity.historyRef;
import static com.ititraining.rahlati.MainActivity.upComingRef;

public class HistoryNotesActivity extends AppCompatActivity {

    private String noteTitle, id, text = null;
    private TextView display_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_notes);

        display_note = (TextView) findViewById(R.id.display_note);
        Intent n = getIntent();
        id = n.getStringExtra("ID");
    }

    @Override
    protected void onStart() {
        super.onStart();

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text = snapshot.child(id).child("note").getValue(String.class);
                display_note.setText(text);
//                if(text.isEmpty()){
//                    display_note.setText("No notes have been added to this Trip.");
//                }else{
//                    display_note.setText(text);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}