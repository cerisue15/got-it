package com.example.gotit;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseClassName;

//----------------------------------------------------------------------------------
// Creating an object for Store
//----------------------------------------------------------------------------------
@ParseClassName("Store")
public class Store extends ParseObject {
    public static final String KEY_STORE = "objectId";
    public static final String KEY_STATE = "sto_state";
    public static final String KEY_ADDRESS = "sto_street_address";
    public static final String KEY_EMAIL = "sto_email";
    public static final String KEY_VENDOR = "ven_id";
    public static final String KEY_CITY = "sto_city";
    public static final String KEY_NAME = "sto_name";
    public static final String KEY_PHONE = "sto_phone";
    public static final String KEY_ZIPCODE = "sto_zipcode";
    public static final String KEY_VEN_ID ="ven_id";

    public Store() {super();}

    public String getStoreId() {
        return getObjectId();
    }
    //public void setStoreId(String storeId){ put(KEY_STORE, storeId); }

    public String getStoreState() {
        return getString(KEY_STATE);
    }
    public void setStoreState(String storeState){
        put(KEY_STATE, storeState);
    }

    public String getStoreAddress() {
        return getString(KEY_ADDRESS);
    }
    public void setStoreAddress(String storeAddress){
        put(KEY_ADDRESS, storeAddress);
    }

    public String getStoreEmail() {
        return getString(KEY_EMAIL);
    }
    public void setStoreEmail(String storeEmail){
        put(KEY_EMAIL, storeEmail);
    }

    public String getStoreCity() {
        return getString(KEY_CITY);
    }
    public void setStoreCity(String storeCity){
        put(KEY_CITY, storeCity);
    }

    public String getStoreName() {
        return getString(KEY_NAME);
    }
    public void setStoreName(String storeName){
        put(KEY_NAME, storeName);
    }

    public Number getStorePhone() {
        return getNumber(KEY_PHONE);
    }
    public void setStorePhone(Number storePhone){ put(KEY_PHONE, storePhone); }

    public Number getStoreZipcode() {
        return getNumber(KEY_ZIPCODE);
    }
    public void setStoreZipcode(Number storeZipcode){
        put(KEY_ZIPCODE, storeZipcode);
    }

    public String getVendorId() {
        return getString(KEY_VEN_ID);
    }

    //public Vendor getStoreVendor() {return getString(KEY_VEN_ID);}
}

