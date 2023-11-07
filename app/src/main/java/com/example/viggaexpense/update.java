package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class update extends AppCompatActivity {
    EditText edtTripNameEdit, edtDestiEdit, edtLengthEdit, edtBudgetEdit, edtDescEdit;
    Spinner edtLevelEdit, edtParkingEdit;
    TextView datepickerstartEdit, datepickerendEdit, hikeName;
    CheckBox checkUpdate;
    Button btnUpdate, btnFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mapping();
        dataTrip tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");
        hikeName.setText(tripInfo.getName());
        edtTripNameEdit.setText(tripInfo.getName());
        edtDestiEdit.setText(tripInfo.getDesti());
        edtLengthEdit.setText(tripInfo.getLength());
        edtBudgetEdit.setText(tripInfo.getBudget());
        edtDescEdit.setText(tripInfo.getDesc());

        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this, R.array.level_options, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtLevelEdit.setAdapter(levelAdapter);
        String selectedLevelOption = tripInfo.getLevel();
        int levelPosition = levelAdapter.getPosition(selectedLevelOption);
        edtLevelEdit.setSelection(levelPosition);

        ArrayAdapter<CharSequence> parkingAdapter = ArrayAdapter.createFromResource(this, R.array.parking_options, android.R.layout.simple_spinner_item);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtParkingEdit.setAdapter(parkingAdapter);
        String selectedParkingOption = tripInfo.getParking();
        int parkingPosition = parkingAdapter.getPosition(selectedParkingOption);
        Log.e("aaaaa", "onCreate: " + parkingPosition);
        edtParkingEdit.setSelection(parkingPosition);

        datepickerstartEdit.setText(tripInfo.getStartDate());
        datepickerendEdit.setText(tripInfo.getEndDate());
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        datepickerstartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        update.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                datepickerstartEdit.setText(selectedDate);
                            }
                        },
                        y, m, d
                );
                datePickerDialog.show();
            }
        });
        datepickerendEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        update.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                datepickerendEdit.setText(selectedDate);
                            }
                        },
                        y, m, d
                );
                datePickerDialog.show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkUpdate.isChecked()){
                    checkUpdate.setError("");
                    Toast.makeText(update.this, "Please check the box to agree with changes", Toast.LENGTH_SHORT).show();
                }
                else{
                    String tripId = String.valueOf(tripInfo.getId());
                    String name = edtTripNameEdit.getText().toString();
                    String level = edtLevelEdit.getSelectedItem().toString();
                    String desti = edtDestiEdit.getText().toString();
                    String startDate = datepickerstartEdit.getText().toString();
                    String endDate = datepickerendEdit.getText().toString();
                    String desc = edtDescEdit.getText().toString();
                    String parking = edtParkingEdit.getSelectedItem().toString();
                    String length = edtLengthEdit.getText().toString();
                    String budget = edtBudgetEdit.getText().toString();
                    String checkDesc;
                    if(desc.equals("")){
                        checkDesc = "null";
                    }
                    else {
                        checkDesc = desc;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(update.this);
                    builder.setTitle("Confirm Infomation")
                            .setMessage("Name Of Hike: " + name + "\n" +
                                    "\n" +
                                    "Destination: " + desti + "\n" +
                                    "\n" +
                                    "Budget: " + budget + "$" + "\n" +
                                    "\n" +
                                    "Departure Day: " + startDate + "\n" +
                                    "\n" +
                                    "End Day: " + endDate + "\n" +
                                    "\n" +
                                    "Distance: " + length + "M" + "\n" +
                                    "\n" +
                                    "Difficulty: " + level + "\n" +
                                    "\n" +
                                    "Parking Lot: " + parking + "\n" +
                                    "\n" +
                                    "Description: " + checkDesc)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
                                    dbHelpers.updateTrip(tripId, name, level, desti, startDate, endDate, desc, parking, length, budget);
                                    Toast.makeText(update.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent listTrip = new Intent(update.this, listtrip.class);
                                    startActivity(listTrip);
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
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    protected void mapping(){
        edtTripNameEdit = (EditText)findViewById(R.id.edtTripNameEdit);
        edtDestiEdit = (EditText)findViewById(R.id.edtDestiEdit);
        edtLengthEdit = (EditText)findViewById(R.id.edtLengthEdit);
        edtBudgetEdit = (EditText)findViewById(R.id.edtBudgetEdit);
        edtDescEdit = (EditText)findViewById(R.id.edtDescEdit);
        edtLevelEdit = (Spinner)findViewById(R.id.edtLevelEdit);
        edtParkingEdit = (Spinner)findViewById(R.id.edtParkingEdit);
        datepickerstartEdit = (TextView)findViewById(R.id.datepickerstartEdit);
        datepickerendEdit = (TextView)findViewById(R.id.datepickerendEdit);
        checkUpdate = (CheckBox)findViewById(R.id.checkUpdate);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        hikeName = (TextView)findViewById(R.id.hikeName);
        btnFinish = (Button)findViewById(R.id.btnFinish);
    }
}