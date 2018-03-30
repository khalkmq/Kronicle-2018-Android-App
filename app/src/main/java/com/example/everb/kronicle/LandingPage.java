package com.example.everb.kronicle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/* This page is responsible for Singup/signin activity for the app */
public class LandingPage extends AppCompatActivity {

    Button signInButton;
    Button signUpButton;
    Button guestSignInButton;

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // Instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Reference the buttons on the intro page
        signInButton = findViewById(R.id.sign_in_button_ip);
        signUpButton = findViewById(R.id.sign_up_button_ip);
        guestSignInButton = findViewById(R.id.guest_sign_in_button_ip);

        /* SIGN UP BUTTON CODE */
        // OnClick Listener for sign up button
        boolean signUpClicked = false;
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // The Intent will take us to the sign up page
                Intent signUpIntent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(signUpIntent);
            }
        });

        /* SIGN IN BUTTON CODE */
        // Add the code below here once its working

        /* GUEST BUTTON CODE */
        guestSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Creates Guest profile automatically, and redirects to MainActivity
                loginDataBaseAdapter.insertEntry("Guest", "Guest", "Guest", "Guest");
                Toast.makeText(getApplicationContext(), "Guest account created, Welcome to Kronicle!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LandingPage.this, MainActivity.class));
            }
        });
    }

    // NOT USED BUT WILL BE USED FOR SIGN IN PAGE
//    // Methos to handleClick Event of Sign In Button
//    public void signIn(View V)
//    {
//        final Dialog dialog = new Dialog(HomeActivity.this);
//        dialog.setContentView(R.layout.login);
//        dialog.setTitle(“Login”);
//
//// get the Refferences of views
//        final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
//        final EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
//
//        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
//
//// Set On ClickListener
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//// get The User name and Password
//                String userName=editTextUserName.getText().toString();
//                String password=editTextPassword.getText().toString();
//
//// fetch the Password form database for respective user name
//                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
//
//// check if the Stored password matches with Password entered by user
//                if(password.equals(storedPassword))
//                {
//                    Toast.makeText(HomeActivity.this, “Congrats: Login Successfull”, Toast.LENGTH_LONG).show();
//                    dialog.dismiss();
//                }
//                else
//                {
//                    Toast.makeText(HomeActivity.this, “User Name or Password does not match”, Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        dialog.show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}