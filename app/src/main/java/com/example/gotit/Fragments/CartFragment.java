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
                        order.put("ord_total", String.valueOf(df2.format(subtotal)));
                        order.put("ord_status", "Paid");

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
                        ParseObject ccObj = ParseObject.createWithoutData("CreditCard", objects.get(0).getObjectId());
                        Log.d("cc",""+ objects.get(0).getObjectId());
                        order.put("cc_id",ccObj );
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
                                    customerCart.deleteAllProducts();
                                }
                            });
                        }
                    }
                });

                /*Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
                 */

                // Remove everything from Cart and go back to Home Page


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

