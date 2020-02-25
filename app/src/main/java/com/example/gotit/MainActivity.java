package com.example.gotit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;


import com.example.gotit.fragments.CategoryFragment;
import com.example.gotit.fragments.FeedFragment;
//import com.example.gotit.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private final String WHERE = "WHERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_nav, menu);
        Log.d(WHERE, "here");
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
                fragment = new FeedFragment();
                break;
            default: fragment = new CategoryFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();


        return true;
        //return super.onOptionsItemSelected(item);
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
