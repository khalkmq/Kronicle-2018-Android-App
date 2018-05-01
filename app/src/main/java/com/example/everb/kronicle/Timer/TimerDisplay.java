package com.example.everb.kronicle.Timer;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.everb.kronicle.R;

import java.util.concurrent.TimeUnit;

public class TimerDisplay extends AppCompatActivity {

    private String titleTimer;
    private String focusTimer;
    private String shortTimer;
    private String longTimer;
    private String waitLong;

    private TextView timerTitle;
    private TextView timerFocus;
    private TextView timerShort;
    private TextView timerLong;
    private TextView longWait;
    private TextView currentTimer;
    private ProgressBar circleBar;
    private FloatingActionButton play;
    private FloatingActionButton reset;
    private ImageView focusArrow;
    private ImageView shortBreakArrow;
    private ImageView longBreakArrow;
    private CountDownTimer countDownTimer;
    private int longWaitChecker;

    private long timeCountInMilliSeconds = 60000;
    private int tracker = 3;


    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startViews();

        titleTimer = getIntent().getExtras().getString("title");
        focusTimer = getIntent().getExtras().getString("focus");
        shortTimer = getIntent().getExtras().getString("short");
        longTimer = getIntent().getExtras().getString("long");
        waitLong = getIntent().getExtras().getString("long_wait");

        timerTitle.setText(titleTimer);
        timerFocus.setText(focusTimer);
        timerShort.setText(shortTimer);
        timerLong.setText(longTimer);
        longWait.setText(waitLong);
        currentTimer.setText(focusTimer);
        longWaitChecker = Integer.parseInt(waitLong);

        reset.setVisibility(View.INVISIBLE);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void startViews() {
        timerTitle = findViewById(R.id.title_td);
        timerFocus = findViewById(R.id.focus_time_td);
        timerShort = findViewById(R.id.short_break_time_td);
        timerLong = findViewById(R.id.long_break_time_td);
        longWait = findViewById(R.id.long_break_wait_td);
        currentTimer = findViewById(R.id.current_timer_td);
        circleBar = findViewById(R.id.timer_circle_progress);
        play = findViewById(R.id.play_fab_td);
        reset = findViewById(R.id.reset_fav_td);
        focusArrow = findViewById(R.id.focus_arrow_td);
        shortBreakArrow = findViewById(R.id.short_break_arrow_td);
        longBreakArrow = findViewById(R.id.long_break_arrow_td);
    }

    private void timer() {
        if (timerStatus == TimerStatus.STOPPED) {
            timerStatus = TimerStatus.STARTED;
            setTimer();
            setCircleBar();
            setFabStart();
            startTimer();
        }

        else {
            timerStatus = TimerStatus.STOPPED;
            setFabPause();
        }
    }

    private void setTimer() {
        String splitter;

        // Focus
        if (tracker == 3) {
            tracker = 2;
            splitter = focusTimer;
            focusArrow.setColorFilter(getResources().getColor(R.color.colorComplement));
            shortBreakArrow.setColorFilter(getResources().getColor(R.color.colorBase));
            longBreakArrow.setColorFilter(getResources().getColor(R.color.colorBase));

        }

        // Short Break
        else if (tracker == 2) {
            tracker = 1;
            splitter = shortTimer;
            focusArrow.setColorFilter(getResources().getColor(R.color.colorBase));
            shortBreakArrow.setColorFilter(getResources().getColor(R.color.colorComplement));
            longBreakArrow.setColorFilter(getResources().getColor(R.color.colorBase));
        }

        else {
            longWaitChecker = longWaitChecker - 1;

            if (longWaitChecker >= 0) {
                tracker = 2;
                splitter = focusTimer;
                longWait.setText(String.valueOf(longWaitChecker));
                focusArrow.setColorFilter(getResources().getColor(R.color.colorComplement));
                shortBreakArrow.setColorFilter(getResources().getColor(R.color.colorBase));
                longBreakArrow.setColorFilter(getResources().getColor(R.color.colorBase));
            }

            else {
                tracker = 3;
                splitter = longTimer;
                longWait.setText(waitLong);
                longWaitChecker = Integer.parseInt(waitLong);
                focusArrow.setColorFilter(getResources().getColor(R.color.colorBase));
                shortBreakArrow.setColorFilter(getResources().getColor(R.color.colorBase));
                longBreakArrow.setColorFilter(getResources().getColor(R.color.colorComplement));
            }
        }

        String[] tokens = splitter.split(":");
        int hours_to_milli = Integer.parseInt(tokens[0]);
        int minutes_to_milli = Integer.parseInt(tokens[1]);

        timeCountInMilliSeconds = hours_to_milli * 3600000 + minutes_to_milli * 60000;
    }

    private void setCircleBar() {
        circleBar.setMax((int) timeCountInMilliSeconds / 1000);
        circleBar.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private void setFabStart() {
        reset.setVisibility(View.VISIBLE);
        play.setImageResource(R.drawable.icon_stop);
    }

    private void  setFabPause() {
        reset.setVisibility(View.INVISIBLE);
        play.setImageResource(R.drawable.icon_play);
        stopTimer();
    }

    private void reset() {
        tracker += 1;
        stopTimer();
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {

            @Override
            public void onTick(long millisecondsLeft) {
                currentTimer.setText(setTimeFormat(millisecondsLeft));
                circleBar.setProgress((int) (millisecondsLeft / 1000));
            }

            @Override
            public void onFinish() {

                currentTimer.setText(setTimeFormat(timeCountInMilliSeconds));
                // call to initialize the progress bar values
                setCircleBar();
                // hiding the reset icon
//                reset.setVisibility(View.GONE);
                // changing stop icon to start icon
                play.setImageResource(R.drawable.icon_play);
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
            }

        }.start();
        countDownTimer.start();
    }

    private void stopTimer() {

        countDownTimer.cancel();
    }

    // Formats Timer to Display Hours, Minutes, and Seconds
    private String setTimeFormat(long milliSeconds) {
        String timeUnits = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return timeUnits;
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
