package com.example.gotit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private final String LOGGED = "LOGGED";
    private EditText etUsername;
    private EditText etPassword;
    private TextView btnSignUp;
    private Button btnSignIn;
    private ImageView imvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);

        //added

        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
        imvIcon = findViewById(R.id.login_icon);
        btnSignIn = findViewById(R.id.signin_btn);
        btnSignUp = findViewById(R.id.btn_SignUp);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Log.d("LOGIN", "Info: " + username + " " + password);

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    login(username, password);
                }
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserRegistration();
            }
        });

    }

    private void goUserRegistration() {
        Intent i = new Intent(this, UserRegistrationActivity.class);
        startActivity(i);
        Log.d("WORKING", "It's working");
        finish();
    }

    //Function to validate user
    private void login(String user, String password) {

        //Query to check user input against object (username)
        ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("Customer");
        userQuery.whereEqualTo("cus_username", user);
        userQuery.whereEqualTo("cus_password", password);
        userQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() != 0) {
                    Log.d("users", "Retrieved " + objects.get(0).get("cus_username"));

                    Log.d("users", "Retrieved " + objects.size() + " users");
                    goMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainActivity() {
        Log.d(LOGGED, "logging in");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Log.d("WORKING", "It's working");
        finish();
    }
}
