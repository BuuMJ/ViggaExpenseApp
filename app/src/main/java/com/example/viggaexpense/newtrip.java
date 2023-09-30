package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class newtrip extends AppCompatActivity {
    TextView datePickerStartDate, datePickerEndDate;
    SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtrip);
        mapping();
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormatter.format(c.getTime());
        datePickerStartDate.setText(currentDate);

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
    }

    protected void mapping(){
        datePickerEndDate = (TextView)findViewById(R.id.datepickerend);
        datePickerStartDate = (TextView)findViewById(R.id.datepickerstart);
    }
}