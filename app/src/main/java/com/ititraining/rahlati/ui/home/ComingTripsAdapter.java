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

import com.ititraining.rahlati.MainActivity;
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.SetTripActivity;
import com.ititraining.rahlati.ui.alarm.Alarm;

import java.util.ArrayList;

import static com.ititraining.rahlati.MainActivity.historyRef;
import static com.ititraining.rahlati.MainActivity.tripID;
import static com.ititraining.rahlati.MainActivity.upComingRef;

public class ComingTripsAdapter extends ArrayAdapter<UpComingTrips> {

    public MainActivity activity = (MainActivity) getContext();
    public static UpComingTrips trip;
    //    private ArrayList<UpComingTrips> trips;
    public static final String POSITION = "POSITION";
    public static final String DATE ="DATE";
    public static final String TIME = "TIME";
    public static final String TRIP_NAME = "TRIP_NAME";
    public static final String START_POINT = "START_POINT";
    public static final String END_POINT = "END_POINT";
    public static final String NOTE = "NOTE";
    public static final String ALARM_ID ="ALARM_ID";
    public static final String DAY ="DAY";
    public static final String MONTH ="MONTH";
    public static final String YEAR ="YEAR";
    public static final String HOUR ="HOUR";
    public static final String MINUTE ="MINUTE";


    public ComingTripsAdapter(@NonNull Context context, @NonNull ArrayList<UpComingTrips> upComingTrips) {
        super(context, 0, upComingTrips);
//        trips = upComingTrips;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_container,
                    parent, false);
        }

        trip = (UpComingTrips) getItem(position);

        TextView date = convertView.findViewById(R.id.date_view);
        date.setText(trip.getDate());

        TextView time = convertView.findViewById(R.id.time_view);
        time.setText(trip.getTime());

        TextView tripName = convertView.findViewById(R.id.tripName);
        tripName.setText(trip.getTripName());

        TextView startPoint = convertView.findViewById(R.id.start_point);
        startPoint.setText(" " + trip.getStartPoint());

        TextView endPoint = convertView.findViewById(R.id.end_point);
        endPoint.setText(" " + trip.getEndPoint());

        TextView upComing = convertView.findViewById(R.id.up_coming);
        upComing.setText("Up Coming");

        TextView start = convertView.findViewById(R.id.start);
        start.setText("START TRIP");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endPoint = trip.getEndPoint();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + endPoint));
                getContext().startActivity(intent);
                trip.setStatus("Done");
                activity.createFloatingWidget(trip.getNote());
                historyRef.child(trip.getId()).setValue(trip);
                upComingRef.child(trip.getId()).removeValue();
            }
        });

        TextView note = convertView.findViewById(R.id.note);
        note.setText("NOTE");
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripID = trip.getId();
                Intent intent = new Intent(getContext(), NoteActivityDialog.class);
                intent.putExtra("ID", trip.getId());
                getContext().startActivity(intent);
            }
        });

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

                        if (item.getTitle().toString().contains("Note")) {
                            Toast.makeText(getContext(), "Note", Toast.LENGTH_SHORT).show();
                            tripID = trip.getId();
                            Intent intent = new Intent(getContext(), NoteActivityDialog.class);
                            intent.putExtra("ID", trip.getId());
                            getContext().startActivity(intent);
                        }

                        if (item.getTitle().toString().contains("Edit")) {
                            Toast.makeText(getContext(), "Edited", Toast.LENGTH_SHORT).show();
                            Intent edit_intent = new Intent(getContext(), SetTripActivity.class);
                            edit_intent.putExtra(POSITION , trip.getId());
                            edit_intent.putExtra(DATE, trip.getDate());
                            edit_intent.putExtra(TIME, trip.getTime());
                            edit_intent.putExtra(TRIP_NAME, trip.getTripName());
                            edit_intent.putExtra(START_POINT, trip.getStartPoint());
                            edit_intent.putExtra(END_POINT, trip.getEndPoint());
                            edit_intent.putExtra(NOTE, trip.getNote());
                            edit_intent.putExtra(ALARM_ID, trip.getAlarmId());
                            //////////////////////////////////////////////////////////////
//                            edit_intent.putExtra(DAY, trip.);
//                            edit_intent.putExtra(MONTH, trip.getNote());
//                            edit_intent.putExtra(YEAR, trip.getAlarmId());
//                            edit_intent.putExtra(HOUR, trip.getNote());
//                            edit_intent.putExtra(MINUTE, trip.getAlarmId());
                            getContext().startActivity(edit_intent);
                        }

                        if (item.getTitle().toString().contains("Cancel")) {
                            new AlertDialog.Builder(getContext())
                                    .setMessage("Are you sure you want to cancel this trip?")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                                            trip.setStatus("Canceled");
                                            historyRef.child(trip.getId()).setValue(trip);
                                            upComingRef.child(trip.getId()).removeValue();
                                            Alarm alarm = new Alarm(trip.getAlarmId());
                                            alarm.cancelAlarm(getContext());
                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton("No", null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }

                        if (item.getTitle().toString().contains("Delete")) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Delete Trip!")
                                    .setMessage("Are you sure you want to delete this trip?")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                            trip.setStatus("Deleted");
                                            historyRef.child(trip.getId()).setValue(trip);
                                            upComingRef.child(trip.getId()).removeValue();
                                            Alarm alarm = new Alarm(trip.getAlarmId());
                                            alarm.cancelAlarm(getContext());

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
