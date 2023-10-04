package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class newtrip extends AppCompatActivity {
    TextView datePickerStartDate, datePickerEndDate;
    SimpleDateFormat dateFormatter;
    Button btnCreateTrip;
    CheckBox checkReuire;
    EditText edtTripName, edtBudget, edtDesti, edtDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtrip);
        mapping();
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int de = c.get(Calendar.DAY_OF_MONTH ) + 2;
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormatter.format(c.getTime());
        datePickerStartDate.setText(currentDate);
        c.add(Calendar.DAY_OF_MONTH,2);
        String endDate = dateFormatter.format(c.getTime());
        datePickerEndDate.setText(endDate);
        datePickerStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        newtrip.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
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
                                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
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
    }
    protected void checkRequired(){
        if(edtTripName.getText().toString().equals("")){
            edtTripName.setError("Please fill in your trip name");
        }
        else if(edtBudget.getText().toString().equals("")){
            edtBudget.setError("Please fill in your budget");
        }
        else if(edtDesti.getText().toString().equals("")){
            edtDesti.setError("Please fill in your Destination");
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
        String budget = edtBudget.getText().toString();
        String desti = edtDesti.getText().toString();
        String start_date = datePickerStartDate.getText().toString();
        String end_date = datePickerEndDate.getText().toString();
        String desc = edtDesc.getText().toString();
        long tripID = dbHelper.inserDetails(name, budget, desti, start_date, end_date, desc);
    }
    protected void mapping(){
        datePickerEndDate = (TextView)findViewById(R.id.datepickerend);
        datePickerStartDate = (TextView)findViewById(R.id.datepickerstart);
        btnCreateTrip = (Button)findViewById(R.id.btnCrateTrip);
        checkReuire = (CheckBox)findViewById(R.id.checkReuire);
        edtBudget = (EditText)findViewById(R.id.edtBudget);
        edtDesti = (EditText)findViewById(R.id.edtDesti);
        edtDesc = (EditText)findViewById(R.id.edtDesc);
        edtTripName = (EditText)findViewById(R.id.edtTripName);
    }
}