package com.flowerworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addChildFragments();
        setListenerBottomNavigation();
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

    private void addChildFragments() {
        ChildRouter.addSearchFragment(this);
        ChildRouter.addPersonFragment(this);
        ChildRouter.addHomeFragment(this);
        ChildRouter.hideFragment(this, ChildRouter.PERSON_FRAGMENT_TAG);
        ChildRouter.hideFragment(this, ChildRouter.SEARCH_FRAGMENT_TAG);
    }

    private void setListenerBottomNavigation() {
        final Fragment fragment = this;
        BottomNavigationView menuBottomNavigationView = Objects.requireNonNull(getView()).findViewById(R.id.main_fragment_menu_bottom_navigation_view);
        menuBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_item:
                        ChildRouter.showFragment(fragment, ChildRouter.HOME_FRAGMENT_TAG);
                        ChildRouter.hideFragment(fragment, ChildRouter.PERSON_FRAGMENT_TAG);
                        ChildRouter.hideFragment(fragment, ChildRouter.SEARCH_FRAGMENT_TAG);
                        return true;
                    case R.id.person_item:
                        ChildRouter.hideFragment(fragment, ChildRouter.HOME_FRAGMENT_TAG);
                        ChildRouter.showFragment(fragment, ChildRouter.PERSON_FRAGMENT_TAG);
                        ChildRouter.hideFragment(fragment, ChildRouter.SEARCH_FRAGMENT_TAG);
                        return true;
                    case R.id.search_item:
                        ChildRouter.hideFragment(fragment, ChildRouter.HOME_FRAGMENT_TAG);
                        ChildRouter.hideFragment(fragment, ChildRouter.PERSON_FRAGMENT_TAG);
                        ChildRouter.showFragment(fragment, ChildRouter.SEARCH_FRAGMENT_TAG);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).backPressedFromMain();
        super.onDestroy();

    }
}
