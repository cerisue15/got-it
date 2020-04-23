package com.example.gotit.Fragments;

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

import com.example.gotit.Adapters.ProductsAdapter;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ListProductsSearchFragment extends Fragment {
    private Cart customerCart;
    private RecyclerView rviewPosts;
    protected final String FEED = "FEED";
    protected final String ERROR = "ERROR";
    protected List<Product> mProductPosts;
    protected ProductsAdapter adapter;
    private String storeID, searchWord;
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
        title = view.findViewById((R.id.pageTitle));
        title.setText("Products");

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


        Log.d("word"," "+searchWord);

        ParseQuery<Product> mainQuery = new ParseQuery<Product>(Product.class);
        mainQuery.whereFullText("pro_name", searchWord);
        mainQuery.findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> products, ParseException e) {

                Log.d("size", " = " + products.size());
                if (e != null){
                    Log.e(ERROR, "Error with Query");
                    e.printStackTrace();
                    return;
                }
                mProductPosts.addAll(products);
                adapter.notifyDataSetChanged();

            }
        });


    }


    public ListProductsSearchFragment (Cart cart, String word){
        customerCart=cart;
        searchWord=word;
    }
}
//----------------------------------------------------------------------------------
//  (c) 2020, cerigoff Ceri Goff
//----------------------------------------------------------------------------------


