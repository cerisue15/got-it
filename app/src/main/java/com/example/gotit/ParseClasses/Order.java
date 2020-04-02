package com.example.gotit.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Order")
public class Order extends ParseObject {

    public static final String KEY_TOTAL = "ord_total";
    public static final String KEY_STORE = "sto_id";
    public static final String KEY_CREATED = "createdAt";

    public Order() {super();}

    public String getOrderTotal(){ return getString(KEY_TOTAL); }
    public void setOrderTotal(String total){ put(KEY_TOTAL, total); }

    public ParseObject getStore(){ return getParseObject(KEY_TOTAL); }
    //public void setStore(getParseUser total){ put(KEY_TOTAL, total); }


}
