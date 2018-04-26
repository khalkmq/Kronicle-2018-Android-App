package com.example.everb.kronicle.Timer;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.everb.kronicle.R;

public class TimerSettings extends AppCompatActivity{

    private final AppCompatActivity activity = TimerSettings.this;

    private EditText titleTimer;
    private EditText focusTimerHours;
    private EditText focusTimerMinutes;
    private EditText shortTimerHours;
    private EditText shortTimerMinutes;
    private EditText longTimerHours;
    private EditText longTimerMinutes;
    private EditText longWaitTimer;
    private Button submitTimer;
    private TimerDatabase timerDatabase;
    private TimerData timerData;



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
        focusTimerHours = findViewById(R.id.focus_hours_ts);
        focusTimerMinutes = findViewById(R.id.focus_minutes_ts);
        shortTimerHours = findViewById(R.id.short_break_hours_ts);
        shortTimerMinutes = findViewById(R.id.short_break_minutes_ts);
        longTimerHours = findViewById(R.id.long_break_hours_ts);
        longTimerMinutes = findViewById(R.id.long_break_minutes_ts);
        longWaitTimer = findViewById(R.id.wait_long_break_ts);
        submitTimer = findViewById(R.id.submit_ts);

        submitTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (titleTimer.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "The title can't be empty", Toast.LENGTH_SHORT).show();
                }

                if (focusTimerHours.getText().length() == 0) {
                    focusTimerHours.setText("00");
                }

                else if (focusTimerHours.getText().length() == 1) {
                    focusTimerHours.setText("0" + focusTimerHours.getText());
                }

                if (focusTimerMinutes.getText().length() == 0) {
                    focusTimerMinutes.setText("00");
                }

                else if (focusTimerMinutes.getText().length() == 1) {
                    focusTimerMinutes.setText("0" + focusTimerMinutes.getText());
                }

                if (shortTimerHours.getText().length() == 0) {
                    shortTimerHours.setText("00");
                }

                else if (shortTimerHours.getText().length() == 1) {
                    shortTimerHours.setText("0" + shortTimerHours.getText());
                }

                if (shortTimerMinutes.getText().length() == 0) {
                    shortTimerMinutes.setText("00");
                }

                else if (shortTimerMinutes.getText().length() == 1) {
                    shortTimerMinutes.setText("0" + shortTimerMinutes.getText());
                }

                if (longTimerHours.getText().length() == 0) {
                    longTimerHours.setText("00");
                }

                else if (longTimerHours.getText().length() == 1) {
                    longTimerHours.setText("0" + longTimerHours.getText());
                }

                if (longTimerMinutes.getText().length() == 0) {
                    longTimerMinutes.setText("00");
                }

                else if (longTimerMinutes.getText().length() == 1) {
                    longTimerMinutes.setText("0" + longTimerMinutes.getText());
                }

                timerData.setTitle(titleTimer.getText().toString().trim());
                timerData.setFocus_duration(focusTimerHours.getText().toString().trim() + ":" + focusTimerMinutes.getText().toString().trim());
                timerData.setShort_break_duration(shortTimerHours.getText().toString().trim() + ":" + shortTimerMinutes.getText().toString().trim());
                timerData.setLong_break_duration(longTimerHours.getText().toString().trim() + ":" + longTimerMinutes.getText().toString().trim());
                timerData.setLong_break_wait(longWaitTimer.getText().toString().trim());

                timerDatabase.addTimer(timerData);

                Toast.makeText(getApplicationContext(), "New Timer Added", Toast.LENGTH_LONG).show();
                finish();
            }
        });
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
