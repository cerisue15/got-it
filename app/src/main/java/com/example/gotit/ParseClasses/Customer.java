package com.example.gotit.ParseClasses;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import static java.util.jar.Pack200.Packer.ERROR;

//----------------------------------------------------------------------------------
// Creating an object for each customer
//----------------------------------------------------------------------------------
@ParseClassName("Customer")
public class Customer extends ParseObject {

        public static final String KEY_CUSTOMER = "objectId";
        public static final String KEY_STATUS = "cus_status";
        public static final String KEY_FIRSTNAME = "cus_first_name";
        public static final String KEY_LASTNAME = "cus_last_name";
        public static final String KEY_EMAIL = "cus_email";
        public static final String KEY_PASSWORD = "cus_password";
        public static final String KEY_USERNAME = "cus_username";
        //public static final String KEY_LOCATION = "location";
        public static final String KEY_IMAGE ="image";

        public Customer() { super(); }

        public String getCustomerId() {
                return getObjectId();
        }

        public String getFirstName(){ return getString(KEY_FIRSTNAME); }
        public void setFirstName(String firstName){ put(KEY_FIRSTNAME, firstName); }

        public String getLastName(){ return getString(KEY_LASTNAME); }
        public void setLastName(String lastName){ put(KEY_LASTNAME, lastName); }

        public String getEmail(){ return getString(KEY_EMAIL); }
        public void setEmail(String email){ put(KEY_EMAIL, email); }

        public String getUsername(){ return getString(KEY_USERNAME); }
        public void setUsername(String user){ put(KEY_USERNAME, user); }

        public String getPassword(){ return getString(KEY_PASSWORD); }
        public void setPassword(String password){ put(KEY_PASSWORD, password); }

}
