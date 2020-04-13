package com.example.gotit.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotit.Adapters.OrderItemsAdapter;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.Customer;
import com.example.gotit.ParseClasses.CustomerAddress;
import com.example.gotit.ParseClasses.Order;
import com.example.gotit.ParseClasses.OrderedItem;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.Adapters.ProductsAdapter;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListProductsinOrderFragment extends Fragment {

    private Order order;
    Cart customerCart;
    private RecyclerView rviewPosts;
    protected final String FEED = "FEED";
    protected final String ERROR = "ERROR";
    protected List<OrderedItem> mOrderItemPosts;
    protected OrderItemsAdapter adapter;
    private TextView title;
    private DecimalFormat df2 = new DecimalFormat("#.##");


    private TextView deliv_at;
    private TextView cust_address;
    private TextView sub_total;
    private TextView tax;
    private TextView deliv_fee;
    private TextView total;
    private TextView status;

    private final Double taxpercent = 0.07;
    private final Double deliveryAmt = 5.00;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.order_details, container, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        rviewPosts = view.findViewById(R.id.rviewPosts);


        status = view.findViewById((R.id.status));
        deliv_at = view.findViewById((R.id.deliv_at));
        cust_address = view.findViewById((R.id.cust_address));
        sub_total = view.findViewById((R.id.sub_total));
        tax = view.findViewById((R.id.tax));
        deliv_fee = view.findViewById((R.id.deliv_fee));
        total = view.findViewById((R.id.total));

        title = view.findViewById((R.id.title));
        title.setText("Your Order Details");


        sub_total.setText(df2.format(Double.valueOf(order.getOrderTotal())));
        tax.setText(df2.format(taxpercent * Double.valueOf(order.getOrderTotal())));
        deliv_fee.setText(df2.format(deliveryAmt));
        total.setText(df2.format(taxpercent + deliveryAmt + Double.valueOf(order.getOrderTotal())));


        //Parse Query for Customer adress
        order.getParseObject("add_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                cust_address.setText(object.getString("add_street")
                        + " " + object.getString("add_city")
                        + ", " + object.getString("add_state")
                        + " " + object.getString("add_zipcode"));
            }
        });

        //Post Status Text
        status.setText("Your order status: " + order.get("ord_status"));

        //Post Address Text
        String pattern = "MM-dd-yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = simpleDateFormat.format(order.getUpdatedAt());
        deliv_at.setText(date);

        //create data source
        mOrderItemPosts = new ArrayList<>();
        //create adapter
        adapter = new OrderItemsAdapter(getContext(), mOrderItemPosts, customerCart);
        //set adapter on the recycler view
        rviewPosts.setAdapter(adapter);
        //set the layout manger on the recycler view
        rviewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();
    }

    protected void queryPosts() {

        ParseQuery<OrderedItem> orderedItemParseQuery = new ParseQuery<OrderedItem>(OrderedItem.class);
        orderedItemParseQuery.setLimit(20);
        ParseObject obj = ParseObject.createWithoutData("Order",order.getObjectId()); // this pointer object class name and pointer value
        orderedItemParseQuery.whereEqualTo("ord_id", obj); // this pointer object and parse object
        orderedItemParseQuery.findInBackground(new FindCallback<OrderedItem>() {
            @Override
            public void done(List<OrderedItem> orderedItems, ParseException e) {
                if (e != null){
                    Log.e(ERROR, "Error with Query");
                    e.printStackTrace();
                    return;
                }
                mOrderItemPosts.addAll(orderedItems);
                adapter.notifyDataSetChanged();

            }
        });
    }

    public ListProductsinOrderFragment(Order o){
        order=o;
    }
}
