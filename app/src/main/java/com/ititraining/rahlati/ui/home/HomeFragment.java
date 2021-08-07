package com.ititraining.rahlati.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ititraining.rahlati.MainActivity;
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.SetTripActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private String tripName, startPoint, endPoint;
    public static ArrayList<UpComingTrips> arrayList;
    public static UpComingTrips upComingTrips;
    public static ComingTripsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        TextView textViewHistory = view.findViewById(R.id.text_home);
//        textViewHistory.setText("This is Home Fragment");

        ListView listView = view.findViewById(R.id.home_listView_fragment);
        arrayList = new ArrayList<>();
        adapter = new ComingTripsAdapter(getContext(), arrayList);
        listView.setAdapter(adapter);

//        if(listView != null){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SetTripActivity.class);
                startActivity(intent);
            }
        });
//        }

        return view;
    }
}