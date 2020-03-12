package com.example.gotit;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

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

        public Customer() {super();}

        public ParseUser getCustomerId() {
                return getParseUser(KEY_CUSTOMER);
        }
        public void setCustomerId(ParseUser customerId){
                put(KEY_CUSTOMER, customerId);
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



    /*
    public String getPhonenum(){ return getString(KEY_PHONENUM); }
    public void setPhonenum(String phonenum){ put(KEY_PHONENUM, phonenum); }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }
    */
}
