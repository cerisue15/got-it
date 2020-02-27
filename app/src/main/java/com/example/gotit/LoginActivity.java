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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
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
                //TODO: Replace with Login func
                login(username, password);
                goMainActivity();
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
            userQuery.whereEqualTo("cus_username", password);
            //Query to check user input against object (password)
            ParseQuery<ParseObject> pwordQuery = ParseQuery.getQuery("Customer");
            pwordQuery.whereEqualTo("cus_password", password);

            //Do compound query to validate both username AND password for a specific object
            List<ParseQuery<ParseObject>> queries = new ArrayList<>();
            queries.add(userQuery);
            queries.add(pwordQuery);
            ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> results, ParseException e) {
                    if (e == null) {
                        Log.d("users", "Retrieved " + results.size() + " users");
                        goMainActivity();

                    } else {
                        Log.d("users", "Error: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    /*private void login( String user,final String password) {

        ParseUser.logInInBackground(user, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e("ERROR", "Issue w Login");
                    e.printStackTrace();
                    return;
                }
                //TODO:navigate to new activity if the sign in succeeds
                Log.d("WORKING", "It's working");
                goMainActivity();
            }
        });
    }*/

    private void  goMainActivity(){
        Log.d(LOGGED, "logging in");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Log.d("WORKING", "It's working");
        finish();
    }
}
