package com.example.gotit.ParseClasses;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.jar.Pack200.Packer.ERROR;

public class Cart{

    public static final String KEY_NAME = "name";
    private Map<Integer, String> hm = new HashMap<>();
    List<Product> products = new ArrayList<>();
    private Integer integ=0;
    private String customerId;
    private String nameProd, nameP;
    private String storeName;

    public Cart(String c){
        super();
        this.customerId=c;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void addProductToCart(Product product){
        if(!hm.containsValue(product.getProductName())){
            hm.put(integ, product.getProductName());
            products.add(product);
            integ++;
        }

    }
    public void deleteAllProducts(){
        hm.clear();
        products.clear();
    }

    public List<Product> getListProducts(){
        return products;
    }

    public void deleteProductFromCart(Product product){
        hm.values().remove(product.getProductName());
        products.remove(product);
    }

    public Boolean isProductFromStore(String s){

        final Boolean[] istrue = {false};

        if (products.size() != 0){

            Log.d("names", "" + getStoreName() +" "+ s);

            if(getStoreName().equals(s)){
                return true;
            }
            else return false;


        }
        return true;
    }

}
