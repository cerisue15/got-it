package com.example.gotit.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.Fragments.CartFragment;
import com.example.gotit.ParseClasses.Product;
import com.example.gotit.R;
import com.parse.ParseFile;

import java.text.DecimalFormat;
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

        }

        //----------------------------------------------------------------------------------
        //  Put store on Recycler View
        //----------------------------------------------------------------------------------
        public void bind(final Product product, @NonNull final ViewHolder holder ) {

            // Get Product Name
            productName.setText(product.getProductName());

            // Get Product Price
            DecimalFormat df2 = new DecimalFormat("#.##");
            tvPrice.setText("$"+df2.format(product.getProductPrice()).toString());

            // Get Product Image
            ParseFile image = product.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            // Get Product Quantity for Cart
            tvCnt.setText("" + product.getcartQuantity());

            add_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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

                        count = (Integer)product.getcartQuantity();
                        if (count > 1) {
                            count--;
                            product.setcartQuantity(count);
                            tvCnt.setText("" + count);
                        }

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
                }
            });


        }

        public void removeAt(int position) {
            products.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size());
        }
    }
}


