package com.ititraining.rahlati.ui.history;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.ui.history.DirectionHelper.TaskLoadedCallback;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.GeoPoint;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;

public class HistoryMapFragment extends Fragment implements OnMapReadyCallback, TaskLoadedCallback {
    String start_point, end_point;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    Random random = new Random();
    int rand_color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    List<MarkerOptions> markerOptionsList=new ArrayList<>();
    public HistoryMapFragment(String  start_point,String end_point){
        this.start_point=start_point;
        this.end_point=end_point;}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history_map, container, false);


        place1 = new MarkerOptions().position(getLocationFromAddress(start_point));
        place2 = new MarkerOptions().position(getLocationFromAddress(end_point));
        markerOptionsList.add(place1);
        markerOptionsList.add(place2);
      //  com.google.android.gms.maps.MapFragment  mapFragment = (com.google.android.gms.maps.MapFragment)getFragmentManager().findFragmentById(R.id.mapFreg);
      //  SupportMapFragment mapFragment =  (SupportMapFragment) form.getSupportFragmentManager().findFragmentByTag();//SupportMapFragment.findFragmentById(R.id.mapFreg);//.newInstance();
        //mapFragment.getMapAsync(this);
        SupportMapFragment mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.mapFreg);
        mapFragment.getMapAsync(this);
         return view;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addPolyline((new PolylineOptions()).add( getLocationFromAddress(start_point),getLocationFromAddress(end_point)).width(5).color(rand_color).geodesic(true));
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



    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(getApplicationContext(), Locale.getDefault());
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
        return  latLng ; }
}
