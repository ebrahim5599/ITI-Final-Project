package com.ititraining.rahlati;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class alertbox extends AppCompatActivity {
    Button btn_start;
    Button btn_snooze;
    Button btn_cancel;
    Ringtone ringtone;
    Dialog dialog;
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if( alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertbox_activity);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();

        btn_start = dialog.findViewById(R.id.start);
        btn_snooze = dialog.findViewById(R.id.snooze);
        btn_cancel = dialog.findViewById(R.id.cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone != null){
                    ringtone.stop();
                    ringtone = null;
                    dialog.dismiss();
                    finish();
                }
            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//
//            setShowWhenLocked(true);
//            setTurnScreenOn(true);
//            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
//            keyguardManager.requestDismissKeyguard(this, null);
//        }
//        else {
//
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
//                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
//                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
//                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        }

    }

}