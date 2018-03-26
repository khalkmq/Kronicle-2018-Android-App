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

    Button signUpTest;

    // Constructor
    public FragmentNotes() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.notes_fragment,container,false);

        signUpTest = view.findViewById(R.id.button_NF);

        // OnClick Listener for sign up button
        signUpTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // The Intent will take us to the sign up page
                startActivity(new Intent(getActivity(), IntroPage.class));
            }
        });





        return view;
}
}
