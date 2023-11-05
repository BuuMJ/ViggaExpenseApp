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

import java.util.Calendar;

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