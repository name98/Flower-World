package com.flowerworld.fragments;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flowerworld.R;

public class ChildRouter {
    private FragmentManager manager;
    private Fragment active;

    public ChildRouter(FragmentManager manager) {
        this.manager = manager;
    }
    public void addFragment(String tag, String data){
        if(isAdded(tag))
            manager.beginTransaction()
            .show(manager.findFragmentByTag(tag))
            .commit();
        else
            adding(tag,data);
    }
    private void adding(String tag, String data){
        switch (tag){
            case "homeFragment":
                HomeFragment homeFragment = new HomeFragment();
                manager.beginTransaction()
                        .add(R.id.main_fragment_contaner, homeFragment,tag)
                        .commit();
                active = homeFragment;
                break;
            case "searchFragment":
                SearchFragment searchFragment = new SearchFragment();
                manager.beginTransaction()
                        .add(R.id.main_fragment_contaner, searchFragment,tag)
                        .addToBackStack(null)
                        .hide(searchFragment)
                        .commit();
                break;
            case "personFragment":
                PersonFragment personFragment = new PersonFragment();
                manager.beginTransaction()
                        .add(R.id.main_fragment_contaner, personFragment,tag)
                        .addToBackStack(null)
                        .hide(personFragment)
                        .commit();
                break;

        }
    }
    private boolean isAdded(String tag){
        if(manager.findFragmentByTag(tag) != null)
            return true;
        else
            return false;
    }
    public void setActive(String tag){
        if(isAdded(tag))
            if (active != null){
                manager.beginTransaction()
                        .hide(active)
                        .show(manager.findFragmentByTag(tag))
                        .commit();
                active = manager.findFragmentByTag(tag);
            }
    }
}
