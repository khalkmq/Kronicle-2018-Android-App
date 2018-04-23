package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Fragment {

    // View
    View view;
    // Database files
    SQLiteDatabase theDB;

    // Constructor
    public SignUp() {}

    /** onCreateView **/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate View
        view = inflater.inflate(R.layout.sign_up, container, false);

        // Assign button to view and onClickListener
        Button signUpBtn = view.findViewById(R.id.sign_up_button_su);
        signUpBtn.setOnClickListener(new View.OnClickListener() {

            /** THIS CONTROLS WHAT HAPPENS WHEN SIGN-UP IS CLICKED **/
            @Override
            public void onClick(View v) {

                // Reference the objects on the sign up page
                EditText emailText = view.findViewById(R.id.email_su);
                EditText firstNameText = view.findViewById(R.id.first_name_su);
                EditText usernameText = view.findViewById(R.id.username_su);
                EditText passwordText = view.findViewById(R.id.password_su);
                EditText passwordConfirmText = view.findViewById(R.id.confirm_password_su);

                // Get the user's input and save it on strings
                String email = emailText.getText().toString();
                String firstName = firstNameText.getText().toString();
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String passwordConfirm = passwordConfirmText.getText().toString();

                // Check if any EditText boxes is empty
                if (firstName.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Name can not be empty", Toast.LENGTH_LONG).show(); return;
                } else if (email.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Email can not be empty", Toast.LENGTH_LONG).show(); return;
                } else if (username.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Username can not be empty", Toast.LENGTH_LONG).show(); return;
                } else if (password.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Password field can not be empty", Toast.LENGTH_LONG).show(); return;
                } else if (passwordConfirm.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Please confirm your password", Toast.LENGTH_LONG).show(); return;
                }

                // Check if the password and password confirmation match
                else if (!password.equals(passwordConfirm)) {
                    Toast.makeText(getContext().getApplicationContext(), "The passwords do not match", Toast.LENGTH_LONG).show(); return;
                }

                // Check if an account already exists
                String[] projection = {"username"};
                Cursor cursor = theDB.query("offlineUsers", projection, null, null, null, null, null);

                //Check for any similar usernames
                boolean uniqueState = true;
                while (cursor.moveToNext()) {
                    // An account with similar username exists!
                    if (cursor.getString(cursor.getColumnIndexOrThrow("username")).equals(username))
                        uniqueState = false;
                }
                cursor.close();

                // If user is not unique, prompt a change in name
                if (uniqueState != true) {
                    Toast.makeText(getContext().getApplicationContext(), "This username is already in use", Toast.LENGTH_LONG).show(); return;
                }
                // Otherwise, register the account in the database
                else {
                    // The user's information will be saved in the Database
                    ContentValues values = new ContentValues();
                    values.put("loggedIn", true);
                    values.put("firstName", firstName);
                    values.put("username", username);
                    values.put("password", password);
                    values.put("email", email);
                    long newRowId = theDB.insert("offlineUsers", null, values);

                    // Welcome the user!
                    Toast.makeText(getContext().getApplicationContext(), "Account Created, Welcome " + username + "!", Toast.LENGTH_LONG).show();

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

    /** When activity is paused **/
    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }

}
