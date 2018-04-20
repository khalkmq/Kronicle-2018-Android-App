package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
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

    // Variables for input
    EditText emailText;
    EditText fullNameText;
    EditText usernameText;
    EditText birthDateText;
    EditText passwordText;
    EditText passwordConfirmText;

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


    /** Sign Up Button Click**/
    public void btnSignUpClick(View view) {

        // Reference the objects on the sign up page
        emailText = view.findViewById (R.id.email_su);
        fullNameText = view.findViewById (R.id.full_name_su);
        usernameText = view.findViewById (R.id.username_su);
        //birthDateText = view.findViewById (R.id.birth_date_su);
        passwordText = view.findViewById(R.id.password_su);
        passwordConfirmText = view.findViewById(R.id.confirm_password_su);

        // Get the user's input and save it on strings
        String email = emailText.getText().toString();
        /**NOT USED YET**/ // String fullName = fullNameText.getText().toString();
        String username = usernameText.getText().toString();
        //String birthdate = birthDateText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordConfirm = passwordConfirmText.getText().toString();

        // Check if any EditText boxes is empty
        if(username.equals("")) {
            Toast.makeText(this.getApplicationContext(), "Username can not be empty", Toast.LENGTH_LONG).show();
        }

//        else if(birthdate.equals("")) {
//            Toast.makeText(this.getApplicationContext(), "Birth Date can not be empty", Toast.LENGTH_LONG).show();
//        }

        else if(email.equals("")) {
            Toast.makeText(this.getApplicationContext(), "Email can not be empty", Toast.LENGTH_LONG).show();
        }

        else if(password.equals("")) {
            Toast.makeText(this.getApplicationContext(), "Password field can not be empty", Toast.LENGTH_LONG).show();
        }

        else if(passwordConfirm.equals("")) {
            Toast.makeText(this.getApplicationContext(), "Please confirm your password", Toast.LENGTH_LONG).show();
        }

        // Check if the password and password confirmation match
        else if(!password.equals(passwordConfirm)) {Toast.makeText(this.getApplicationContext(), "The passwords do not match", Toast.LENGTH_LONG).show();
        }

        /**    ----- INSERT CODE TO KEEP ACCOUNTS UNIQUE HERE -----    **/

        // If the requirements are met the account will be created, and page with redirect to main page.

        else
        {
            // The user's information will be saved in the Database
            ContentValues values = new ContentValues();
            values.put("loggedIn", true);
            /** ADD ONE FOR NAMES TOO **/
            values.put("username", username);
            values.put("password", password);
            values.put("email", email);
            values.put("birthdate", "03/21/1995");
            long newRowId = theDB.insert("offlineUsers", null, values);

            // Welcome the user!
            Toast.makeText(this.getApplicationContext(), "Account Created, Welcome " + username +"!", Toast.LENGTH_LONG).show();

            // Go to the Main Page
            startActivity(new Intent(LandingPage.this, MainActivity.class));
        }
    }

    /** Sign In Button Click**/
    public void btnSignInClick(View view) {
        //Nothing happens so far.
        Toast.makeText(getApplicationContext(), "Hello Sign In!", Toast.LENGTH_LONG).show();
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
        // Creates Guest profile automatically, and redirects to MainActivity
        ContentValues values = new ContentValues();
        values.put("loggedIn", true);
        values.put("username", "guest");
        values.put("password", "guest");
        values.put("email", "guest");
        values.put("birthdate", "guest");
        long newRowId =  theDB.insert("offlineUsers", null, values);

        // Welcome the user!
        Toast.makeText(getApplicationContext(), "Account Created, Welcome Guest!", Toast.LENGTH_LONG).show();

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