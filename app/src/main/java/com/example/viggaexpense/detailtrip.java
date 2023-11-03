package com.example.viggaexpense;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class detailtrip extends AppCompatActivity {
    TextView hikeDesti, difficult, length, duration, hikeName;
    Button btnFinish, btnAddMore;
    ScrollView containerDetails;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailtrip);
        mapping();
        dataTrip tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");
        Observation newObservation = (Observation) getIntent().getSerializableExtra("newObservation");
        hikeDesti.setText(tripInfo.getDesti());
        hikeName.setText(tripInfo.getName());
        String difficultyText = "Difficulty: " + tripInfo.getLevel();
        SpannableStringBuilder difficultyBuilder = new SpannableStringBuilder(difficultyText);
        int startLevel = 11;
        int endLevel = startLevel + (difficultyText.length() - startLevel);
        Log.d("aaaa", "a a " + endLevel);
        difficultyBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startLevel, endLevel, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        difficult.setText(difficultyBuilder);
        int metterToKillometter = Integer.parseInt(tripInfo.getLength());
        String lengthText;
        if(metterToKillometter < 1000){
            lengthText = "Total Distance: " + tripInfo.getLength() + " Meters";
        }
        else{
            double lengthInKm = metterToKillometter / 1000.0;
            lengthText = "Total Distance: " + lengthInKm + " KM";
        }
        SpannableStringBuilder lengthBuilder = new SpannableStringBuilder(lengthText);
        int startLength = 15;
        int endLength = startLength + (lengthText.length() - startLength);
        lengthBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startLength, endLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        length.setText(lengthBuilder);

        String start = tripInfo.getStartDate();
        String end = tripInfo.getEndDate();
        if (start.length() < 10) {
            start = start.substring(0, 8) + "0" + start.substring(8);
        }
        if (end.length() < 10) {
            end = end.substring(0, 8) + "0" + end.substring(8);
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
        String durationText = "Durations: " + textDuration;
        SpannableStringBuilder durationBuilder = new SpannableStringBuilder(durationText);
        int startDuration = 9;
        int endDuration = startDuration + (durationText.length() - startDuration);
        durationBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startDuration, endDuration, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        duration.setText(durationBuilder);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(detailtrip.this,listtrip.class);
                startActivity(back);
            }
        });
        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMore = new Intent(detailtrip.this, newobversation.class);
                addMore.putExtra("tripInfo", tripInfo);
                startActivity(addMore);
            }
        });
        TextView obversationTitle = new TextView(this);
        obversationTitle.setText(newObservation.getTimeOfObservation());
        containerDetails.addView(obversationTitle);
    }
    protected void mapping(){
        hikeDesti = (TextView)findViewById(R.id.hikeDesti);
        difficult = (TextView)findViewById(R.id.difficult);
        length = (TextView)findViewById(R.id.length);
        duration = (TextView)findViewById(R.id.duration);
        hikeName = (TextView)findViewById(R.id.hikeName);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnAddMore = (Button)findViewById(R.id.btnAddMore);
        containerDetails = (ScrollView)findViewById(R.id.containerDetails);
    }
}