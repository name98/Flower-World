package com.flowerworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.flowerworld.R;
import com.flowerworld.ui.FlowerFragmentUI;

public class    FlowerFragment extends Fragment {
    private final static String PRODUCT_ID_KEY = "product_id";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setVisibilityProgressBar(true);
        new FlowerFragmentUI(String.valueOf(getArguments().getInt(PRODUCT_ID_KEY)), view);
    }

    static FlowerFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY,id);
        FlowerFragment fragment = new FlowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setVisibilityProgressBar(boolean isVisible) {
        RelativeLayout paneRelativeLayout = getView().findViewById(R.id.progress_layout_pane);
        if (isVisible) {
            paneRelativeLayout.setVisibility(View.VISIBLE);
        }
        else
            paneRelativeLayout.setVisibility(View.INVISIBLE);
    }
}