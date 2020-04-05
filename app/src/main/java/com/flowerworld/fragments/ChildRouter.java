package com.flowerworld.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flowerworld.R;

public class ChildRouter {

    final static String HOME_FRAGMENT_TAG = "home_fragment";
    final static String SEARCH_FRAGMENT_TAG = "search_fragment";
    public final static String SETTINGS_FRAGMENT_TAG = "settings_fragment";
    private final static int FRAGMENT_CONTAINER = R.id.main_fragment_contaner;



    static void addHomeFragment(Fragment fragment) {
        FragmentManager childManager = fragment.getChildFragmentManager();
        HomeFragment homeFragment = HomeFragment.newInstance();
        childManager.beginTransaction()
                .add(FRAGMENT_CONTAINER, homeFragment,HOME_FRAGMENT_TAG)
                .commit();
    }

    static void showFragment(Fragment fragment, String tag) {
        FragmentManager childManager = fragment.getChildFragmentManager();
        Fragment fragmentByTag = childManager.findFragmentByTag(tag);
        if(fragmentByTag != null){
            childManager.beginTransaction()
                    .show(fragmentByTag)
                    .commit();
        }
    }

    static void hideFragment(Fragment fragment, String tag) {
        FragmentManager childManager = fragment.getChildFragmentManager();
        Fragment fragmentByTag = childManager.findFragmentByTag(tag);
        if(fragmentByTag != null) {
            childManager.beginTransaction()
                    .hide(fragmentByTag)
                    .commit();
        }
    }

    static void addSearchFragment(Fragment fragment) {
        FragmentManager childManager = fragment.getChildFragmentManager();
        SearchFragment searchFragment = SearchFragment.newInstance();
        childManager.beginTransaction()
                .add(FRAGMENT_CONTAINER, searchFragment, SEARCH_FRAGMENT_TAG)
                .commit();
    }

    static void addSettingsFragment(Fragment fragment) {
        FragmentManager childManager = fragment.getChildFragmentManager();
        SettingsFragment settingsFragment = SettingsFragment.newInstance();
        childManager.beginTransaction()
                .add(FRAGMENT_CONTAINER, settingsFragment, SETTINGS_FRAGMENT_TAG)
                .commit();
    }

    public static Fragment getFragmentByTag(Fragment parent, String tag) {
        FragmentManager childManager = parent.getChildFragmentManager();
        return childManager.findFragmentByTag(tag);
    }
}
