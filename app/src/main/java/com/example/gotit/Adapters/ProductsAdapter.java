package com.example.gotit.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.Activities.LoginActivity;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private Boolean favorited = false;
    private final String LISTPRODUCTS = "LISTPRODUCTS";
    private Cart customerCart;


    public ProductsAdapter(Context context, List<Product> products, Cart cart){
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.bind(product);
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

        }

        //----------------------------------------------------------------------------------
        //  Put store on Recycler View
        //----------------------------------------------------------------------------------
        public void bind(final Product product){

            productName.setText(product.getProductName());
            ParseFile image = product.getImage();
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvCaption.setText("$"+product.getProductPrice().toString());



            addToCart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    product.getParseObject("sto_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            ParseObject store = object;
                            String name = store.getString("sto_name");

                            if (customerCart.isProductFromStore(name) == false){
                                Toast.makeText(v.getContext(), "Can't add product to cart! \n Product is from a different store", Toast.LENGTH_LONG).show();
                            }
                            else {
                                customerCart.addProductToCart(product);
                                product.getParseObject("sto_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        ParseObject store = object;
                                        String nameProd = store.getString("sto_name");

                                        customerCart.setStore(nameProd, store.getObjectId());
                                    }
                                });
                            }
                        }
                    });


                        //product.put("cart_id", ParseObject.createWithoutData("Cart", customerCart.getObjectId()) );
                        //product.saveInBackground();

                }
            });

        }

    }

}
//----------------------------------------------------------------------------------
//  (c) 2020, cerigoff Ceri Goff
//----------------------------------------------------------------------------------

