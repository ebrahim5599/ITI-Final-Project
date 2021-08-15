package com.ititraining.rahlati;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        String uriString = intent.getStringExtra("KEY_TONE_URL");

        Toast.makeText(context,"Alarm ring now ........",
                Toast.LENGTH_LONG).show();

        Intent secIntent = new Intent(context, alertbox.class);
        secIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        secIntent.putExtra("SEC_RINGTONE_URI", uriString);
        context.startActivity(secIntent);

    }

}

