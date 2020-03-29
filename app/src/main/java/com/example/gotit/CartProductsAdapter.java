package com.example.gotit;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.fragments.CartFragment;
import com.example.gotit.fragments.ListStoresFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;
public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder>{

    private Context context;
    private List<Product> products;
    private Boolean favorited = false;
    private final String LISTPRODUCTS = "LISTPRODUCTS";
    private Cart customerCart;
    private int pos=0;


    public CartProductsAdapter(Context context, List<Product> products, Cart cart){
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

            View view = LayoutInflater.from(context).inflate(R.layout.cartproduct_post, parent, false);

            /*customerCart.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                    Log.e("ERROR", "Error in signing up user");
                    e.printStackTrace();
                    return;
                    }
                }
            });
             */

            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = products.get(position);
        pos=position;
        holder.bind(product, holder);



    }

    @Override
    public int getItemCount() {
            return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView productName;
        private ImageButton add_quantity;
        private ImageButton minus_quantity;
        private ImageButton delete;
        private TextView tvPrice;
        private ImageView ivImage;
        private TextView tvCnt;
        int count=1;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            productName = itemView.findViewById(R.id.productName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCnt = itemView.findViewById(R.id.itemCnt);
            ivImage = itemView.findViewById(R.id.ivImage);
            add_quantity = itemView.findViewById(R.id.add);
            minus_quantity = itemView.findViewById(R.id.minus);
            delete = itemView.findViewById(R.id.delete);

            //----------------------------------------------------------------------------------
            //  Allows a user to favorite a store
            //----------------------------------------------------------------------------------

            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseQuery<Product> productQuery = new ParseQuery<Product>(Product.class);
                    productQuery.setLimit(20);
                    ParseObject obj = ParseObject.createWithoutData("Cart", customerCart.getObjectId()); // this pointer object class name and pointer value
                    productQuery.whereEqualTo("cart_id", obj); // this pointer object and parse object
                    //productQuery.addDescendingOrder(Post.KEY_CREATED_AT);
                    productQuery.findInBackground(new FindCallback<Product>() {
                        @Override
                        public void done(List<Product> products, ParseException e) {
                            if (e != null) {
                                Log.e("ERROR", "Error with Query");
                                e.printStackTrace();
                                return;
                            }
                            else {
                                products.get
                            }

                        }
                    });
                }
            });*/
        }

        //----------------------------------------------------------------------------------
        //  Put store on Recycler View
        //----------------------------------------------------------------------------------
        public void bind(final Product product, @NonNull final ViewHolder holder ) {

            productName.setText(product.getProductName());
            tvPrice.setText("$"+product.getProductPrice().toString());

            ParseFile image = product.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            tvCnt.setText("" + product.getcartQuantity());

            add_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("PLUS", "--->" + count);

                    count = (Integer)product.getcartQuantity();
                    count++;
                    product.setcartQuantity(count);
                    tvCnt.setText("" + count);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment myFragment = new CartFragment(customerCart);
                    activity.getSupportFragmentManager()
                            .beginTransaction().replace(R.id.flContainer, myFragment)
                            .commit();

                }
            });

            minus_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer)product.getcartQuantity()>0){

                        Log.d("MINUS", "--->" + count);

                        count = (Integer)product.getcartQuantity();
                        count--;
                        product.setcartQuantity(count);
                        tvCnt.setText("" + count);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Fragment myFragment = new CartFragment(customerCart);
                        activity.getSupportFragmentManager()
                                .beginTransaction().replace(R.id.flContainer, myFragment)
                                .commit();

                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerCart.deleteProductFromCart(product);
                    removeAt(getAdapterPosition());

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment myFragment = new CartFragment(customerCart);
                    activity.getSupportFragmentManager()
                            .beginTransaction().replace(R.id.flContainer, myFragment)
                            .commit();
                    Log.d("DELETE", "--> deleted");


                }
            });

            //tvCaption.setText(store.getCaption());
            /*ParseFile image = post.getImage();
               if(image != null){
                   Glide.with(context).load(image.getUrl()).into(ivImage);
               }*/
            //Log.d("COMMENT", "Comments: " + post.getCommentCount());

        }

        public void removeAt(int position) {
            products.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size());
        }
    }
}


