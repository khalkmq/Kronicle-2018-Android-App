package com.example.everb.kronicle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText nameText;
    EditText lastNameText;
    EditText emailText;
    EditText passwordText;
    EditText passwordConfirmText;
    Button signUpButton;

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Reference the objects on the sign up page
        nameText = findViewById (R.id.name_text_su);
        lastNameText = findViewById (R.id.last_name_text_su);
        emailText = findViewById (R.id.email_text_su);
        passwordText = findViewById(R.id.password_text_su);
        passwordConfirmText = findViewById(R.id.password_comfirm_text_su);
        signUpButton = findViewById(R.id.sign_up_button_su);

        // OnClick Listener for sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Get the user's input and save it on strings
                String name = nameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String passwordConfirm = passwordConfirmText.getText().toString();

                // Check if any EditText boxes is empty
                if(name.equals("") || lastName.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field left empty", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if the password and password confirmation match
                if(!password.equals(passwordConfirm))
                {
                    Toast.makeText(getApplicationContext(), "The passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }

                // If the requirements are met the account will be created
                else
                {
                    // The user's information will be saved in the Database
                    loginDataBaseAdapter.insertEntry(name, lastName, email, password);
                    Toast.makeText(getApplicationContext(), "Account Created, Welcome to Kronicle!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}
