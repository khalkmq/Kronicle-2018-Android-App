package com.example.everb.kronicle.Timer;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.everb.kronicle.MainActivity;
import com.example.everb.kronicle.R;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_settings);

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
}
