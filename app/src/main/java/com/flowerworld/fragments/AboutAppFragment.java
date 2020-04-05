package com.flowerworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;

import java.util.Objects;

public class AboutAppFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_app_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setTollbar();
    }

    private void setTollbar() {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        actionBar.setTitle("");
        assert actionBar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.ABOUT_APP_TAG);
            }
        });
    }

    public static AboutAppFragment newInstance() {
        return new AboutAppFragment();
    }
}
