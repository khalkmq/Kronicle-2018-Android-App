package com.example.everb.kronicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.support.annotation.Nullable;
import android.widget.Button;

public class FragmentNotes extends Fragment {

    View view;

    // Constructor
    public FragmentNotes() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_notes,container,false);
        return view;
    }
}
