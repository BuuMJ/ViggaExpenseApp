package com.example.viggaexpense;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class listtrip extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu, closeMenuIcon;
    LinearLayout nav_home, nav_newtrip, nav_listtrip, nav_about, parentLayout, box_searchAdvance;
    TextView titleMenu, filter_search, dateSearchStart, dateSearchEnd;
    SearchView searchView;
    EditText searchDesti;
    SimpleDateFormat dateFormatter;
    private boolean checkClick = false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtrip);
        mappingListTrip();
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        List<dataTrip> tripList = dbHelpers.getDetails();
        String[] tripArray = new String[tripList.size()];
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String trip) {
                filterList(trip);
                return true;
            }
        });
        filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkClick){
                    showAvancedSearch();
                    checkClick = true;
                    filter_search.setText("Collapse");
                    filter_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collapseicon, 0);
                }
                else{
                    hideAvancedSearch();
                    checkClick = false;
                    filter_search.setText("Filter Search");
                    filter_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filtersearch, 0);
                }
            }
        });
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateSearchStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        listtrip.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String selectedDate;
                                if (dayOfMonth < 10){
                                    selectedDate = "0" + dayOfMonth + "/" + (month + 1) + "/" + year;
                                }
                                else {
                                    selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                }
                                dateSearchStart.setText(selectedDate);
                            }
                        },
                        y, m, d
                );
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dateSearchStart.setText("");
                    }
                });
                datePickerDialog.show();
            }
        });
        dateSearchEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        listtrip.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String selectedDate;
                                if (dayOfMonth < 10){
                                    selectedDate = "0" + dayOfMonth + "/" + (month + 1) + "/" + year;
                                }
                                else {
                                    selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                }
                                dateSearchEnd.setText(selectedDate);
                            }
                        },
                        y, m, d
                );
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dateSearchEnd.setText("");
                    }
                });
                datePickerDialog.show();
            }
        });
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
            TextView duration = new TextView(this);

            Button editHike = new Button(this);
            Button deleteHike = new Button(this);

            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            editParams.setMargins(0,12,4,0);
            editParams.weight = 1;

            LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            deleteParams.setMargins(4,12,0,0);
            deleteParams.weight = 1;

            editHike.setLayoutParams(editParams);
            deleteHike.setLayoutParams(deleteParams);

            actionLinearLayout.addView(editHike);
            actionLinearLayout.addView(deleteHike);

            tripNameText.setText(trip.getName());
            tripNameText.setTextSize(26);
            tripNameText.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
            tripNameText.setPadding(0,14,0,14);
            tripNameText.setTypeface(Typeface.DEFAULT_BOLD);

            infoTripText.setTextColor(getResources().getColor(android.R.color.black));
            infoTripText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.destiicon, 0, 0, 0);
            infoTripText.setText("  " + trip.getDesti());
            infoTripText.setGravity(Gravity.CENTER_VERTICAL);
            infoTripText.setTextSize(18);
            infoTripText.setPadding(0,14,0,14);

            parkingText.setTextColor(getResources().getColor(android.R.color.black));
            parkingText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.parkingicon, 0, 0, 0);
            parkingText.setText("  " + trip.getParking());
            parkingText.setGravity(Gravity.CENTER_VERTICAL);
            parkingText.setTextSize(18);
            parkingText.setPadding(0,14,0,14);

            startDateText.setTextColor(getResources().getColor(android.R.color.black));
            startDateText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.starticon, 0, 0, 0);
            startDateText.setText("  " + trip.getStartDate() + " - " + trip.getEndDate());
            startDateText.setGravity(Gravity.CENTER_VERTICAL);
            startDateText.setTextSize(18);
            startDateText.setPadding(0,14,0,14);

            String start = trip.getStartDate();
            String end = trip.getEndDate();
            if (start.length() < 10) {
                start = start.substring(0, 8) + "0" + start.substring(8);
            }
            if (end.length() < 10) {
                end = end.substring(0, 8) + "0" + end.substring(8);
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(start, dateFormatter);
            LocalDate endDate = LocalDate.parse(end, dateFormatter);
            long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            String durationDays = String.valueOf(days);
            String textDuration;
            if (days < 10){
                if(days <= 1){
                    textDuration = "0" + durationDays + " Day";
                }
                else {
                    textDuration = "0" + durationDays + " Days";
                }
            }
            else {
                textDuration = durationDays + " Days";
            }
            String durationText = "  " + textDuration;
            duration.setTextColor(getResources().getColor(R.color.black));
            duration.setCompoundDrawablesWithIntrinsicBounds(R.drawable.durationicon, 0, 0, 0);
            duration.setText(durationText);
            duration.setGravity(Gravity.CENTER_VERTICAL);
            duration.setTextSize(18);
            duration.setPadding(0,14,0,14);


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
            childLinearLayout.addView(duration);
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
                                .setMessage("Are you sure you want to delete this hike? \n" + "(" + tripInfo.getName() + ")")
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

    protected void filterList(String text) {
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        List<dataTrip> tripList = dbHelpers.getDetails();
        List<dataTrip> filteredList = new ArrayList<>();
        String searchTextLower = text.toLowerCase();
        for (dataTrip trip : tripList) {
            if (trip.getName().toLowerCase().contains(searchTextLower)) {
                boolean addToFilter = true;
                String searchDestiText = searchDesti.getText().toString().toLowerCase();
                if (!searchDestiText.isEmpty() && !trip.getDesti().toLowerCase().contains(searchDestiText)) {
                    addToFilter = false;
                }
                String dateSearchStartText = dateSearchStart.getText().toString().toLowerCase();
                if (addToFilter && !dateSearchStartText.isEmpty() && !trip.getStartDate().toLowerCase().contains(dateSearchStartText)) {
                    addToFilter = false;
                }
                String dateSearchEndText = dateSearchEnd.getText().toString().toLowerCase();
                if (addToFilter && !dateSearchEndText.isEmpty() && !trip.getEndDate().toLowerCase().contains(dateSearchEndText)) {
                    addToFilter = false;
                }
                if (addToFilter) {
                    filteredList.add(trip);
                }
            }
        }
        updateUI(filteredList);
    }

    protected void updateUI(List<dataTrip> trips) {
        parentLayout.removeAllViews();
        if (trips.isEmpty()) {
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("No trips matching your search");
            emptyTextView.setTextSize(18);
            emptyTextView.setTextColor(getResources().getColor(android.R.color.black));
            parentLayout.addView(emptyTextView);
            return;
        }
        for (int i = 0; i < trips.size(); i++) {
            dataTrip trip = trips.get(i);
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
            TextView duration = new TextView(this);

            Button editHike = new Button(this);
            Button deleteHike = new Button(this);

            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            editParams.setMargins(0,12,4,0);
            editParams.weight = 1;

            LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            deleteParams.setMargins(4,12,0,0);
            deleteParams.weight = 1;

            editHike.setLayoutParams(editParams);
            deleteHike.setLayoutParams(deleteParams);

            actionLinearLayout.addView(editHike);
            actionLinearLayout.addView(deleteHike);

            tripNameText.setText(trip.getName());
            tripNameText.setTextSize(26);
            tripNameText.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
            tripNameText.setPadding(0,14,0,14);
            tripNameText.setTypeface(Typeface.DEFAULT_BOLD);

            infoTripText.setTextColor(getResources().getColor(android.R.color.black));
            infoTripText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.destiicon, 0, 0, 0);
            infoTripText.setText("  " + trip.getDesti());
            infoTripText.setGravity(Gravity.CENTER_VERTICAL);
            infoTripText.setTextSize(18);
            infoTripText.setPadding(0,14,0,14);

            parkingText.setTextColor(getResources().getColor(android.R.color.black));
            parkingText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.parkingicon, 0, 0, 0);
            parkingText.setText("  " + trip.getParking());
            parkingText.setGravity(Gravity.CENTER_VERTICAL);
            parkingText.setTextSize(18);
            parkingText.setPadding(0,14,0,14);

            startDateText.setTextColor(getResources().getColor(android.R.color.black));
            startDateText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.starticon, 0, 0, 0);
            startDateText.setText("  " + trip.getStartDate() + " - " + trip.getEndDate());
            startDateText.setGravity(Gravity.CENTER_VERTICAL);
            startDateText.setTextSize(18);
            startDateText.setPadding(0,14,0,14);

            String start = trip.getStartDate();
            String end = trip.getEndDate();
            if (start.length() < 10) {
                start = start.substring(0, 8) + "0" + start.substring(8);
            }
            if (end.length() < 10) {
                end = end.substring(0, 8) + "0" + end.substring(8);
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(start, dateFormatter);
            LocalDate endDate = LocalDate.parse(end, dateFormatter);
            long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            String durationDays = String.valueOf(days);
            String textDuration;
            if (days < 10){
                if(days <= 1){
                    textDuration = "0" + durationDays + " Day";
                }
                else {
                    textDuration = "0" + durationDays + " Days";
                }
            }
            else {
                textDuration = durationDays + " Days";
            }
            String durationText = "  " + textDuration;
            duration.setTextColor(getResources().getColor(R.color.black));
            duration.setCompoundDrawablesWithIntrinsicBounds(R.drawable.durationicon, 0, 0, 0);
            duration.setText(durationText);
            duration.setGravity(Gravity.CENTER_VERTICAL);
            duration.setTextSize(18);
            duration.setPadding(0,14,0,14);


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
            childLinearLayout.addView(duration);
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
                                .setMessage("Are you sure you want to delete this hike? \n" + "(" + tripInfo.getName() + ")")
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
    }

    private List<Observation> getObservationListForTrip(int tripId) {
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        return dbHelpers.getObvervationDetails(tripId);
    }
    protected void showAvancedSearch(){
        box_searchAdvance.setVisibility(View.VISIBLE);
    }
    protected void hideAvancedSearch(){
        box_searchAdvance.setVisibility(View.GONE);
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
        searchView = (SearchView)findViewById(R.id.searchView);
        filter_search = (TextView)findViewById(R.id.filter_search);
        box_searchAdvance = (LinearLayout)findViewById(R.id.box_searchAdvance);
        searchDesti = (EditText)findViewById(R.id.searchDesti);
        dateSearchStart = (TextView)findViewById(R.id.dateSearchStart);
        dateSearchEnd = (TextView)findViewById(R.id.dateSearchEnd);
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