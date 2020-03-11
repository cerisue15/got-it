package com.example.gotit;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.fragments.ListStoresFragment;
import com.parse.ParseFile;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private Boolean favorited = false;
    private final String LISTPRODUCTS = "LISTPRODUCTS";

    public ProductsAdapter(Context context, List<Product> products){
        this.context = context;
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
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private ImageButton heart_btn;
        private Button viewProducts_btn;
        private TextView tvCaption;
        private ImageView ivImage;
        private ImageButton btn_Comment;
        private TextView cnt_comment;
        private TextView cnt_like;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivImage = itemView.findViewById(R.id.ivImage);
            heart_btn = itemView.findViewById(R.id.heart_icon);
            viewProducts_btn = itemView.findViewById(R.id.addToCart_btn);


            //----------------------------------------------------------------------------------
            //  Allows a user to favorite a store
            //----------------------------------------------------------------------------------
            heart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                    if (favorited == false) {
                        heart_btn.setImageResource(R.drawable.ic_favorite_48dp);
                        favorited = true;
                    }
                    else {
                        heart_btn.setImageResource(R.drawable.ic_favorite_border_48dp);
                        favorited = false;
                    }
                }
            });

            viewProducts_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new ListStoresFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(LISTPRODUCTS).commit();

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
