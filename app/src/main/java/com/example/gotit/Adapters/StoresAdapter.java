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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.Fragments.ListProductsFragment;
import com.example.gotit.R;
import com.example.gotit.ParseClasses.Store;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

//----------------------------------------------------------------------------------
//  Creating the view that allows a scrollable list of stores
//----------------------------------------------------------------------------------
public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {

    private Context context;
    private List<Store> stores;
    private Boolean favorited = false;
    private final String LISTPRODUCTS = "LISTPRODUCTS";
    private Cart cart;


    public StoresAdapter(Context context, List<Store> stores, Cart customerCart) {
        this.context = context;
        this.stores = stores;
        this.cart = customerCart;
    }

    //----------------------------------------------------------------------------------
    //  Creating the view that allows a scrollable list of stores (Recycler)
    //----------------------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.bind(store);

    }

    //----------------------------------------------------------------------------------
    //  Add the view of the stores
    //----------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return stores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName;
        private ImageButton heart_btn;
        private Button viewProducts_btn;
        private TextView tvCaption;
        private ImageView ivImage;
        private ImageButton btn_Comment;
        private TextView cnt_comment;
        private TextView cnt_like;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.storeName);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            ivImage = itemView.findViewById(R.id.ivImage);
            heart_btn = itemView.findViewById(R.id.heart_icon);
            viewProducts_btn = itemView.findViewById(R.id.viewProducts_btn);


            //----------------------------------------------------------------------------------
            //  Allows a user to favorite a store
            //----------------------------------------------------------------------------------
            heart_btn.setOnClickListener( new View.OnClickListener() {
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
        public void bind(final Store store) {

            storeName.setText(store.getStoreName());

            //----------------------------------------------------------------------------------
            //  View product
            //----------------------------------------------------------------------------------
            viewProducts_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    ListProductsFragment prodFrag = new ListProductsFragment(cart);
                    prodFrag.setStoreId(store.getStoreId());
                    Fragment myFragment = prodFrag;

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(LISTPRODUCTS).commit();
                }

            });

            //----------------------------------------------------------------------------------
            //  Add vendor images
            //----------------------------------------------------------------------------------
            store.getParseObject("ven_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
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

    }

}
//----------------------------------------------------------------------------------
//  (c) 2020, cerigoff Ceri Goff
//----------------------------------------------------------------------------------

