package com.ititraining.rahlati.ui.history;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.GeoPoint;
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.ui.history.DirectionHelper.FetchURL;
import com.ititraining.rahlati.ui.history.DirectionHelper.TaskLoadedCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapActivity  extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
 /////////////////////////////////////////
 Context context = ApplicationProvider.getApplicationContext();
// MapActivity(String  start_point,String end_point){
//     this.start_point=start_point;
//     this.end_point=end_point; }
    String start_point="Damietta,Qism Damietta,Damietta Desert,Damietta Governorate,Egypt",
    end_point= "Harayah Raznah,Zagazig,Ash Sharqia Governoment,Egypt";


    LatLng  start_point_lat=getLocationFromAddress(context,start_point),
            end_point_lat=getLocationFromAddress(context,end_point);
  /////////////////////
 Button getDirection;
float r=new Random().nextInt(360);
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    Random random = new Random();
    int rand_color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    List<MarkerOptions> markerOptionsList=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);

        getDirection = findViewById(R.id.btnGetDirection);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(MapActivity.this) .execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving"); }});


//        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503));
//        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583));
        place1 = new MarkerOptions().position(start_point_lat)
                .icon(BitmapDescriptorFactory.defaultMarker(r));
        place2 = new MarkerOptions().position(end_point_lat)
                .icon(BitmapDescriptorFactory.defaultMarker(r));

       markerOptionsList.add(place1);
        markerOptionsList.add(place2);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFreg);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addPolyline((new PolylineOptions()).add(start_point_lat,end_point_lat).width(5).color(rand_color).geodesic(true));
        showAllMarkers();
    }
    private void showAllMarkers(){
        LatLngBounds.Builder builder=new LatLngBounds.Builder();
        for(MarkerOptions m:markerOptionsList){
            builder.include(m.getPosition());
        }
        LatLngBounds bounds=builder.build();
        int width=getResources().getDisplayMetrics().widthPixels;
        int height=getResources().getDisplayMetrics().heightPixels;
        int padding=(int)(width*.30);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding);
        mMap.animateCamera(cameraUpdate);
    }
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output +"?" + parameters + "&key=AIzaSyDzTHI4DXKFwrM0xAzwnI-Brz1_3UkcL7g" ;
        return url;
    }




    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder( context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }











   /* public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;
        LatLng latLng = null;
        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));


            double lat = p1.getLatitude();
            double lng = p1.getLongitude();
             latLng = new LatLng(lat, lng);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  latLng ; }*/
}
