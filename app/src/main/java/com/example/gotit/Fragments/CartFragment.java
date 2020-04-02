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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.Adapters.CartProductsAdapter;
import com.example.gotit.Activities.CheckoutActivity;
import com.example.gotit.ParseClasses.CreditCard;
import com.example.gotit.ParseClasses.Customer;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

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
    private  String ccObj;

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

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject customerObj = ParseObject.createWithoutData("Customer", customerCart.getCustomerId());

                ParseQuery<CreditCard> ccQuery = new ParseQuery<CreditCard>(CreditCard.class);
                ccQuery.whereEqualTo("cus_id", customerObj);
                ccQuery.findInBackground(new FindCallback<CreditCard>() {
                    @Override
                    public void done(List<CreditCard> objects, ParseException e) {
                            if (e != null){
                                Log.e(ERROR, "Error");
                                e.printStackTrace();
                                return;
                            }
                            Log.d("size",""+ objects.get(0).getObjectId());

                        ParseObject order = ParseObject.create("Order");
                        order.put("ord_total", String.valueOf(subtotal));
                        order.put("ord_status", "Paid");
                        ParseObject ccObj = ParseObject.createWithoutData("CreditCard", objects.get(0).getObjectId());
                        order.put("cc_id",ccObj );
                        order.put("cus_id", customerObj);
                        order.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null){
                                    Log.e(ERROR, "Error putting in order");
                                    e.printStackTrace();
                                    return;
                                }
                                Log.d("Order", "Successful");

                            }
                        });

                        for(Product product : mProductPosts){
                            ParseObject orderedItem = ParseObject.create("OrderedItem");
                            orderedItem.put("oid_quantity", product.getcartQuantity());
                            orderedItem.put("oid_price", product.getProductPrice());
                            ParseObject oObj = ParseObject.createWithoutData("Order",order.getObjectId() );
                            orderedItem.put("ord_id", order );
                            orderedItem.put("pro_id", product);
                            orderedItem.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null){
                                        Log.e(ERROR, "Error putting in item");
                                        e.printStackTrace();
                                        return;
                                    }
                                    Log.d("OrderedItem", "Successful");

                                }
                            });
                        }
                    }
                });
                Log.d("ccc",""+ ccObj);
                //ParseObject ccObj = ParseObject.createWithoutData("CreditCard","3w1vILgeWR" );

                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
                Log.d("WORKING", "It's working");
            }
        });
    }

    protected void queryPosts(Cart cart) {

        mProductPosts.addAll(cart.getListProducts());
        for(Product product : mProductPosts){
            subtotal += (Double) product.getProductPrice() * (Integer)product.getcartQuantity();
        }
        Log.d("TOTAL", "-->" + subtotal);
        totalTv.setText("$"+String.valueOf(subtotal));
        adapter.notifyDataSetChanged();

    }

    public void putCCid(String Id){
        Log.d("ccc",""+ Id);
        ccObj=Id;
    }

}

