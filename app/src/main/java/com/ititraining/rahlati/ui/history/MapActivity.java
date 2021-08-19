package com.ititraining.rahlati.ui.history;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.ui.history.DirectionHelper.FetchURL;
import com.ititraining.rahlati.ui.history.DirectionHelper.TaskLoadedCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class MapActivity  extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

 //String start_point="Damietta",end_point="Zagazig";
    String start_point,end_point;
    MapActivity(String  start_point,String end_point){
         this.start_point=start_point;
       this.end_point=end_point; }
    //button to show the direction betwen the start state and the end  state .
     Button getDirection;
    //random variable to give each trip markers a different color.
    float r=new Random().nextInt(360);
    //map to put poliline , murkers ,and the direction on.
    private GoogleMap mMap;
    //the markers that show the location of the start state and the end  state on the map.
    private MarkerOptions place1, place2;
    //the Polyline between the start state and the end  state on the map.
    private Polyline currentPolyline;
    //list to put on it the two markers.
    List<MarkerOptions> markerOptionsList=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);
        //the button when pressed shows the Direction
 getDirection = findViewById(R.id.btnGetDirection);
 //the Click Listener of the button
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the Direction
            new FetchURL(MapActivity.this) .execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving"); }});
//the place where the marker will appear
        place1 = new MarkerOptions().position(getLatLngFromAddress(start_point)).icon(BitmapDescriptorFactory.defaultMarker(r));
        place2 = new MarkerOptions().position(getLatLngFromAddress(end_point)).icon(BitmapDescriptorFactory.defaultMarker(r));
       markerOptionsList.add(place1);
        markerOptionsList.add(place2);
        //creat  map fragment
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFreg);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addPolyline((new PolylineOptions()).add(getLatLngFromAddress(start_point),getLatLngFromAddress(end_point)));
        showAllMarkers();
    }

    // function to make markers appear
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

//function to get the Latitude and Longitude of the eache the start state and the end  state
private LatLng getLatLngFromAddress(String address) {

    Geocoder geocoder = new Geocoder(getApplicationContext());
    List<Address> addressList;

    try {
        addressList = geocoder.getFromLocationName(address, 1);
        if (addressList != null) {
            Address singleaddress = addressList.get(0);
            LatLng latLng = new LatLng(singleaddress.getLatitude(), singleaddress.getLongitude());
            return latLng;
        } else {
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

}
}
