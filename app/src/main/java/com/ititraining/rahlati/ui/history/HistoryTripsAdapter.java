package com.ititraining.rahlati.ui.history;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ititraining.rahlati.R;
import com.ititraining.rahlati.ui.home.UpComingTrips;

import java.util.ArrayList;

import static com.ititraining.rahlati.MainActivity.historyRef;
import static com.ititraining.rahlati.ui.history.HistoryFragment.historyAdapter;


public class HistoryTripsAdapter extends ArrayAdapter<UpComingTrips> {
    public HistoryTripsAdapter(@NonNull Context context, @NonNull ArrayList<UpComingTrips> upComingTripsHis) {
        super(context, 0, upComingTripsHis);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_container_history,
                    parent,false);
        }

        UpComingTrips upComingTrips = (UpComingTrips) getItem(position);

        TextView date = convertView.findViewById(R.id.date_view);
        date.setText(upComingTrips.getDate());

        TextView time = convertView.findViewById(R.id.time_view);
        time.setText(upComingTrips.getTime());

        TextView trip_title = convertView.findViewById(R.id.trip_title);
        trip_title.setText(upComingTrips.getTripName());

        TextView startPoint = convertView.findViewById(R.id.start_point);
        startPoint.setText(" "+upComingTrips.getStartPoint());

        TextView endPoint = convertView.findViewById(R.id.end_point);
        endPoint.setText(" "+upComingTrips.getEndPoint());

        TextView status = convertView.findViewById(R.id.status);
        status.setText(upComingTrips.getStatus());

        TextView trip_note = convertView.findViewById(R.id.trip_note);
        trip_note.setText("SHOW NOTES");
        trip_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryNotesActivity.class);
                intent.putExtra("ID",upComingTrips.getId());
                getContext().startActivity(intent);
            }
        });

        TextView delete_trip = convertView.findViewById(R.id.delete_trip);
        delete_trip.setText("DELETE TRIP");
        delete_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to delete this trip?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                historyRef.child(upComingTrips.getId()).removeValue();
                                historyAdapter.notifyDataSetChanged();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        LinearLayout linearLayout = convertView.findViewById(R.id.expand);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(parent, new AutoTransition());
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(parent, new AutoTransition());
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}
