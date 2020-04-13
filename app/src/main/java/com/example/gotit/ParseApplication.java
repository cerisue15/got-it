package com.example.gotit;

import android.app.Application;

import com.example.gotit.ParseClasses.CreditCard;
import com.example.gotit.ParseClasses.Customer;
import com.example.gotit.ParseClasses.CustomerAddress;
import com.example.gotit.ParseClasses.Order;
import com.example.gotit.ParseClasses.OrderedItem;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.ParseClasses.Store;
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

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("mySeniorProj") // should correspond to APP_ID env variable
                .clientKey("myKey2298")  // set explicitly unless clientKey is explicitly configured on Parse server
                //.clientBuilder(builder)
                .server("http://senior-proj-ex.herokuapp.com/parse").build());
    }

}
