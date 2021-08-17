package com.ititraining.rahlati.ui.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ititraining.rahlati.MainActivity;
import com.ititraining.rahlati.R;

public class CustomDialog extends AppCompatActivity {

    Button btn_start;
    Button btn_snooze;
    Button btn_cancel;
    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();

        btn_start = findViewById(R.id.start1);
        btn_snooze = findViewById(R.id.snooze1);
        btn_cancel = findViewById(R.id.cancel1);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone != null){
                    ringtone.stop();
                    ringtone = null;
                    stopService(new Intent(getApplicationContext(), AlarmService.class));
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone != null){
                    ringtone.stop();
                    ringtone = null;
                    finish();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone != null){
                    ringtone.stop();
                    ringtone = null;
                    stopService(new Intent(getApplicationContext(), AlarmService.class));
                    finish();
                }
            }
        });

    }
}