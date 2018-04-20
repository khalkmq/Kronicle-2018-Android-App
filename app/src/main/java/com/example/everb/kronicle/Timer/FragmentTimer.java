package com.example.everb.kronicle.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.example.everb.kronicle.R;

public class FragmentTimer extends Fragment {

    View view;

    // Constructor
    public FragmentTimer() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_timer,container,false);

        return view;
    }
}
