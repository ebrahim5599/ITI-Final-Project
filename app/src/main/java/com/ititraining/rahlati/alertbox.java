package com.ititraining.rahlati;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
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
        Uri alarmUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if( alarmUri==null){
            alarmUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); }
        ringtone=RingtoneManager.getRingtone(getApplicationContext(),alarmUri);
        ringtone.play();
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alertbox_activity);
        btn_start =dialog.findViewById(R.id.start);
        btn_snooze=dialog.findViewById(R.id.snooze);
        btn_cancel=dialog.findViewById(R.id.cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone != null){
                    ringtone.stop();
                    ringtone = null;
                    dialog.dismiss();
                    finish();
                } } });

//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createFloatingWidget(R.layout.main_alarmbox);
//            }
//        });
 dialog.show();
    }
    //////////////////////////////////////////////
    /////Floating bubble

    /*  start floating widget service  */
    public void createFloatingWidget(View view) {
        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE);
        } else
            //If permission is granted start floating widget service
            startFloatingWidgetService();

    }

    /*  Start Floating widget service and finish current activity */
    private void startFloatingWidgetService() {
        startService(new Intent(alertbox.this, FloatingWidgetService.class));
        finish();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK)
                //If permission granted start floating widget service
                startFloatingWidgetService();
            else
                //Permission is not available then display toast
                Toast.makeText(this,getResources().getString(1900024),
                        //getResources().getString(R.string.draw_other_app_permission_denied),
                        Toast.LENGTH_SHORT).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    ///////////////////////////////////////////

}