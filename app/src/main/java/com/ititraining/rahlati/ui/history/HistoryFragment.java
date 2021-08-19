package com.ititraining.rahlati.ui.history;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.ui.home.ComingTripsAdapter;
import com.ititraining.rahlati.ui.home.UpComingTrips;

import java.util.ArrayList;
import static com.ititraining.rahlati.MainActivity.historyRef;

public class HistoryFragment extends Fragment {

    public static ArrayList<UpComingTrips> historyArrayList;
    public static HistoryTripsAdapter historyAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView listView = view.findViewById(R.id.history_listView_fragment);
        historyArrayList = new ArrayList<>();
        historyAdapter = new HistoryTripsAdapter(getContext(), historyArrayList);
        listView.setAdapter(historyAdapter);
        listView.setDivider(null);

        Button showmap =view.findViewById(R.id.shoemap);
        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(view.getContext(), MapActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyArrayList.clear();
                for(DataSnapshot n: snapshot.getChildren()){
                    UpComingTrips up = n.getValue(UpComingTrips.class);
                    historyArrayList.add(up);
                }
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}