package com.example.gotit;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Cart")
public class Cart extends ParseObject {

    public static final String KEY_NAME = "name";

    public Cart(){super();}

}
