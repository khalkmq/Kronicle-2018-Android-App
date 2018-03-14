package com.example.everb.kronicle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTimer extends Fragment{

    View view;

    // Constructor
    public FragmentTimer() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.timer_fragment,container,false);
        return view;
    }
}