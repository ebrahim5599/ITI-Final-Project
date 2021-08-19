package com.ititraining.rahlati.ui.alarm;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.ititraining.rahlati.R;

public class AlarmService extends Service {

    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service","Service Created!");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, CustomDialog.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(notificationIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CHANNEL DISPLAY NAME", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("alarm has started");
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Now it's Trip time")
                .setContentText("Click here ,please.")
                .setSmallIcon(R.drawable.ic_round_alarm_24)
                .setContentIntent(pendingIntent)
                .setOngoing(false)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}