package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class listtrip extends AppCompatActivity {
    LinearLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtrip);
        mappingListTrip();
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        List<dataTrip> tripList = dbHelpers.getDetails();
        String[] tripArray = new String[tripList.size()];
        LinearLayout childLinearLayout = new LinearLayout(this);
        childLinearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        childLinearLayout.setLayoutParams(layoutParams);
        for (int i = 0; i < tripList.size(); i++) {
            dataTrip trip = tripList.get(i);
//            String tripInfo = "Trip ID: " + trip.getId() + "\n" +
//                    "Name: " + trip.getName() + "\n" +
//                    "Budget: " + trip.getBudget() + "\n" +
//                    "Destination: " + trip.getDesti() + "\n" +
//                    "Start Date: " + trip.getStartDate() + "\n" +
//                    "End Date: " + trip.getEndDate() + "\n" +
//                    "Description: " + trip.getDesc() + "\n";
//            tripArray[i] = tripInfo;
            TextView tripNameText = new TextView(this);
            tripNameText.setText(trip.getId() + " " + trip.getName() + "");
            childLinearLayout.addView(tripNameText);

        }
        parentLayout.addView(childLinearLayout);
    }
    protected void mappingListTrip(){
        parentLayout = (LinearLayout)findViewById(R.id.parentLayout);
    }
}