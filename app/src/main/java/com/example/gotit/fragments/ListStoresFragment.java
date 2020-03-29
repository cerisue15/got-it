package com.example.gotit.fragments;

import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotit.Cart;
import com.example.gotit.MainActivity;
import com.example.gotit.R;
import com.example.gotit.Store;
import com.example.gotit.StoresAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

//----------------------------------------------------------------------------------
//  The activity that allows a user to Register as a customer
//----------------------------------------------------------------------------------
public class ListStoresFragment extends Fragment {

    private RecyclerView rviewPosts;
    protected final String FEED = "FEED";
    protected final String ERROR = "ERROR";
    protected StoresAdapter adapter;
    protected List<Store> mStorePosts;
    private TextView title;
    private Cart customerCart;


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
        mStorePosts = new ArrayList<>();
        //create adapter
        adapter = new StoresAdapter(getContext(), mStorePosts , customerCart);
        //set adapter on the recycler view
        rviewPosts.setAdapter(adapter);
        //set the layout manger on the recycler view
        rviewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();
    }

    protected void queryPosts() {

        ParseQuery<Store> storeQuery = new ParseQuery<Store>(Store.class);
        storeQuery.setLimit(20);
        //postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        storeQuery.findInBackground(new FindCallback<Store>() {
            @Override
            public void done(List<Store> stores, ParseException e) {
                if (e != null){
                    Log.e(ERROR, "Error with Query");
                    e.printStackTrace();
                    return;
                }
                mStorePosts.addAll(stores);
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

    public ListStoresFragment (Cart cart){
        customerCart=cart;
    }


}

