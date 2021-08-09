package com.ititraining.rahlati.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ititraining.rahlati.R;

import java.util.ArrayList;

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
                String startPoint = upComingTrips.getStartPoint();
                String endPoint = upComingTrips.getEndPoint();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+startPoint+"&daddr="+endPoint));
                getContext().startActivity(intent);
            }
        });

        TextView note = convertView.findViewById(R.id.note);
        note.setText("NOTE");

        TextView menu = convertView.findViewById(R.id.menu_icon);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Menu", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
