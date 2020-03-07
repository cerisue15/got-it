package com.example.gotit;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


public class UserRegistrationActivity extends AppCompatActivity {

    private final String SIGNUP = "SIGNUP";
    private final String ERROR = "ERROR";
    private EditText etfname;
    private EditText etlname;
    private EditText etemail;
    private EditText etpassword;
    private EditText etusername;
    //private EditText etphonenum;
    private Button btn_cancel;
    private Button btn_SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        etfname = findViewById(R.id.userReg_fname);
        etlname = findViewById(R.id.userReg_lname);
        etemail = findViewById(R.id.userReg_email);
        etpassword = findViewById(R.id.userReg_password);
        etusername = findViewById(R.id.userReg_username);
        //etphonenum = findViewById(R.id.userReg_phone);
        btn_cancel = findViewById(R.id.btn_Cancel);
        btn_SignUp = findViewById(R.id.btn_Submit);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the text from form
                String fname = etfname.getText().toString();
                String lname = etlname.getText().toString();

                String name = fname + " " + lname;
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                String username = etusername.getText().toString();
                //String phonenum = PhoneNumberUtils.formatNumber(etphonenum.getText().toString());

                verify(fname, lname, email, username, password);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

    }

    private void verify(final String fname,final String lname,final String email,final String username,final String password) {
        if (username.length()==0 || password.length()==0 || fname.length()==0 || lname.length()==0 || email.length()==0){
            Toast.makeText(UserRegistrationActivity.this, "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
        }
        else {

            //Query to check user input against object (username)
            ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("Customer");
            userQuery.whereEqualTo("cus_username", username);
            userQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    Log.d("users", "Retrieved " + objects.size() + " users");

                    if (objects.size() != 0) {
                        Toast.makeText(UserRegistrationActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                        Log.d("users", "Retrieved " + objects.get(0).get("cus_username"));
                        Log.d("users", "Retrieved " + objects.size() + " users");
                    }
                    else{
                        register(fname, lname, username, email, password);
                    }
                }
            });
        }
    }


    private void register(String fname, String lname, String username, String email, String password) {

        ParseObject customer = ParseObject.create("Customer");
        customer.put("cus_first_name", fname );
        customer.put("cus_last_name", lname );
        customer.put("cus_email", email);
        customer.put("cus_username", username);
        customer.put("cus_password", password);
        //parseUser.put(user.KEY_PHONENUM, phonenum);


        customer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(ERROR, "Error in signing up user");
                    e.printStackTrace();
                    return;
                }
                Log.d(SIGNUP, "Sign Up Successful");
                etfname.setText("");
                etlname.setText("");
                etusername.setText("");
                etpassword.setText("");
                etemail.setText("");
                //etphonenum.setText("");

            }
        });

        ProgressDialog dialog = ProgressDialog.show(UserRegistrationActivity.this, "",
                "Loading. Please wait...", true);
        goLoginActivity();
    }


    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        Toast.makeText(UserRegistrationActivity.this, "Log into your new account", Toast.LENGTH_SHORT).show();
        Log.d("WORKING", "It's working");
        finish();
    }




}