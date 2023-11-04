package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class newtrip extends AppCompatActivity {
    TextView datePickerStartDate, datePickerEndDate;
    SimpleDateFormat dateFormatter;
    Button btnCreateTrip, btnFinish;
    CheckBox checkReuire;
    EditText edtTripName, edtDesti, edtDesc, edtLength, edtBudget;
    Spinner edtParking, edtLevel;
    DrawerLayout drawerLayout;
    ImageView menu, closeMenuIcon;
    LinearLayout nav_home, nav_newtrip, nav_listtrip, nav_about;
    TextView titleMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtrip);
        mapping();
        ArrayAdapter<CharSequence> parkingAdapter = ArrayAdapter.createFromResource(this, R.array.parking_options, android.R.layout.simple_spinner_item);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this, R.array.level_options, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtParking.setAdapter(parkingAdapter);
        edtLevel.setAdapter(levelAdapter);

        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int de = c.get(Calendar.DAY_OF_MONTH ) + 2;
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormatter.format(c.getTime());
        datePickerStartDate.setText(currentDate);

        c.add(Calendar.DAY_OF_MONTH,2);
        String endDate = dateFormatter.format(c.getTime());
        datePickerEndDate.setText(endDate);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        datePickerStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        newtrip.this,
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
                                datePickerStartDate.setText(selectedDate);
                            }
                        },
                        y, m, d
                );
                datePickerDialog.show();
            }
        });
        datePickerEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        newtrip.this,
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
                                datePickerEndDate.setText(selectedDate);
                            }
                        },
                        y, m, de
                );
                datePickerDialog.show();
            }
        });
        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRequired();

            }
        });
        nav_newtrip.setBackgroundColor(getResources().getColor(R.color.nav_color));
        titleMenu.setText("Vigga-Hike");
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
                redirectActivity(newtrip.this, home.class);
            }
        });
        nav_newtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();

            }
        });
        nav_listtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(newtrip.this, listtrip.class);
            }
        });
        nav_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(newtrip.this, about.class);
            }
        });
    }
    protected void checkRequired(){
        if(edtTripName.getText().toString().equals("")){
            edtTripName.setError("Please fill in your trip name");
        }
        else if(edtDesti.getText().toString().equals("")){
            edtDesti.setError("Please fill in your Destination");
        }
        else if(edtLength.getText().toString().equals("")){
            edtLength.setError("Please fill in your Length the hike");
        }
        else if(edtBudget.getText().toString().equals("")){
            edtBudget.setError("Please fill in your Budget");
        }
        else if(edtLevel.getSelectedItem().toString().equals("Select Level")){
            ((TextView)edtLevel.getSelectedView()).setError("Please select the Level option");
        }
        else if(edtParking.getSelectedItem().toString().equals("Select Parking option")){
            ((TextView)edtParking.getSelectedView()).setError("Please select the parking option");
        }
        else if(edtDesc.getText().toString().equals("")){
            edtDesc.setError("Please fill in your Description");
        }
        else if(!checkReuire.isChecked()){
            checkReuire.setError("");
            Toast.makeText(newtrip.this, "Please check the box to agree to the terms", Toast.LENGTH_SHORT).show();
        }
        else{
            createNewTrip();
            Toast.makeText(newtrip.this, "The new trip has been successfully created", Toast.LENGTH_SHORT).show();
            Intent listTrip = new Intent(this, listtrip.class);
            startActivity(listTrip);
        }
    }
    private void  createNewTrip(){
        DatabaseHelpers dbHelper = new DatabaseHelpers(getApplicationContext());
        String name = edtTripName.getText().toString();
        String level = edtLevel.getSelectedItem().toString();
        String desti = edtDesti.getText().toString();
        String start_date = datePickerStartDate.getText().toString();
        String end_date = datePickerEndDate.getText().toString();
        String desc = edtDesc.getText().toString();
        String parking = edtParking.getSelectedItem().toString();
        String length = edtLength.getText().toString();
        String budget = edtBudget.getText().toString();
        long tripID = dbHelper.inserDetails(name, level, desti, start_date, end_date, desc, parking, length, budget);
    }
    protected void mapping(){
        datePickerEndDate = (TextView)findViewById(R.id.datepickerend);
        datePickerStartDate = (TextView)findViewById(R.id.datepickerstart);
        btnCreateTrip = (Button)findViewById(R.id.btnCrateTrip);
        checkReuire = (CheckBox)findViewById(R.id.checkReuire);
        edtLevel = (Spinner)findViewById(R.id.edtLevel);
        edtDesti = (EditText)findViewById(R.id.edtDesti);
        edtDesc = (EditText)findViewById(R.id.edtDesc);
        edtTripName = (EditText)findViewById(R.id.edtTripName);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        menu = (ImageView)findViewById(R.id.menu);
        nav_home = (LinearLayout)findViewById(R.id.home);
        nav_newtrip = (LinearLayout)findViewById(R.id.newtrip);
        nav_listtrip = (LinearLayout)findViewById(R.id.listtrip);
        nav_about = (LinearLayout)findViewById(R.id.about);
        titleMenu = (TextView)findViewById(R.id.titleMenu);
        closeMenuIcon = (ImageView)findViewById(R.id.closeMenuIcon);
        edtLength = (EditText)findViewById(R.id.edtLength);
        edtParking = (Spinner)findViewById(R.id.edtParking);
        edtBudget = (EditText)findViewById(R.id.edtBudget);
        btnFinish = (Button)findViewById(R.id.btnFinish);
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
