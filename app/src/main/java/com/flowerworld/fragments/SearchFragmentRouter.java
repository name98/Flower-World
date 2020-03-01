package com.flowerworld.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.flowerworld.R;

public class SearchFragmentRouter {
    final static String PROGRESS_FRAGMENT_TAG = "progress_fragment";
    final static String GRID_FRAGMENT_SEARCH_TAG = "grid_fragment";
    final static String LIST_FRAGMENT_TAG = "list_fragment";
    final static int CONTAINER = R.id.search_fragment_edit_mode_container;
    public static void addLoadFragment(Fragment parentFragment) {
            FragmentManager childFragmentManager = parentFragment.getChildFragmentManager();
            ProgressFragment progressFragment = ProgressFragment.newInstance();
            childFragmentManager.beginTransaction()
                    .add(CONTAINER, progressFragment, PROGRESS_FRAGMENT_TAG)
                    .commit();
    }

    public static void hideFragmentByTag(Fragment parentFragment, String tag) {
        FragmentManager childManager = parentFragment.getChildFragmentManager();
        Fragment fragmentByTag = childManager.findFragmentByTag(tag);
        if(fragmentByTag != null) {
            childManager.beginTransaction()
                    .hide(fragmentByTag)
                    .commit();
        }
    }

    public static void showFragmentByTag(Fragment parentFragment, String tag) {
        FragmentManager childManager = parentFragment.getChildFragmentManager();
        Fragment fragmentByTag = childManager.findFragmentByTag(tag);
        if(fragmentByTag != null){
            childManager.beginTransaction()
                    .show(fragmentByTag)
                    .commit();
        }
    }

    public static void addListFragment(Fragment parentFragment, String text) {
        FragmentManager childManager = parentFragment.getChildFragmentManager();
        ListFragmentForSearch fragment = ListFragmentForSearch.newInstance(text);
        childManager.beginTransaction()
                .add(CONTAINER, fragment, LIST_FRAGMENT_TAG)
                .commit();
    }

    public static void addGridFragment(Fragment parentFragment, String text) {
        FragmentManager childManager = parentFragment.getChildFragmentManager();
        GridSearchesFragment gridSearchesFragment = GridSearchesFragment.newInstance(text);
        childManager.beginTransaction()
                .add(CONTAINER, gridSearchesFragment, GRID_FRAGMENT_SEARCH_TAG)
                .commit();
    }

    public static void reloadGridFragment(Fragment parentFragment, String text) {
        FragmentManager childManager = parentFragment.getChildFragmentManager();
        Fragment fragment = childManager.findFragmentByTag(GRID_FRAGMENT_SEARCH_TAG);
        if (fragment != null) {
            Bundle arg = new Bundle();
            arg.putString(GridSearchesFragment.TAG_KEY, text);
            fragment.setArguments(arg);
            childManager.beginTransaction()
                    .detach(fragment)
                    .attach(fragment)
                    .commit();
        }
        else addGridFragment(parentFragment, text);

    }

    public static void reloadListFragment(Fragment parentFragment, String text) {
        FragmentManager childManager = parentFragment.getChildFragmentManager();
        Fragment fragment = childManager.findFragmentByTag(LIST_FRAGMENT_TAG);
        if (fragment != null) {
            Bundle arg = new Bundle();
            arg.putString(ListFragmentForSearch.KEY_FOR_TEXT, text);
            fragment.setArguments(arg);
            childManager.beginTransaction()
                    .detach(fragment)
                    .attach(fragment)
                    .commit();
        }
        else
            addListFragment(parentFragment, text);
    }
}
