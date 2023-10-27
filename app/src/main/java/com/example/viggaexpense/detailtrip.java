package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class detailtrip extends AppCompatActivity {
    TextView tripNameDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailtrip);
        mapping();
        dataTrip tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");
        tripNameDetail.setText(tripInfo.getName());
    }
    protected void mapping(){
        tripNameDetail = (TextView)findViewById(R.id.tripNameDetail);
    }
}