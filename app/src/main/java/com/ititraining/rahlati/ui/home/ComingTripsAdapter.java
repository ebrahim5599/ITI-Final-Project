package com.ititraining.rahlati.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ititraining.rahlati.R;
import com.ititraining.rahlati.SetTripActivity;

import java.util.ArrayList;

import static com.ititraining.rahlati.ui.home.HomeFragment.adapter;
import static com.ititraining.rahlati.ui.home.HomeFragment.arrayList;

public class ComingTripsAdapter extends ArrayAdapter<UpComingTrips> {
    public ComingTripsAdapter(@NonNull Context context, @NonNull ArrayList<UpComingTrips> upComingTrips) {
        super(context, 0, upComingTrips);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_container,
                    parent,false);
        }

        UpComingTrips upComingTrips = (UpComingTrips) getItem(position);

        TextView date = convertView.findViewById(R.id.date_view);
        date.setText(upComingTrips.getDate());

        TextView time = convertView.findViewById(R.id.time_view);
        time.setText(upComingTrips.getTime());

        TextView tripName = convertView.findViewById(R.id.tripName);
        tripName.setText(upComingTrips.getTripName());

        TextView startPoint = convertView.findViewById(R.id.start_point);
        startPoint.setText(" "+upComingTrips.getStartPoint());

        TextView endPoint = convertView.findViewById(R.id.end_point);
        endPoint.setText(" "+upComingTrips.getEndPoint());

        TextView upComing = convertView.findViewById(R.id.up_coming);
        upComing.setText("Up Coming");

        TextView start = convertView.findViewById(R.id.start);
        start.setText("START TRIP");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endPoint = upComingTrips.getEndPoint();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+endPoint));
                getContext().startActivity(intent);
            }
        });

        TextView note = convertView.findViewById(R.id.note);
        note.setText("NOTE");

        TextView menu = convertView.findViewById(R.id.menu_icon);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().contains("Note")){
                            Toast.makeText(getContext(), "Not working yet", Toast.LENGTH_SHORT).show();
                        }
                        if(item.getTitle().toString().contains("Edit")){
                            Toast.makeText(getContext(), "ُEdited", Toast.LENGTH_SHORT).show();
                            Intent edit_intent = new Intent(getContext(),SetTripActivity.class);
                            edit_intent.putExtra("POSITION",position);
                            edit_intent.putExtra("DATE",upComingTrips.getDate());
                            edit_intent.putExtra("TIME",upComingTrips.getTime());
                            edit_intent.putExtra("TRIP_NAME",upComingTrips.getTripName());
                            edit_intent.putExtra("START_POINT",upComingTrips.getStartPoint());
                            edit_intent.putExtra("END_POINT",upComingTrips.getEndPoint());
                            getContext().startActivity(edit_intent);
                        }
                        if(item.getTitle().toString().contains("Cancel")){

                            new AlertDialog.Builder(getContext())
                                    .setTitle("Cancel Trip!")
                                    .setMessage("Are you sure you want to cancel this trip?")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                                            arrayList.remove(position);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton("No", null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        if(item.getTitle().toString().contains("Delete")){
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Delete Trip!")
                                    .setMessage("Are you sure you want to delete this trip?")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                            arrayList.remove(position);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton("No", null)
                                    .setIcon(android.R.drawable.ic_delete)
                                    .show();
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });

        return convertView;
    }
}
