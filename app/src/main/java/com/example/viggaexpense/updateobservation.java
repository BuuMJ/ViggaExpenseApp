package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class updateobservation extends AppCompatActivity {
    TextView edtTimeOfObversationEdit, observationName;
    EditText edtObversationEdit, edtNotesEdit;
    Button btnUpdateObvers, btnFinish;
    CheckBox checkUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateobservation);
        mapping();
        Observation observationInfo = (Observation) getIntent().getSerializableExtra("observationInfo");
        dataTrip tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");
        observationName.setText(observationInfo.getObservationTitle());
        edtObversationEdit.setText(observationInfo.getObservationTitle());
        edtNotesEdit.setText(observationInfo.getObservationNotes());
        edtTimeOfObversationEdit.setText(observationInfo.getObservationTime());
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        edtTimeOfObversationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateobservation.this,
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
                                edtTimeOfObversationEdit.setText(selectedDate);
                            }
                        },
                        y, m, d
                );
                datePickerDialog.show();
            }
        });
        btnUpdateObvers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date checkStartDate = dateFormatter.parse(edtTimeOfObversationEdit.getText().toString());
                    Date checkDateCreatedHike = dateFormatter.parse(tripInfo.getStartDate().toString());
                    Calendar calendar = Calendar.getInstance();

                    calendar.setTime(checkStartDate);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    checkStartDate = calendar.getTime();
                    Log.d("test", "checkRequired: " + checkStartDate);

                    calendar.setTime(checkDateCreatedHike);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    checkDateCreatedHike = calendar.getTime();
                    if(checkStartDate.before(checkDateCreatedHike)){
                        edtTimeOfObversationEdit.setError("Time of observation cannot be before start date");
                        Toast.makeText(updateobservation.this, "Time of observation cannot be before start date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(!checkUpdate.isChecked()){
                    checkUpdate.setError("");
                    Toast.makeText(updateobservation.this, "Please check the box to agree with changes", Toast.LENGTH_SHORT).show();
                }
                else{
                    String observationId = String.valueOf(observationInfo.getObservationId());
                    String tripId = String.valueOf(observationInfo.getObservationTripId());
                    String observationTitle = edtObversationEdit.getText().toString();
                    String observationTime = edtTimeOfObversationEdit.getText().toString();
                    String observationNotes = edtNotesEdit.getText().toString();
                    DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
                    dbHelpers.updateObservation(observationId, tripId, observationTitle, observationTime, observationNotes);
                    Toast.makeText(updateobservation.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent detailTrip = new Intent(updateobservation.this, detailtrip.class);
                    detailTrip.putExtra("tripInfo", tripInfo);
                    startActivity(detailTrip);
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
        observationName = (TextView)findViewById(R.id.observationName);
        edtTimeOfObversationEdit = (TextView)findViewById(R.id.edtTimeOfObversationEdit);
        edtObversationEdit = (EditText)findViewById(R.id.edtObversationEdit);
        edtNotesEdit = (EditText)findViewById(R.id.edtNotesEdit);
        btnUpdateObvers = (Button)findViewById(R.id.btnUpdateObvers);
        checkUpdate = (CheckBox)findViewById(R.id.checkUpdate);
        btnFinish = (Button)findViewById(R.id.btnFinish);
    }
}