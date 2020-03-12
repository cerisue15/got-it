package com.example.gotit;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private Boolean favorited = false;
    private final String LISTPRODUCTS = "LISTPRODUCTS";
    private ParseObject customerCart;


    public ProductsAdapter(Context context, List<Product> products, ParseObject cart){
        this.context = context;
        this.customerCart = cart;
        this.products = products;
    }

    //----------------------------------------------------------------------------------
    //  Creating the view that allows a scrollable list of products (Recycler)
    //----------------------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_post, parent, false);

        customerCart.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("ERROR", "Error in signing up user");
                    e.printStackTrace();
                    return;
                }
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.bind(product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Position", "--> "+ product.getProductName());
                product.put("cart_id", ParseObject.createWithoutData("Cart", customerCart.getObjectId()) );
                product.saveInBackground();

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView productName;
        private ImageButton heart_btn;
        private Button addToCart_btn;
        private TextView tvCaption;
        private ImageView ivImage;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            productName = itemView.findViewById(R.id.productName);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivImage = itemView.findViewById(R.id.ivImage);
            heart_btn = itemView.findViewById(R.id.heart_icon);
            addToCart_btn = itemView.findViewById(R.id.addToCart_btn);


            //----------------------------------------------------------------------------------
            //  Allows a user to favorite a store
            //----------------------------------------------------------------------------------
            heart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (favorited == false) {
                        heart_btn.setImageResource(R.drawable.ic_favorite_48dp);
                        favorited = true;
                    } else {
                        heart_btn.setImageResource(R.drawable.ic_favorite_border_48dp);
                        favorited = false;
                    }
                }
            });

            addToCart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        //----------------------------------------------------------------------------------
        //  Put store on Recycler View
        //----------------------------------------------------------------------------------
        public void bind(Product product){

            productName.setText(product.getProductName());
            ParseFile image = product.getImage();
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            //tvCaption.setText(store.getCaption());
            /*ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }*/
            //Log.d("COMMENT", "Comments: " + post.getCommentCount());



        }

    }

}
