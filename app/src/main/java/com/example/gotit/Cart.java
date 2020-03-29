package com.example.gotit;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParseClassName("Cart")
public class Cart extends ParseObject {

    public static final String KEY_NAME = "name";
    private Map<Integer, String> hm = new HashMap<>();
    List<Product> products = new ArrayList<>();
    private Integer integ=0;


    public Cart(){super();}

    public void addProductToCart(Product product){
        Log.d("HM", "--> " + hm.containsValue(product.getProductName()));
        if(!hm.containsValue(product.getProductName())){
            hm.put(integ, product.getProductName());
            products.add(product);
            integ++;
        }

    }

    public List<Product> getListProducts(){
        return products;
    }

    public void deleteProductFromCart(Product product){
        Log.d("HM", "--> " + hm.containsValue(product.getProductName()));
        hm.values().remove(product.getProductName());
        products.remove(product);
    }

}
