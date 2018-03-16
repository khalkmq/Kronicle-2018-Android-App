package com.example.everb.kronicle;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class FragmentTimer extends Fragment{

    private long start_time_milli = 600000;

    private TextView textCountDown;
    private Button buttonStart;
    private Button buttonReset;
    private ImageButton buttonSettings;

    private CountDownTimer mCountDownTimer;

    private boolean timerActive;

    private long timeLeftMilli = start_time_milli;


    View view;

    // Constructor
    public FragmentTimer() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.timer_fragment,container,false);

        textCountDown = view.findViewById(R.id.countdown_text_timer);

        buttonStart = view.findViewById(R.id.start_button_timer);

        buttonReset = view.findViewById(R.id.reset_button_timer);

        buttonSettings = view.findViewById(R.id.settings_button_timer);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (timerActive) {
                    pauseTimer();
                }

                else {
                    startTimer();
                }

            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();

        return view;
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(timeLeftMilli, 1000) {
            @Override
            public void onTick(long milliUntilFinished) {
                timeLeftMilli = milliUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerActive = false;
                buttonStart.setText(R.string.start_string);
                buttonStart.setVisibility(View.INVISIBLE);
                buttonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerActive = true;
        buttonStart.setTextColor(getResources().getColor(R.color.colorAccentThree));
        buttonStart.setText(R.string.pause_string);
        buttonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        timerActive = false;
        buttonStart.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        buttonStart.setText(R.string.start_string);
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeLeftMilli = start_time_milli;
        updateCountDownText();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStart.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int mins = (int) (timeLeftMilli / 1000) / 60;
        int secs = (int) (timeLeftMilli / 1000) % 60;

        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d", mins, secs);
        textCountDown.setText(timeLeft);
    }
}