package com.example.gotit.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.Adapters.CartProductsAdapter;
import com.example.gotit.Activities.CheckoutActivity;
import com.example.gotit.ParseClasses.CreditCard;
import com.example.gotit.ParseClasses.Customer;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.ParseClasses.Store;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartFragment extends Fragment {

    private RecyclerView rviewPosts;
    protected final String FEED = "FEED";
    protected final String ERROR = "ERROR";
    protected CartProductsAdapter adapter;
    protected List<Product> mProductPosts;
    private String storeID;
    private TextView title;
    private TextView totalTv;
    private Button checkout;
    private Cart customerCart;
    private Double subtotal=0.0;
    private DecimalFormat df2 = new DecimalFormat("#.##");

    public CartFragment(Cart cart){
        customerCart = cart;
    }

    public void setStoreId(String id){
        storeID = id;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        rviewPosts = view.findViewById(R.id.rviewPosts);
        totalTv = view.findViewById(R.id.tvCaption);
        checkout = view.findViewById(R.id.checkout_btn);

        //create data source
        mProductPosts = new ArrayList<>();
        //create adapter
        adapter = new CartProductsAdapter(getContext(), mProductPosts, customerCart);
        //set adapter on the recycler view
        rviewPosts.setAdapter(adapter);
        //set the layout manger on the recycler view
        rviewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts(customerCart);

        //---------------------------------------------------
        // Input orders
        // Input ordered items
        // Change Item quantity
        //---------------------------------------------------
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-------------------------------------------
                // Create an order
                //-------------------------------------------
                ParseObject customerObj = ParseObject.createWithoutData("Customer", customerCart.getCustomerId());
                ParseObject order = ParseObject.create("Order");
                Random rand = new Random();
                // Generate random integers in range 0 to 999
                int rand_int = 1 + rand.nextInt(3);
                order.put("ord_cas_id", rand_int);
                order.put("ord_total", String.valueOf(df2.format(subtotal)));
                order.put("ord_status", "Pending");

                customerCart.getListProducts().get(0).getParseObject("sto_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        ParseObject store = object;

                        ParseObject storeObj = ParseObject.createWithoutData("Store", store.getObjectId());
                        order.put("sto_id",storeObj );

                    }
                });

                ParseObject addObj = ParseObject.createWithoutData("CustomerAddress", "2oy82BrBUD");
                order.put("add_id",addObj );
                order.put("cus_id", customerObj);
                order.saveInBackground(new SaveCallback() {                     // Save new Order
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.e(ERROR, "Error putting in order");
                            e.printStackTrace();
                            return;
                        }
                    }
                });

                //-------------------------------------------
                // Create an ordered item
                //-------------------------------------------
                for(Product product : mProductPosts){
                    ParseObject orderedItem = ParseObject.create("OrderedItem");
                    orderedItem.put("oid_quantity", product.getcartQuantity());
                    orderedItem.put("oid_price", product.getProductPrice());
                    orderedItem.put("ord_id", order );
                    orderedItem.put("pro_id", product);
                    orderedItem.saveInBackground(new SaveCallback() {           // Save new OrderedItems
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Log.e(ERROR, "Error putting in item");
                                e.printStackTrace();
                                return;
                            }
                        }
                    });

                    //-------------------------------------------
                    // Update product quantity
                    //-------------------------------------------
                    ParseQuery<Product> pQuery = new ParseQuery<Product>(Product.class);
                    pQuery.whereEqualTo("objectId", product.getObjectId());
                    pQuery.findInBackground(new FindCallback<Product>() {
                        @Override
                        public void done(List<Product> objects, ParseException e) {
                            if (e != null) {
                                Log.e(ERROR, "Error putting in item quantity");
                                e.printStackTrace();
                                return;
                            }

                            Number currQuantity = objects.get(0).getNumber("pro_quantity");
                            Number newQuanitity = (Integer)currQuantity - (Integer)product.getcartQuantity();
                            objects.get(0).put("pro_quantity", newQuanitity);
                            objects.get(0).saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e!=null)
                                        e.printStackTrace();
                                }
                            });
                            // Update Product Quantity
                            // Remove everything from Cart and go back to Home Page
                            customerCart.deleteAllProducts();
                        }
                    });
                }

                //-------------------------------------------
                // Create a Transaction
                //-------------------------------------------
                ParseObject transaction = ParseObject.create("Transaction");
                transaction.put("tra_type", "VENDOR");
                Number total = subtotal + (subtotal * 0.07);
                transaction.put("tra_amount", total);
                transaction.put("tra_description", "paid vendor");
                transaction.put("ord_id", order);
                ParseQuery<Store> pQuery = new ParseQuery<Store>(Store.class);
                pQuery.whereEqualTo("objectId", customerCart.getStoreId());
                pQuery.findInBackground(new FindCallback<Store>() {
                    @Override
                    public void done(List<Store> objects, ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        ParseObject store = objects.get(0);
                        transaction.put("ven_id", store.getParseObject("ven_id"));
                        transaction.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, new CategoryFragment(customerCart));
                fragmentTransaction.commit();
            }
        });
    }

    protected void queryPosts(Cart cart) {

        mProductPosts.addAll(cart.getListProducts());
        for(Product product : mProductPosts){
            subtotal += product.getProductPrice().doubleValue() * product.getcartQuantity().doubleValue();
        }

        totalTv.setText("$"+String.valueOf(df2.format(subtotal)));
        adapter.notifyDataSetChanged();

    }

    public void putCCid(String Id){
    }

}

