package com.ititraining.rahlati;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.widget.Toast;

        import androidx.annotation.RequiresApi;

        import com.ititraining.rahlati.ui.alarm.AlarmService;
        import com.ititraining.rahlati.ui.alarm.CustomDialog;


public class AlarmReceiver extends BroadcastReceiver{

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

//        String uriString = intent.getStringExtra("KEY_TONE_URL");
//        Toast.makeText(context,"Alarm ring now ........", Toast.LENGTH_LONG).show();
//
////        Intent secIntent = new Intent(context, alertbox.class);
////        Intent secIntent = new Intent(context, CustomDialog.class);
//        Intent secIntent = new Intent(context, AlarmService.class);
//        secIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        secIntent.putExtra("SEC_RINGTONE_URI", uriString);
//        context.startService(secIntent);
//
////        context.startForegroundService(secIntent);

        Intent intentService = new Intent(context, AlarmService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }

    }

}

