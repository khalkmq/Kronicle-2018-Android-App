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
    View view;

    // Database variables
    private SQLiteDatabase theDB;
    private long rowid;
    public static final String TAG = "LandingPage";


    private Button buttonGuest;


    public SignIn() {

    }

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_in, container, false);

        // Get a ASyncWritable database
        // Replaced this with get activity
        UserDatabase.getInstance(getActivity().getApplicationContext()).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });

        buttonGuest = view.findViewById(R.id.guest_sign_in_button_si);

        buttonGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates Guest profile automatically, and redirects to MainActivity
                ContentValues values = new ContentValues();
                values.put("username", "guest");
                values.put("password", "guest");
                values.put("email", "guest");
                values.put("birthdate", "guest");
                long newRowId = theDB.insert("offlineUsers", null, values);

                // Welcome the user!
                Toast.makeText(getActivity(), getString(R.string.guest_toast), Toast.LENGTH_LONG).show();

                // Go to the Main Page
                startActivity(new Intent(getActivity(), MainActivity.class));

                // Finish this activity
                getActivity().finish();
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
