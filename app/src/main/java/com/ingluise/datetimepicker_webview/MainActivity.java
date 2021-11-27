package com.ingluise.datetimepicker_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText ti1, ti2;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ti1 = findViewById(R.id.input_fecha);
        ti2 = findViewById(R.id.input_hora);

        ti1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.input_fecha:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        ti2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.input_hora:
                        showTimePickerDialog();
                        break;
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                ti1.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                if (hour < 10) {
                    selectedTime = "0" + hour + ":" + minutes;
                    if (minutes < 10) {
                        selectedTime = "0" + hour + ":" + "0" + minutes;
                    }
                }
                else {
                    selectedTime = hour + ":" + minutes;
                    if (minutes < 10) {
                        selectedTime = hour + ":" + "0" + minutes;
                    }
                }

                ti2.setText(selectedTime);
            }
        });

        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}