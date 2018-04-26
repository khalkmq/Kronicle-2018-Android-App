package com.example.everb.kronicle.Timer;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.everb.kronicle.R;

public class TimerDisplay extends AppCompatActivity {

    TextView timerTitle;
    TextView timerFocus;
    TextView timerShort;
    TextView timerLong;
    String longWait;

    private long timeCountInMilliSeconds = 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String titleTimer = getIntent().getExtras().getString("title");
        String focusTimer = getIntent().getExtras().getString("focus");
        String shortTimer = getIntent().getExtras().getString("short");
        String longTimer = getIntent().getExtras().getString("long");
        String waitLong = getIntent().getExtras().getString("long_wait");


        timerTitle = findViewById(R.id.title_tri);
        timerFocus = findViewById(R.id.focus_time_tri);
        timerShort = findViewById(R.id.short_break_time_tri);
        timerLong = findViewById(R.id.long_break_time_tri);

        timerTitle.setText(titleTimer);
        timerFocus.setText(focusTimer);
        timerShort.setText(shortTimer);
        timerLong.setText(longTimer);

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
