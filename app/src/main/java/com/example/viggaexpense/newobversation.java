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

    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Locale;

    public class newobversation extends AppCompatActivity {
        Button btnFinish, btnAddObvers;
        EditText edtObversation, edtNotes;
        CheckBox checkReuire;
        dataTrip tripInfo;
        TextView hikeName, edtTimeOfObversation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_newobversation);
            mapping();
            tripInfo = (dataTrip) getIntent().getSerializableExtra("tripInfo");
            hikeName.setText(tripInfo.getName());
            btnAddObvers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkRequire();
                }
            });
            btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = dateFormatter.format(c.getTime());
            edtTimeOfObversation.setText(currentDate);
            edtTimeOfObversation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            newobversation.this,
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
                                    edtTimeOfObversation.setText(selectedDate);
                                }
                            },
                            y, m, d
                    );
                    datePickerDialog.show();
                }
            });
        }
        public void checkRequire(){
            if(edtObversation.getText().toString().equals("")){
                edtObversation.setError("Please fill obversation title");
            }
            else if(edtTimeOfObversation.getText().toString().equals("")){
                edtObversation.setError("Please fill time of the obversation");
            }
            else if(!checkReuire.isChecked()){
                checkReuire.setError("Please tick the condition box");
            }
            else{
                createObversation();
                Intent backToDetail = new Intent(newobversation.this, detailtrip.class);
                backToDetail.putExtra("tripInfo", tripInfo);
                startActivity(backToDetail);
            }
        }
        public void createObversation(){
            DatabaseHelpers dbHelpers = new DatabaseHelpers(getApplicationContext());
            int tripId = tripInfo.getId();
            String observationTitle = edtObversation.getText().toString();
            String observationTime = edtTimeOfObversation.getText().toString();
            String observationNotes = edtNotes.getText().toString();
            long observationId = dbHelpers.inserObservation(tripId , observationTitle, observationTime, observationNotes);
        }
        protected void mapping(){
            btnFinish = (Button)findViewById(R.id.btnFinish);
            btnAddObvers = (Button)findViewById(R.id.btnAddObvers);
            edtObversation = (EditText)findViewById(R.id.edtObversation);
            edtTimeOfObversation = (TextView)findViewById(R.id.edtTimeOfObversation);
            edtNotes = (EditText)findViewById(R.id.edtNotes);
            checkReuire = (CheckBox)findViewById(R.id.checkReuire);
            hikeName = (TextView)findViewById(R.id.hikeName);
        }
    }