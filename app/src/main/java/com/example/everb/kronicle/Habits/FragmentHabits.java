package com.example.everb.kronicle.Habits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.everb.kronicle.R;

public class FragmentHabits extends Fragment{

    private View view;

    // Constructor
    public FragmentHabits() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_habits, container,false);

        return view;
    }
}