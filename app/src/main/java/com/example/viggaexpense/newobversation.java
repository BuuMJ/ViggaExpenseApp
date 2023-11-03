package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class newobversation extends AppCompatActivity {
    Button btnFinish, btnAddObvers;
    EditText edtObversation, edtTimeOfObversation, edtNotes;
    CheckBox checkReuire;
    dataTrip tripInfo;
    TextView hikeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newobversation);
        mapping();
        setupAddObservationButton();
        dataTrip tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");
        hikeName.setText(tripInfo.getName());
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setupAddObservationButton() {
        btnAddObvers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkReuire.isChecked()) {
                    String observation = edtObversation.getText().toString();
                    String timeOfObservation = edtTimeOfObversation.getText().toString();
                    String notes = edtNotes.getText().toString();
                    Observation newObservation = new Observation(observation, timeOfObservation, notes);
                    tripInfo.addObservation(newObservation);
                    Intent intent = new Intent(newobversation.this, detailtrip.class);
                    intent.putExtra("newObservation", newObservation);
                    intent.putExtra("tripInfo", tripInfo);
                    startActivity(intent);
                    Toast.makeText(newobversation.this, "Add Obversation Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(newobversation.this, "Please agree to the terms and information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void mapping(){
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnAddObvers = (Button)findViewById(R.id.btnAddObvers);
        edtObversation = (EditText)findViewById(R.id.edtObversation);
        edtTimeOfObversation = (EditText)findViewById(R.id.edtTimeOfObversation);
        edtNotes = (EditText)findViewById(R.id.edtNotes);
        checkReuire = (CheckBox)findViewById(R.id.checkReuire);
        hikeName = (TextView)findViewById(R.id.hikeName);
    }
}