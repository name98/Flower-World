package com.flowerworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.flowerworld.R;
import com.flowerworld.ui.FlowerFragmentUI;

public class    FlowerFragment extends Fragment {
    private int id;
    private final static String PRODUCT_ID_KEY = "product_id";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flower_page_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new FlowerFragmentUI(String.valueOf(getArguments().getInt(PRODUCT_ID_KEY)), view);
    }

    public static FlowerFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY,id);
        FlowerFragment fragment = new FlowerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}