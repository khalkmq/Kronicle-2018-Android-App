package com.example.everb.kronicle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.Toast;

public class SignIn extends Fragment {

    // Design Variables
    View view;

    // Database variables
    private SQLiteDatabase theDB;

    // Constructor
    public SignIn() {}

    /** onCreate **/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_in, container, false);

        // Get a ASyncWritable database
        UserDatabase.getInstance(getActivity().getApplicationContext()).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });

        return view;
    }

    /** On Resume **/
    @Override
    public void onResume() {
        super.onResume();
    }

    /** When Activity is paused **/
    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }
}
