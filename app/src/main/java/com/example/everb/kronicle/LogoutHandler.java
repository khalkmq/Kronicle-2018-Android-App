package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LogoutHandler extends AppCompatActivity {

    //Database stuff
    SQLiteDatabase theDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a ASyncWritable database
        UserDatabase.getInstance(this).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;

                // Once retrieved, log the user out
                // Set Content to false(logged-out)
                ContentValues values = new ContentValues();
                values.put("loggedIn", false);
                theDB.update("offlineUsers", values,"loggedIn = 1", null);

                // Send a toast to the User
                Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();

                // Go to the Landing Page
                startActivity(new Intent(LogoutHandler.this, LandingPage.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
