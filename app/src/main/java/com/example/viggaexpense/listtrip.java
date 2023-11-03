package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class listtrip extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu, closeMenuIcon;
    LinearLayout nav_home, nav_newtrip, nav_listtrip, nav_about, parentLayout;
    TextView titleMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtrip);
        mappingListTrip();
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        List<dataTrip> tripList = dbHelpers.getDetails();
        String[] tripArray = new String[tripList.size()];
        for (int i = 0; i < tripList.size(); i++) {
            dataTrip trip = tripList.get(i);
            LinearLayout childLinearLayout = new LinearLayout(this);
            childLinearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0,12,0,12);
            childLinearLayout.setLayoutParams(layoutParams);
            childLinearLayout.setTag(trip);

            LinearLayout actionLinearLayout = new LinearLayout(this);
            actionLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams actionLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            actionLayoutParams.setMargins(0,12,0,0);
            actionLinearLayout.setLayoutParams(actionLayoutParams);

            TextView tripNameText = new TextView(this);
            TextView infoTripText = new TextView(this);
            TextView parkingText = new TextView(this);
            TextView startDateText = new TextView(this);
            TextView endDateText = new TextView(this);

            Button editHike = new Button(this);
            Button deleteHike = new Button(this);

            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            editParams.setMargins(0,0,4,0);
            editParams.weight = 1;

            LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            deleteParams.setMargins(4,0,0,0);
            deleteParams.weight = 1;

            editHike.setLayoutParams(editParams);
            deleteHike.setLayoutParams(deleteParams);

            actionLinearLayout.addView(editHike);
            actionLinearLayout.addView(deleteHike);

            tripNameText.setText((i + 1) + " - " + trip.getName());
            tripNameText.setTextSize(22);
            tripNameText.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
            tripNameText.setPadding(0,0,0,6);
            tripNameText.setTypeface(Typeface.DEFAULT_BOLD);

            infoTripText.setTextColor(getResources().getColor(android.R.color.black));
            infoTripText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location, 0, 0, 0);
            infoTripText.setText(" Destination: " + trip.getDesti());
            infoTripText.setTextSize(18);
            infoTripText.setPadding(0,6,0,6);

            parkingText.setTextColor(getResources().getColor(android.R.color.black));
            parkingText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.parking, 0, 0, 0);
            parkingText.setText(" Parking: " + trip.getParking());
            parkingText.setTextSize(18);
            parkingText.setPadding(0,6,0,6);

            SpannableString contentStartDateText = new SpannableString("Start Date: " + trip.getStartDate());
            ForegroundColorSpan colorContentStartDateText = new ForegroundColorSpan(Color.BLACK);
            contentStartDateText.setSpan(colorContentStartDateText, 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            startDateText.setPadding(0,6,0,6);
            startDateText.setText(contentStartDateText);
            startDateText.setTextSize(18);

            SpannableString contentEndDateText = new SpannableString("End Date: " + trip.getEndDate());
            ForegroundColorSpan colorContentEndDateText = new ForegroundColorSpan(Color.BLACK);
            contentEndDateText.setSpan(colorContentEndDateText, 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            endDateText.setText(contentEndDateText);
            endDateText.setPadding(0,0,0,6);
            endDateText.setTextSize(18);

            editHike.setText("Edit Hike");
            editHike.setTextSize(18);
            editHike.setBackground(getResources().getDrawable(R.drawable.edit_button));
            editHike.setTextColor(getResources().getColor(R.color.white));
            deleteHike.setText("Delete Hike");
            deleteHike.setBackground(getResources().getDrawable(R.drawable.delete_button));
            deleteHike.setTextColor(getResources().getColor(R.color.white));
            deleteHike.setTextSize(18);

            childLinearLayout.addView(tripNameText);
            childLinearLayout.addView(infoTripText);
            childLinearLayout.addView(parkingText);
            childLinearLayout.addView(startDateText);
            childLinearLayout.addView(endDateText);
            childLinearLayout.addView(actionLinearLayout);
            childLinearLayout.setBackground(getResources().getDrawable(R.drawable.item_style_background));

            childLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataTrip tripInfo = (dataTrip) view.getTag();
                    if(tripInfo != null){
                        Intent intent = new Intent(listtrip.this, detailtrip.class);
//                        List<Observation> observationListForTrip = getObservationListForTrip(tripInfo.getId());
                        intent.putExtra("tripInfo", tripInfo);
//                        intent.putExtra("observationListForTrip", (Serializable) observationListForTrip);
                        startActivity(intent);
                    }
                }
            });
            editHike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataTrip tripInfo = (dataTrip) childLinearLayout.getTag();
                    if (tripInfo != null) {
                        Intent intent = new Intent(listtrip.this, update.class);
                        intent.putExtra("tripInfo", tripInfo);
                        startActivity(intent);
                    }
                }
            });
            deleteHike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataTrip tripInfo = (dataTrip) childLinearLayout.getTag();
                    if (tripInfo != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(listtrip.this);
                        builder.setTitle("Confirm Delete")
                                .setMessage("Are you sure you want to delete this hike?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
                                        dbHelpers.deleteTrip(tripInfo.getId());
                                        parentLayout.removeView(childLinearLayout);
                                        Toast.makeText(listtrip.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });

            parentLayout.addView(childLinearLayout);
        }
        if (parentLayout.getChildCount() == 0) {
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("You have no trips listed");
            emptyTextView.setTextSize(18);
            emptyTextView.setTextColor(getResources().getColor(android.R.color.black));
            parentLayout.addView(emptyTextView);
        }

        nav_listtrip.setBackgroundColor(getResources().getColor(R.color.nav_color));
        titleMenu.setText("List Trip");
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        closeMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayout);
            }
        });
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(listtrip.this, home.class);
            }
        });
        nav_newtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(listtrip.this, newtrip.class);
            }
        });
        nav_listtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        nav_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(listtrip.this, about.class);
            }
        });
    }
    private List<Observation> getObservationListForTrip(int tripId) {
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        return dbHelpers.getObvervationDetails(tripId);
    }

    protected void mappingListTrip(){
        parentLayout = (LinearLayout)findViewById(R.id.parentLayout);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        menu = (ImageView)findViewById(R.id.menu);
        nav_home = (LinearLayout)findViewById(R.id.home);
        nav_newtrip = (LinearLayout)findViewById(R.id.newtrip);
        nav_listtrip = (LinearLayout)findViewById(R.id.listtrip);
        nav_about = (LinearLayout)findViewById(R.id.about);
        titleMenu = (TextView)findViewById(R.id.titleMenu);
        closeMenuIcon = (ImageView)findViewById(R.id.closeMenuIcon);
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}