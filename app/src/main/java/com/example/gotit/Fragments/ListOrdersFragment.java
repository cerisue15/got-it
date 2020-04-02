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

import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.Order;
import com.example.gotit.Adapters.OrdersAdapter;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ListOrdersFragment extends Fragment {

    private Cart customerCart;
    private RecyclerView rviewPosts;
    protected final String FEED = "FEED";
    protected final String ERROR = "ERROR";
    protected List<Order> mOrderPosts;
    protected OrdersAdapter adapter;
    private String customerID;
    private TextView title;


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
        title.setText("Your Orders");

        //create data source
        mOrderPosts = new ArrayList<>();
        //create adapter
        adapter = new OrdersAdapter(getContext(), mOrderPosts, customerCart);
        //set adapter on the recycler view
        rviewPosts.setAdapter(adapter);
        //set the layout manger on the recycler view
        rviewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();
    }

    protected void queryPosts() {


        Log.d("listord", "size -->"+ customerID);
        ParseQuery<Order> orderQuery = new ParseQuery<Order>(Order.class);
        orderQuery.setLimit(20);
        ParseObject obj = ParseObject.createWithoutData("Customer", customerID); // this pointer object class name and pointer value
        orderQuery.whereEqualTo("cus_id", obj); // this pointer object and parse object
        //postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        orderQuery.findInBackground(new FindCallback<Order>() {
            @Override
            public void done(List<Order> orders, ParseException e) {
                if (e != null){
                    Log.e(ERROR, "Error with Query");
                    e.printStackTrace();
                    return;
                }
                mOrderPosts.addAll(orders);
                adapter.notifyDataSetChanged();

            }
        });
    }

    public ListOrdersFragment (String id, Cart cart){
        this.customerID = id;
        this.customerCart=cart;
    }
}

