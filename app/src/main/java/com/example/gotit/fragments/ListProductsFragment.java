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
import com.example.gotit.Product;
import com.example.gotit.R;
import com.example.gotit.ProductsAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ListProductsFragment extends Fragment {

    private Cart customerCart;
    private RecyclerView rviewPosts;
    protected final String FEED = "FEED";
    protected final String ERROR = "ERROR";
    protected List<Product> mProductPosts;
    protected ProductsAdapter adapter;
    private String storeID;
    private TextView title;




    public void setStoreId(String id){
        storeID = id;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        rviewPosts = view.findViewById(R.id.rviewPosts);

        //create data source
        mProductPosts = new ArrayList<>();
        //create adapter
        adapter = new ProductsAdapter(getContext(), mProductPosts, customerCart);
        //set adapter on the recycler view
        rviewPosts.setAdapter(adapter);
        //set the layout manger on the recycler view
        rviewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();
    }

    protected void queryPosts() {

        Log.d("STORE", "ID = " + storeID);

        ParseQuery<Product> productQuery = new ParseQuery<Product>(Product.class);
        productQuery.setLimit(20);
        ParseObject obj = ParseObject.createWithoutData("Store",storeID); // this pointer object class name and pointer value
        productQuery.whereEqualTo("sto_id", obj); // this pointer object and parse object
        //postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
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
                /*
                for (int i=0; i<stores.size(); i++) {
                    Store store = stores.get(i);
                    //Log.d(POST, "Post: " + post.getCaption() + ", Username: " + post.getAuthor().getUsername());
                }
                */

            }
        });
    }

    public ListProductsFragment (Cart cart){
        customerCart=cart;
    }
}
