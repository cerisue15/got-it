package com.example.gotit.Activities;

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

import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

//----------------------------------------------------------------------------------
// The activity that allows a user to Login
// Also a feature to take a user to a registration activity
//----------------------------------------------------------------------------------
public class LoginActivity extends AppCompatActivity {

    private final String LOGGED = "LOGGED";
    private EditText etUsername;
    private EditText etPassword;
    private TextView btnSignUp;
    private Button btnSignIn;
    private ImageView imvIcon;
    //----------------------------------------------------------------------------------
    // Set the view
    //----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        ParseUser.getCurrentUser().logOut();

        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
        imvIcon = findViewById(R.id.login_icon);
        btnSignIn = findViewById(R.id.signin_btn);
        btnSignUp = findViewById(R.id.btn_SignUp);

        //----------------------------------------------------------------------------------
        // Listen for a click on the Sign In Button
        //----------------------------------------------------------------------------------
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

        //----------------------------------------------------------------------------------
        // Listen for a click on the Sign Up Button
        //----------------------------------------------------------------------------------
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserRegistration();
            }
        });

    }

    //----------------------------------------------------------------------------------
    // Functionality for the Sign Up Button
    // Takes user to the User Registration Activity to register
    //----------------------------------------------------------------------------------
    private void goUserRegistration() {
        Intent i = new Intent(this, UserRegistrationActivity.class);
        startActivity(i);
        Log.d("WORKING", "It's working");
        finish();
    }

    //----------------------------------------------------------------------------------
    // Functionality for the Sign In Button
    // This function logs in the the user
    // This function validates the user credentials
    //----------------------------------------------------------------------------------
    private void login(String user, String password) {

        //Query to check user input against object (username)
        ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("Customer");
        userQuery.whereEqualTo("cus_username", user);
        userQuery.whereEqualTo("cus_password", password);
        //userQuery.whereEqualTo("cus_status", true);
        userQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                Log.d("TAG", "Amount:" + objects.size());
                if (objects.size() != 0) {
                    Log.d("users", "Retrieved " + objects.get(0).get("cus_username"));

                    Log.d("users", "Retrieved " + objects.size() + " users");

                    String Id = objects.get(0).getObjectId();

                    goMainActivity(Id);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //----------------------------------------------------------------------------------
    // This function takes the user to the Main Activity after they were
    // successfully logged in
    //----------------------------------------------------------------------------------
    private void goMainActivity(String id) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("customer_ID", id);
        startActivity(intent);
        finish();
    }
}
