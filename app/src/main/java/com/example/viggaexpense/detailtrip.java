package com.example.viggaexpense;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class detailtrip extends AppCompatActivity {
    TextView hikeDesti, difficult, length, duration, hikeName, txtBudget, description;
    Button btnFinish, btnAddMore;
    LinearLayout containerDetails;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailtrip);
        mapping();
        dataTrip tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");

        hikeDesti.setText(tripInfo.getDesti());
        hikeName.setText(tripInfo.getName());

        String budgetText = ("Total Budget: " + tripInfo.getBudget() +"$");
        SpannableStringBuilder budgetBuilder = new SpannableStringBuilder(budgetText);
        int startBudget = 12;
        int endBudget = startBudget + (budgetText.length() - startBudget);
        budgetBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startBudget, endBudget, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtBudget.setText(budgetBuilder);

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
            NumberFormat format = new DecimalFormat("0.#");
            lengthText = "Total Distance: " + format.format(lengthInKm) + "KM";
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
        String durationText = "Durations: " + textDuration;
        SpannableStringBuilder durationBuilder = new SpannableStringBuilder(durationText);
        int startDuration = 9;
        int endDuration = startDuration + (durationText.length() - startDuration);
        durationBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startDuration, endDuration, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        duration.setText(durationBuilder);

        if(tripInfo.getDesc().toString().equals("")){
            description.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams marginBottom = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            marginBottom.setMargins(20,8,20,20);
            marginBottom.topToBottom = R.id.length;
            marginBottom.bottomToBottom = R.id.card_detail;
            marginBottom.startToStart = R.id.card_detail;
            marginBottom.endToEnd = R.id.card_detail;
            duration.setLayoutParams(marginBottom);
        }
        else{
            description.setVisibility(View.VISIBLE);
            String descriptionText = "Description: " + "\n" + tripInfo.getDesc();
            SpannableStringBuilder descriptionBuilder = new SpannableStringBuilder(descriptionText);
            int startDesc = 12;
            int endDesc = startDesc + (descriptionText.length() - startDesc);
            Log.e("endDesc", "onCreate: " + endDesc);
            descriptionBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startDesc, endDesc, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            description.setText(descriptionBuilder);
        }

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
        DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
        List<Observation> observationList = dbHelpers.getObvervationDetails(tripInfo.getId());
        Log.d("test", "onCreate: " + observationList);

        for (Observation observation : observationList) {
            LinearLayout containerLayout = new LinearLayout(this);
            containerLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            containerParams.setMargins(0,20,0,20);
            containerLayout.setLayoutParams(containerParams);
            containerLayout.setTag(observation);

            LinearLayout childLinearLayout = new LinearLayout(this);
            childLinearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0,12,0,0);
            childLinearLayout.setLayoutParams(layoutParams);
            childLinearLayout.setBackground(getDrawable(R.drawable.background_obversation));

            LinearLayout timeLayout = new LinearLayout(this);
            timeLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams timeLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            timeLayoutParams.setMargins(0,0,0,12);
            timeLayout.setLayoutParams(timeLayoutParams);


            LinearLayout actionObservationLayout = new LinearLayout(this);
            LinearLayout.LayoutParams actionObservationLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            actionObservationLayout.setOrientation(LinearLayout.HORIZONTAL);
            actionObservationLayout.setPadding(0,8,0,8);
            actionObservationLayout.setLayoutParams(actionObservationLayoutParams);

            TextView observationTitle = new TextView(this);
            TextView observationTime = new TextView(this);
            TextView observationNotes = new TextView(this);

            Button editObservation = new Button(this);
            Button deleteObservation = new Button(this);

            LinearLayout.LayoutParams marginEditParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    120
            );
            marginEditParams.setMargins(0,12,12,0);

            editObservation.setText("Edit");
            editObservation.setBackground(getDrawable(R.drawable.edit_button));
            editObservation.setGravity(Gravity.CENTER);
            editObservation.setTextSize(16);
            editObservation.setTextColor(getColor(R.color.white));
            editObservation.setLayoutParams(marginEditParams);

            LinearLayout.LayoutParams marginDeleteParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    120
            );
            marginDeleteParams.setMargins(12,12,0,0);
            deleteObservation.setText("Delete");
            deleteObservation.setBackground(getDrawable(R.drawable.delete_button));
            deleteObservation.setGravity(Gravity.CENTER);
            deleteObservation.setTextSize(16);
            deleteObservation.setTextColor(getColor(R.color.white));
            deleteObservation.setLayoutParams(marginDeleteParams);

            editObservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Observation observationInfo = (Observation) containerLayout.getTag();
                    Intent intent = new Intent(detailtrip.this, updateobservation.class);
                    intent.putExtra("tripInfo", tripInfo);
                    intent.putExtra("observationInfo", observationInfo);
                    startActivity(intent);
                }
            });

            deleteObservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(detailtrip.this);
                    builder.setTitle("Confirm Delete")
                            .setMessage("Are you sure you want to delete this observation? \n" + "(" + observation.getObservationTitle() + ")")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
                                    dbHelpers.deleteObversation(observation.getObservationId());
                                    containerLayout.removeView(timeLayout);
                                    containerLayout.removeView(childLinearLayout);
                                    Toast.makeText(detailtrip.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
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
            });

            observationTitle.setText(observation.getObservationTitle());
            observationTitle.setTextColor(getColor(R.color.black));
            observationTitle.setTextSize(20);

            observationTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.timeobversation, 0, 0, 0);
            observationTime.setText(" " + observation.getObservationTime());
            observationTime.setTextSize(18);
            observationTime.setTextColor(getColor(R.color.greyColor));

            observationNotes.setText(observation.getObservationNotes());
            observationNotes.setTextColor(getColor(R.color.greyColor));
            observationNotes.setTextSize(16);

            actionObservationLayout.addView(editObservation);
            actionObservationLayout.addView(deleteObservation);

            timeLayout.addView(observationTime);

            childLinearLayout.addView(observationTitle);
            childLinearLayout.addView(observationNotes);
            childLinearLayout.addView(actionObservationLayout);

            containerLayout.addView(timeLayout);
            containerLayout.addView(childLinearLayout);

            containerDetails.addView(containerLayout);
        }
    }
    protected void onResume() {
        super.onResume();
    }
    protected void mapping(){
        hikeDesti = (TextView)findViewById(R.id.hikeDesti);
        difficult = (TextView)findViewById(R.id.difficult);
        length = (TextView)findViewById(R.id.length);
        duration = (TextView)findViewById(R.id.duration);
        hikeName = (TextView)findViewById(R.id.hikeName);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnAddMore = (Button)findViewById(R.id.btnAddMore);
        containerDetails = (LinearLayout)findViewById(R.id.containerDetails);
        txtBudget = (TextView)findViewById(R.id.txtBudget);
        description = (TextView)findViewById(R.id.description);
    }
}