package com.example.gotit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;


import com.example.gotit.fragments.CartFragment;
import com.example.gotit.fragments.CategoryFragment;
import com.example.gotit.fragments.ListStoresFragment;
import com.parse.ParseObject;
//import com.example.gotit.fragments.ProfileFragment;

//----------------------------------------------------------------------------------
// The activity that sets up the framework for the app
// Shows the main page the user gets redirected to
//----------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity {

    private final String WHERE = "WHERE";
    private final String CATEGORY = "CATEGORY";
    private final String CART = "CART";
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private ImageView back;
    private Boolean home = false;
    private DrawerLayout drawer;
    private Cart cart = new Cart();
    //private ParseObject cart;

    //----------------------------------------------------------------------------------
    // Sets the view
    //----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.back_btn);
        setSupportActionBar(toolbar);

        //ParseApplication application=(ParseApplication) getApplication();
        //cart = application.getCart();

        Log.d("cart", "--> "+ cart.getObjectId());
        //----------------------------------------------------------------------------------
        // Create Navigation Drawer
        //----------------------------------------------------------------------------------
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //----------------------------------------------------------------------------------
        //Set Container for Fragments
        //----------------------------------------------------------------------------------
        CategoryFragment homeFragment = new CategoryFragment(cart);
        fragmentManager.beginTransaction()
                .replace(R.id.flContainer, homeFragment, CATEGORY)
                .addToBackStack(CATEGORY)
                .commit();

        //----------------------------------------------------------------------------------
        // Functionality for back button
        //----------------------------------------------------------------------------------
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("CLICK", "click works");
                fragmentManager.popBackStack();

            }
        });
    }

    //----------------------------------------------------------------------------------
    //Functionality to move navigation back off screen
    //----------------------------------------------------------------------------------
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    //----------------------------------------------------------------------------------
    //Show toolbar
    //----------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_nav, menu);

        return true;
    }

    //----------------------------------------------------------------------------------
    //Creating Functionality for buttons on toolbar
    //----------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Fragment fragment = new CategoryFragment(cart);

        switch (item.getItemId()) {
            case R.id.action_search:
                //TODO: swap fragment here
                break;
            case R.id.action_shopcart:
                fragment = new CartFragment(cart);
                break;
            default:
        }

        fragmentManager.beginTransaction()
                .replace(R.id.flContainer, fragment)
                .addToBackStack(CART)
                .commit();


        return true;
        //return super.onOptionsItemSelected(item);
    }


    public void hideButton(Boolean b){
        home = b;
    }


}
