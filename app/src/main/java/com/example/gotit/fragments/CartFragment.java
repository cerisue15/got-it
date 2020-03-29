package com.example.gotit.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotit.Cart;
import com.example.gotit.CartProductsAdapter;
import com.example.gotit.Product;
import com.example.gotit.ProductsAdapter;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    private Cart customerCart;
    private Double subtotal=0.0;

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

        //create data source
        mProductPosts = new ArrayList<>();
        //create adapter
        adapter = new CartProductsAdapter(getContext(), mProductPosts, customerCart);
        //set adapter on the recycler view
        rviewPosts.setAdapter(adapter);
        //set the layout manger on the recycler view
        rviewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts(customerCart);
    }

    protected void queryPosts(Cart cart) {

        /*Log.d("STORE", "ID = " + storeID);

        ParseQuery<Product> productQuery = new ParseQuery<Product>(Product.class);
        productQuery.setLimit(20);
        ParseObject obj = ParseObject.createWithoutData("Cart",customerCart.getObjectId()); // this pointer object class name and pointer value
        productQuery.whereEqualTo("cart_id", obj); // this pointer object and parse object
        //productQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        productQuery.findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> products, ParseException e) {
                if (e != null){
                    Log.e(ERROR, "Error with Query");
                    e.printStackTrace();
                    return;
                }

                mProductPosts.addAll(products);
                adapter.notifyDataSetChanged();

            }
        });
        */
        mProductPosts.addAll(cart.getListProducts());
        for(Product product : mProductPosts){
            subtotal += (Double) product.getProductPrice() * (Integer)product.getcartQuantity();
        }
        Log.d("TOTAL", "-->" + subtotal);
        totalTv.setText(String.valueOf(subtotal));
        adapter.notifyDataSetChanged();

    }

}

