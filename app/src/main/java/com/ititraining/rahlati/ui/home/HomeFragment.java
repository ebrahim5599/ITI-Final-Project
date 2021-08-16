
package com.ititraining.rahlati.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ititraining.rahlati.MainActivity;
import com.ititraining.rahlati.R;
import com.ititraining.rahlati.SetTripActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.ititraining.rahlati.MainActivity.upComingRef;


public class HomeFragment extends Fragment {

    private String tripName, startPoint, endPoint, allText;
    public static ArrayList<UpComingTrips> arrayList;
    public static UpComingTrips upComingTrips;
    public static ComingTripsAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        if(allText != null)
            Toast.makeText(getContext(), allText, Toast.LENGTH_SHORT).show();
    }



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listView = view.findViewById(R.id.home_listView_fragment);
        arrayList = new ArrayList<>();
        adapter = new ComingTripsAdapter(getContext(), arrayList);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startPoint = adapter.getItem(position).getStartPoint();
                endPoint = adapter.getItem(position).getEndPoint();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+startPoint+"&daddr="+endPoint));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        upComingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot n: snapshot.getChildren()){
                    UpComingTrips up = n.getValue(UpComingTrips.class);
                    arrayList.add(up);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setAllText(String allText){ this.allText = allText; }
}
/*
//            if (allText.contains(".")) {
//                // Split it.
//                String[] parts = allText.split("[.]");
//                String part1 = parts[0];
//                String part2 = parts[1];
//                edt_number.setText(part1);
//                edt_message.setText(part2);
//            }
 */

/*
Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
    Uri.parse("google.navigation:q=an+address+city"))
 */