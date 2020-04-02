package com.example.gotit.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Vendor")
public class Vendor extends ParseObject {

    public static final String KEY_VENDOR = "objectId";
    public static final String KEY_CITY = "ven_city";
    public static final String KEY_STATE = "ven_state";
    public static final String KEY_ROUT_NUM = "ven_routing_num";
    public static final String KEY_TAXID = "ven_tax_id";
    public static final String KEY_NAME = "ven_name";
    public static final String KEY_PHONENUM = "ven_phonenum";
    public static final String KEY_ZIPCODE = "ven_zipcode";
    public static final String KEY_ACCT_NUM = "ven_account_num";
    public static final String KEY_ADDRESS = "ven_street_address";
    public static final String KEY_IMAGE ="img";

    public Vendor() {super();}

    public String getProductId() {
        return getObjectId();
    }
    //public void setProductId(String storeId){ put(KEY_STORE, productId); }

    public String getCity() { return getString(KEY_CITY); }
    public void setCity(String city){ put(KEY_CITY, city); }

    public String getZipcode(){ return getString(KEY_ZIPCODE); }
    public void setZipcode(String zipcode){ put(KEY_ZIPCODE, zipcode); }

    public Number getTaxId(){ return getNumber(KEY_TAXID); }
    public void setTaxId(Number taxid){ put(KEY_TAXID, taxid); }

    public Number getAcctNum(){ return getNumber(KEY_ACCT_NUM); }
    public void setAcctNum(Number acct){ put(KEY_ACCT_NUM, acct); }

    public String getVendorName(){ return getString(KEY_NAME); }
    public void setVendorName(String name){ put(KEY_NAME, name); }

    public String getAddress(){ return getString(KEY_ADDRESS); }
    public void setAddress(String address){ put(KEY_ADDRESS, address); }

    public String getState(){ return getString(KEY_STATE); }
    public void setState(String state){ put(KEY_STATE, state); }

    public Number getRoutingNumber(){ return getNumber(KEY_ROUT_NUM); }
    public void setRoutingNumber(Number routingNumber){ put(KEY_ROUT_NUM, routingNumber); }

    public Number getPhonenum(){ return getNumber(KEY_PHONENUM); }
    public void setPhonenum(Number phonenum){ put(KEY_PHONENUM, phonenum); }

    public ParseFile getImage() { return getParseFile(KEY_IMAGE); }
    public void setImage(ParseFile image){ put(KEY_IMAGE, image); }

}
