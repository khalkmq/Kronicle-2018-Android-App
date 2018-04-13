package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    // Variables for input
    EditText usernameText;
    EditText emailText;
    EditText birthDateText;
    EditText passwordText;
    EditText passwordConfirmText;

    // Database files
    SQLiteDatabase theDB;

    /** On Create **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    /** On Resume **/
    @Override
    protected void onResume() {
        super.onResume();
        // Get a writable database
        UserDatabase.getInstance(this).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });
    }

    /** When the sign up button is clicked **/
    public void btnSignUpClick(View view) {

        // Reference the objects on the sign up page
        usernameText = findViewById (R.id.name_text_su);
        birthDateText = findViewById (R.id.birth_date_su);
        emailText = findViewById (R.id.email_text_su);
        passwordText = findViewById(R.id.password_text_su);
        passwordConfirmText = findViewById(R.id.password_comfirm_text_su);

        // Get the user's input and save it on strings
        String username = usernameText.getText().toString();
        String birthdate = birthDateText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordConfirm = passwordConfirmText.getText().toString();

        // Check if any EditText boxes is empty
        if(username.equals("")){Toast.makeText(getApplicationContext(), "Username can not be empty", Toast.LENGTH_LONG).show();return;}
        else if(birthdate.equals("")){Toast.makeText(getApplicationContext(), "Birth Date can not be empty", Toast.LENGTH_LONG).show();return;}
        else if(email.equals("")){Toast.makeText(getApplicationContext(), "Email can not be empty", Toast.LENGTH_LONG).show();return;}
        else if(password.equals("")){Toast.makeText(getApplicationContext(), "Password field can not be empty", Toast.LENGTH_LONG).show();return;}
        else if(passwordConfirm.equals("")){Toast.makeText(getApplicationContext(), "Please confirm your password", Toast.LENGTH_LONG).show();return;}

        // Check if the password and password confirmation match
        else if(!password.equals(passwordConfirm)) {Toast.makeText(getApplicationContext(), "The passwords do not match", Toast.LENGTH_LONG).show();return;}

        /**    ----- INSERT CODE TO KEEP ACCOUNTS UNIQUE HERE -----    **/

        // If the requirements are met the account will be created, and page with redirect to main page.
        else
        {
            // The user's information will be saved in the Database
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            values.put("email", email);
            values.put("birthdate", birthdate);
            long newRowId = theDB.insert("offlineUsers", null, values);

            // Welcome the user!
            Toast.makeText(getApplicationContext(), "Account Created, Welcome " + username +"!", Toast.LENGTH_LONG).show();

            // Go to the Main Page
            startActivity(new Intent(SignUp.this, MainActivity.class));
        }
    }

    /** When activity is paused **/
    @Override
    protected void onPause() {
        super.onPause();
        theDB.close();
    }
}
