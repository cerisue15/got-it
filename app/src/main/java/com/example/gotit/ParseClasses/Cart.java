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


    public Cart(String c){
        super();
        this.customerId=c;
    }

    public String getCustomerId() {
        return customerId;
    }

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

    public Boolean isProductFromStore(Product product){
        final Boolean[] istrue = {false};
        if (products.size() != 0){
            for (Product p : products){

                p.getParseObject("sto_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        ParseObject store = object;
                        Log.d("names", "" + store.getObjectId());
                        nameP = store.getString("sto_name");

                        product.getParseObject("sto_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                ParseObject store = object;
                                Log.d("names", "" + store.getObjectId());
                                nameProd = store.getString("sto_name");

                                Log.d("names", "" + nameP +" "+ nameProd);

                                if (nameP.equals(nameProd)==true){
                                    istrue[0] = true;
                                }
                                else
                                    istrue[0] = false;

                            }
                        });
                    }
                });

                return istrue[0];
            }
        }
        return true;
    }

}
