package com.ititraining.rahlati;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Rahlati extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
