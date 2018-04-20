package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class SignUp extends Fragment {
    View view;

    public SignUp() {

    }

    // Variables for input
//    EditText emailText;
//    EditText fullNameText;
//    EditText usernameText;
//    EditText birthDateText;
//    EditText passwordText;
//    EditText passwordConfirmText;

    // Database files
    SQLiteDatabase theDB;

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_up, container, false);
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
//
//    /** When the sign up button is clicked **/
//    public void btnSignUpClick(View view) {
//
//        // Reference the objects on the sign up page
//        emailText = view.findViewById (R.id.email_su);
//        fullNameText = view.findViewById (R.id.full_name_su);
//        usernameText = view.findViewById (R.id.username_su);
////      birthDateText = view.findViewById (R.id.birth_date_su);
//        passwordText = view.findViewById(R.id.password_su);
//        passwordConfirmText = view.findViewById(R.id.confirm_password_su);
//
//        // Get the user's input and save it on strings
//        String email = emailText.getText().toString();
//        String fullName = fullNameText.getText().toString();
//        String username = usernameText.getText().toString();
////      String birthdate = birthDateText.getText().toString();
//        String password = passwordText.getText().toString();
//        String passwordConfirm = passwordConfirmText.getText().toString();
//
//        // Check if any EditText boxes is empty
//        if(username.equals("")) {
//            Toast.makeText(getActivity().getApplicationContext(), "Username can not be empty", Toast.LENGTH_LONG).show();
//        }
//
////        else if(birthdate.equals("")) {
////            Toast.makeText(getActivity().getApplicationContext(), "Birth Date can not be empty", Toast.LENGTH_LONG).show();
////        }
//
//        else if(email.equals("")) {
//            Toast.makeText(getActivity().getApplicationContext(), "Email can not be empty", Toast.LENGTH_LONG).show();
//        }
//
//        else if(password.equals("")) {
//            Toast.makeText(getActivity().getApplicationContext(), "Password field can not be empty", Toast.LENGTH_LONG).show();
//        }
//
//        else if(passwordConfirm.equals("")) {
//            Toast.makeText(getActivity().getApplicationContext(), "Please confirm your password", Toast.LENGTH_LONG).show();
//        }
//
//        // Check if the password and password confirmation match
//        else if(!password.equals(passwordConfirm)) {Toast.makeText(getActivity().getApplicationContext(), "The passwords do not match", Toast.LENGTH_LONG).show();
//        }
//
//        /**    ----- INSERT CODE TO KEEP ACCOUNTS UNIQUE HERE -----    **/
//
//        // If the requirements are met the account will be created, and page with redirect to main page.
//
//        else
//        {
//            // The user's information will be saved in the Database
//            ContentValues values = new ContentValues();
//            values.put("loggedIn", true);
//            values.put("username", username);
//            values.put("password", password);
//            values.put("email", email);
//
//            // EDIT
//            values.put("birthdate", "03/21/1995");
//            long newRowId = theDB.insert("offlineUsers", null, values);
//
//            // Welcome the user!
//            Toast.makeText(getActivity().getApplicationContext(), "Account Created, Welcome " + username +"!", Toast.LENGTH_LONG).show();
//
//            // Go to the Main Page
//            startActivity(new Intent(getActivity(), MainActivity.class));
//        }
//    }

    /** When activity is paused **/
    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }
}
