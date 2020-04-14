package com.example.gotit;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

//----------------------------------------------------------------------------------
// Initialize Parse
//----------------------------------------------------------------------------------
public class ParseApplication extends Application{

    private ParseObject cart;

    @Override
    public void onCreate() {
        super.onCreate();



        ParseObject.registerSubclass(Store.class);
        ParseObject.registerSubclass(Customer.class);
        ParseObject.registerSubclass(Product.class);
        ParseObject.registerSubclass(Vendor.class);
        ParseObject.registerSubclass(Cart.class);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("got-it-famu") // should correspond to APP_ID env variable
                .clientKey("CeGreCzUVL")  // set explicitly unless clientKey is explicitly configured on Parse server
                //.clientBuilder(builder)
                .server("http://got-it-famu.herokuapp.com/parse").build());

        cart = ParseObject.create("Cart");
        cart.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e("ERROR", "Error in signing up user");
                    e.printStackTrace();
                    return;
                }
            }
        });
        Log.d("cart", "--> "+ cart.getObjectId());
    }

    public ParseObject getCart() {

        return cart;
    }

}
