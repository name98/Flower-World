package com.flowerworld.ui;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.flowerworld.R;
import com.flowerworld.fragments.ChildRouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragmentUI {
    private View view;
    private Fragment fragment;
    private ChildRouter router;
    private BottomNavigationView bottomNavigationView;

    public MainFragmentUI(View view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
        start();

    }
    private void start(){
        router = new ChildRouter(fragment.getChildFragmentManager());
        router.addFragment("homeFragment","");
        router.addFragment("searchFragment","");
        router.addFragment("personFragment","");
        //router.setActive("homeFragment");
        createUI();
    }
    private void createUI(){
        bottomNavigationView = view.findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        router.setActive("homeFragment");
                        return true;
                    case R.id.nav_person:
                        Log.d(String.valueOf(menuItem.getItemId())," :is");
                        router.setActive("personFragment");
                        return true;
                    case R.id.nav_abc:
                        router.setActive("searchFragment");
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

}
