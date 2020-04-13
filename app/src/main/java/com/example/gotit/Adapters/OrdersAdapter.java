package com.example.gotit.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.Fragments.ListProductsinOrderFragment;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.Order;
import com.example.gotit.R;
import com.example.gotit.ParseClasses.Store;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private static final String LISTORDERPRODUCTS = "LISTORDERPRODUCTS";
    private Context context;
    private List<Order> orders;
    private Boolean favorited = false;
    private final String LISTORDERS = "LISTORDERS";
    private Cart customerCart;
    private Store store;


    public OrdersAdapter(Context context, List<Order> orders, Cart cart){
        this.context = context;
        this.customerCart = cart;
        this.orders = orders;
    }

    //----------------------------------------------------------------------------------
    //  Creating the view that allows a scrollable list of products (Recycler)
    //----------------------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView storeName;
        private Button viewOrder_btn;
        private TextView tvDate;
        private ImageView ivImage;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            storeName = itemView.findViewById(R.id.storeName);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivImage = itemView.findViewById(R.id.ivImage);
            viewOrder_btn = itemView.findViewById(R.id.viewOrder_btn);

        }

        //----------------------------------------------------------------------------------
        //  Put store on Recycler View
        //----------------------------------------------------------------------------------
        public void bind(final Order order){

            Log.d("orderid", "-->"+ order.getObjectId());

            order.getParseObject("sto_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    storeName.setText(object.getString("sto_name"));

                    object.getParseObject("ven_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            ParseObject vendor = object;
                            ParseFile image = vendor.getParseFile("img");
                            if (image != null) {
                                Glide.with(context).load(image.getUrl()).into(ivImage);
                            }
                        }
                    });
                }
            });

            String pattern = "MM-dd-yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
            String date = simpleDateFormat.format(order.getCreatedAt());

            tvDate.setText(date);

            Log.d("cust", "-->"+ order.get("cus_id"));

            viewOrder_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ListProductsinOrderFragment prodFrag = new ListProductsinOrderFragment(order);
                    Fragment myFragment = prodFrag;
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(LISTORDERPRODUCTS).commit();

                }
            });

        }

    }

}
