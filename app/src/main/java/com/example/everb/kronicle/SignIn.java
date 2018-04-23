package com.example.everb.kronicle;

import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends Fragment {

    // Design Variables
    View view;
    // Database files
    SQLiteDatabase theDB;

    // Constructor
    public SignIn() {}


    /** onCreateView **/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate View
        view = inflater.inflate(R.layout.sign_in, container, false);

        // Assign button from view and onClickListener
        Button signInBtn = view.findViewById(R.id.sign_in_button_si);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            /** THIS CONTROLS WHAT HAPPENS WHEN SIGN-IN IS CLICKED **/
            @Override
            public void onClick(View v) {
                // Reference the objects on the sign up page
                EditText usernameText = view.findViewById(R.id.username_si);
                EditText passwordText = view.findViewById(R.id.password_si);

                // Get the user's input and save it on strings
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                // Check if any EditText boxes is empty
                if (username.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Username can not be empty", Toast.LENGTH_LONG).show(); return;
                }
                else if (password.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Password can not be empty", Toast.LENGTH_LONG).show(); return;
                }

                // Check if an account already exists
                String[] projection = {"username","password"};
                Cursor cursor = theDB.query("offlineUsers", projection, null, null, null, null, null);

                String truePass = "";
                //Check for any similar usernames and retrieve the password if exists
                boolean foundState = false;
                while (cursor.moveToNext()) {
                    // An account with similar username exists!, get the password
                    if (cursor.getString(cursor.getColumnIndexOrThrow("username")).equals(username)){
                        foundState = true;
                        truePass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        break;
                        }
                    }
                cursor.close();


                // If user does not exist, prompt re-entry
                if (foundState != true) {
                    Toast.makeText(getContext().getApplicationContext(), "This username does not exist", Toast.LENGTH_LONG).show(); return;
                }
                // If password is incorrect, prompt re-entry
                if(!password.equals(truePass)) {
                    Toast.makeText(getContext().getApplicationContext(), "This password is incorrect", Toast.LENGTH_LONG).show(); return;
                }
                // Otherwise, attempt a log-in
                else {
                    // Set Content
                    ContentValues values = new ContentValues();
                    values.put("loggedIn", true);
                    String[] whereArgs ={username};
                    theDB.update("offlineUsers", values,"username = ?", whereArgs);

                    // Welcome the user!
                    Toast.makeText(getContext().getApplicationContext(), "Login successful, Welcome " + username + "!", Toast.LENGTH_LONG).show();

                    // Go to the Main Page
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                }

            }) ;

        return view;
    }

    /** On Resume **/
    @Override
    public void onResume() {
        super.onResume();
        // Get a writable database
        UserDatabase.getInstance(getActivity().getApplicationContext()).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });
    }

    /** When Activity is paused **/
    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }
}
