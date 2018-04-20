package com.example.everb.kronicle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.Console;

public class SplashScreen extends AppCompatActivity {

    // Database stuff
    private SQLiteDatabase theDB;
    private boolean loginState = false;


    /** OnCreate **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a ASyncWritable database
        UserDatabase.getInstance(this).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;

                // Check if an account is logged in
                String[] projection = {"loggedIn"};
                Cursor cursor = theDB.query("offlineUsers", projection, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    // An account is logged in!
                    if(cursor.getInt(cursor.getColumnIndexOrThrow("loggedIn")) == 1)
                        loginState = true;
                }
                cursor.close();

                // Route to Main Activity if logged
                if(loginState == true){
                    Toast.makeText(getApplicationContext(), "Welcome back!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
                // Route to LandingPage if not logged
                else {
                    startActivity(new Intent(SplashScreen.this, LandingPage.class));
                    finish();
                }
            }
        });
    }


}