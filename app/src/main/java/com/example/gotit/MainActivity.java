package com.example.gotit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;


import com.example.gotit.fragments.CategoryFragment;
import com.example.gotit.fragments.FeedFragment;
//import com.example.gotit.fragments.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    private final String WHERE = "WHERE";
    private final String CATEGORY = "CATEGORY";
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private ImageView back;
    private Boolean home = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.back_btn);
        setSupportActionBar(toolbar);

        //Set Home Screen
        CategoryFragment homeFragment = new CategoryFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.flContainer, homeFragment, CATEGORY)
                .addToBackStack(CATEGORY)
                .commit();

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("CLICK", "click works");
                fragmentManager.popBackStack();
                /*if (fragmentManager.fin)){
                    fragmentManager.popBackStack();
                }*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_nav, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.action_search:
                //TODO: swap fragment here
                fragment = new CategoryFragment();
                break;
            case R.id.action_shopcart:
                fragment = new CategoryFragment();
                break;
            default: fragment = new CategoryFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();


        return true;
        //return super.onOptionsItemSelected(item);
    }

    public void hideButton(Boolean b){
        home = b;
    }


    /*
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        final FragmentManager fragmentManager = getSupportFragmentManager();


        bottomNavigationView = findViewById(R.id.top_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new FeedFragment();
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //TODO: swap fragment here
                        fragment = new CategoryFragment();
                        break;
                    case R.id.action_post:
                        fragment = new CategoryFragment();
                        break;
                    case R.id.action_profile:
                        //TODO: swap fragment here
                        fragment = new CategoryFragment();
                        break;
                    default: break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        //Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
    */

}
