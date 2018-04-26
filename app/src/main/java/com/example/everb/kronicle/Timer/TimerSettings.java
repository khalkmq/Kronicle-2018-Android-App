package com.example.everb.kronicle.Timer;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.everb.kronicle.R;

import java.util.Calendar;

public class TimerSettings extends AppCompatActivity{

    private final AppCompatActivity activity = TimerSettings.this;

    private EditText titleTimer;
    private EditText focusTimer;
    private EditText shortTimer;
    private EditText longTimer;
    private EditText longWaitTimer;
    private Button submitTimer;
    private TimerDatabase timerDatabase;
    private TimerData timerData;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int getHour;
    int getMinutes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        timerFunction();
    }

    private void timerFunction() {
        timerDatabase = new TimerDatabase(activity);
        timerData = new TimerData();

        titleTimer = findViewById(R.id.title_ts);
        focusTimer = findViewById(R.id.focus_ts);
        shortTimer = findViewById(R.id.short_break_ts);
        longTimer = findViewById(R.id.long_break_ts);
        longWaitTimer = findViewById(R.id.wait_long_break_ts);
        submitTimer = findViewById(R.id.submit_ts);

        focusTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock(focusTimer);
            }
        });





        submitTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timerData.setTitle(titleTimer.getText().toString().trim());
                timerData.setFocus_duration(focusTimer.getText().toString().trim());
                timerData.setShort_break_duration(shortTimer.getText().toString().trim());
                timerData.setLong_break_duration(longTimer.getText().toString().trim());
                timerData.setLong_break_wait(longWaitTimer.getText().toString().trim());


                timerDatabase.addTimer(timerData);


                Toast.makeText(getApplicationContext(), "New Timer Added", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void clock (final EditText text) {
        calendar = Calendar.getInstance();
        getHour = calendar.get(Calendar.HOUR_OF_DAY);
        getMinutes = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(TimerSettings.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                text.setText(String.format("%02d:%02d", hourOfDay, minutes));
            }
        }, getHour, getMinutes, true);

        timePickerDialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (getParentActivityIntent() == null) {
                    onBackPressed();
                }

                else {
                    NavUtils.navigateUpFromSameTask(this);
                }

                return true;

                default: return super.onOptionsItemSelected(item);
        }
    }
}
