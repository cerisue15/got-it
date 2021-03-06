package com.example.gotit;

import android.app.Application;

import com.example.gotit.ParseClasses.CreditCard;
import com.example.gotit.ParseClasses.Customer;
import com.example.gotit.ParseClasses.CustomerAddress;
import com.example.gotit.ParseClasses.Order;
import com.example.gotit.ParseClasses.OrderedItem;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.ParseClasses.Store;
import com.example.gotit.ParseClasses.Transaction;
import com.parse.Parse;
import com.parse.ParseObject;

//----------------------------------------------------------------------------------
// Initialize Parse
//----------------------------------------------------------------------------------
public class ParseApplication extends Application { ;

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Store.class);
        ParseObject.registerSubclass(Customer.class);
        ParseObject.registerSubclass(CustomerAddress.class);
        ParseObject.registerSubclass(Product.class);
        ParseObject.registerSubclass(Order.class);
        ParseObject.registerSubclass(OrderedItem.class);
        ParseObject.registerSubclass(CreditCard.class);
        ParseObject.registerSubclass(Transaction.class);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        /*Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("got-it-famu") // should correspond to APP_ID env variable
                .clientKey("CeGreCzUVL")  // set explicitly unless clientKey is explicitly configured on Parse server
                //.clientBuilder(builder)
                .server("http://got-it-famu.herokuapp.com/parse").build());
        */


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("cjones-parse-server") // should correspond to APP_ID env variable
                .clientKey("Grayson2015")  // set explicitly unless clientKey is explicitly configured on Parse server
                //.clientBuilder(builder)
                .server("http://parse-server-example-cjones.herokuapp.com/parse").build());


    }

}
//----------------------------------------------------------------------------------
//  (c) 2020, cerigoff Ceri Goff
//----------------------------------------------------------------------------------

