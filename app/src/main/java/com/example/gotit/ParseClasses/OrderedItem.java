package com.example.gotit.ParseClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("OrderedItem")
public class OrderedItem extends ParseObject {

    public static final String KEY_QUANTITY = "oid_quanity";
    public static final String KEY_PRICE = "oid_price";
    public static final String KEY_CREATED = "createdAt";

    public OrderedItem() {super();}

    public Number getProductQuantity(){ return getNumber(KEY_QUANTITY); }
    public void setProductQuantity(Number total){ put(KEY_QUANTITY, total); }

    public Number getProductPrice(){ return getNumber(KEY_PRICE); }
    public void setPrice(Number price){ put(KEY_PRICE, price); }

}
