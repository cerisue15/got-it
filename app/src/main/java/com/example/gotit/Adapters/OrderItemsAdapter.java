package com.example.gotit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.OrderedItem;
import com.example.gotit.ParseClasses.Store;
import com.example.gotit.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {

    private Context context;
    private List<OrderedItem> orderedItems;
    private Boolean favorited = false;
    private final String LISTORDERS = "LISTORDERS";
    private Cart customerCart;
    private Store store;


    public OrderItemsAdapter(Context context, List<OrderedItem> orderedItems, Cart cart){
        this.context = context;
        this.customerCart = cart;
        this.orderedItems = orderedItems;
    }

    //----------------------------------------------------------------------------------
    //  Creating the view that allows a scrollable list of products (Recycler)
    //----------------------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_items_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OrderedItem item = orderedItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return orderedItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView productName;
        private TextView quantity;
        private TextView price;
        private ImageView ivImage;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            productName = itemView.findViewById(R.id.productName);
            quantity = itemView.findViewById(R.id.quantity);
            ivImage = itemView.findViewById(R.id.ivImage);
            price = itemView.findViewById(R.id.price);

        }

        //----------------------------------------------------------------------------------
        //  Put store on Recycler View
        //----------------------------------------------------------------------------------
        public void bind(final OrderedItem item){

            item.getParseObject("pro_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    ParseObject product = object;
                    ParseFile image = product.getParseFile("img");
                    if (image != null) {
                        Glide.with(context).load(image.getUrl()).into(ivImage);
                    }
                    productName.setText(product.getString("pro_name"));
                }
            });
            quantity.setText(item.get("oid_quantity").toString());
            price.setText("$"+ item.get("oid_price").toString());

        }

    }

}
