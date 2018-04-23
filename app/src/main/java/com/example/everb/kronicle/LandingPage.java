package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class LandingPage extends AppCompatActivity {

    // Database variables
    SQLiteDatabase theDB;
    private long rowid;

    // Design Stuff
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    /** On Create **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a ASyncWritable database
       UserDatabase.getInstance(this).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });

       // Create Design Features
        setContentView(R.layout.landing_page);

        tabLayout = findViewById(R.id.tab_layout_lp);
        appBarLayout = findViewById(R.id.appbar_lp);
        viewPager = findViewById(R.id.view_pager_lp);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new SignIn(), getString(R.string.sign_in));
        adapter.AddFragment(new SignUp(), getString(R.string.sign_up));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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

    /** Sign In with Google Click **/
    public void btnSignInGoogle(View view) {
        //Nothing happens so far.
        Toast.makeText(getApplicationContext(), "Hello Google!", Toast.LENGTH_LONG).show();
    }

    /** Sign In with Facebook Click **/
    public void btnSignInFacebook(View view) {
        //Nothing happens so far.
        Toast.makeText(getApplicationContext(), "Hello Facebook!", Toast.LENGTH_LONG).show();
    }

    /** Forgot Password Click **/
    public void btnForgotPass(View view) {
        //Nothing happens so far.
        Toast.makeText(getApplicationContext(), "OH NO! Forgot it :(", Toast.LENGTH_LONG).show();
    }

    /** Join as a Guest Button Click **/
    public void btnGuestClick(View view) {

        // Check if a guest account already exists
        String[] projection = {"username"};
        Cursor cursor = theDB.query("offlineUsers", projection, null, null, null, null, null);

        //Check for a guest account
        boolean uniqueState = true;
        while (cursor.moveToNext()) {
            // A guest account exists
            if (cursor.getString(cursor.getColumnIndexOrThrow("username")).equals("guest"))
                uniqueState = false;
        }
        cursor.close();

        // If user exists, log them in
        if (uniqueState != true) {
            // Set Content
            ContentValues values = new ContentValues();
            values.put("loggedIn", true);
            String[] whereArgs ={"guest"};
            theDB.update("offlineUsers", values,"username = ?", whereArgs);

            // Welcome the user!
            Toast.makeText(getApplicationContext(), "Welcome back, Guest!", Toast.LENGTH_LONG).show();
        }

        // Otherwise, register the account in the database
        else {
            // Creates Guest profile automatically
            ContentValues values = new ContentValues();
            values.put("loggedIn", true);
            values.put("firstName", "guest");
            values.put("username", "guest");
            values.put("password", "guest");
            values.put("email", "guest");
            long newRowId = theDB.insert("offlineUsers", null, values);

            // Welcome the user!
            Toast.makeText(getApplicationContext(), "Account Created, Welcome Guest!", Toast.LENGTH_LONG).show();
        }

        // Go to the Main Page
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

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