package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* This page is responsible for SignUp/SignIn activity for the app */
public class LandingPage extends AppCompatActivity {

    // Database variables
    private SQLiteDatabase theDB;
    private long rowid;
    public static final String TAG = "LandingPage";


    /** On Create **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // Get a ASyncWritable database
        UserDatabase.getInstance(this).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });
    }

    /** On Resume **/
    @Override
    protected void onResume() {
        super.onResume();
    }

    /** Sign Up Button Click**/
    public void btnSignUpClick(View view) {
        Intent signUpIntent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(signUpIntent);
    }

    /** Sign In Button Click**/
    public void btnSignInClick(View view) {
        //Nothing happens so far.
    }

    /** Join as a Guest Button Click**/
    public void btnGuestClick(View view) {
        // Creates Guest profile automatically, and redirects to MainActivity
            ContentValues values = new ContentValues();
            values.put("username", "guest");
            values.put("password", "guest");
            values.put("email", "guest");
            values.put("birthdate", "guest");
            long newRowId =  theDB.insert("offlineUsers", null, values);

            // Welcome the user!
            Toast.makeText(getApplicationContext(), "Account Created, Welcome Guest!", Toast.LENGTH_LONG).show();

            // Go to the Main Page
            startActivity(new Intent(this, MainActivity.class));

            // Finish this activity
            finish();
    }

    /** When Activity is paused **/
    @Override
    protected void onPause() {
        super.onPause();
        theDB.close();
    }
}