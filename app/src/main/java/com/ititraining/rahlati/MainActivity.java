package com.ititraining.rahlati;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.ititraining.rahlati.ui.history.HistoryFragment;
import com.ititraining.rahlati.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        add button: to add new trip [Ibrahim].
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,SetTripActivity.class);
                startActivity(intent);
            }
        });

        // this code enables Navigation drawer [Ibrahim].
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//
//        Intent intent = getIntent();
//        String trip_name = intent.getStringExtra("TRIP_NAME");
//        String trip_start_point = intent.getStringExtra("START");
//        String trip_end_point = intent. getStringExtra("END");
//        Toast.makeText(this,trip_name, Toast.LENGTH_SHORT).show();
//
//        if(savedInstanceState == null){
//            fragment = new HomeFragment();
//            fragmentManager = getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.nav_host_fragment, fragment, "HomeFragment").commit();
//        }else{
//            fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
//        }
//
//        fragment.setTripName(trip_name);
//        fragment.setStartPoint(trip_start_point);
//        fragment.setEndPoint(trip_end_point);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//
//    private void setTripName(String tripName) {
//        this.tripName = tripName;
//    }
//
//    private void setStartPoint(String startPoint) {
//        this.startPoint = startPoint;
//    }
//
//    private void setEndPoint(String endPoint) {
//        this.endPoint = endPoint;
//    }
}
