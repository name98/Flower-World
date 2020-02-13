package com.flowerworld.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;
import com.flowerworld.connections.FlowerFragmentHalper;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.ui.CharterFragmentUI;

public class CharterFragment extends Fragment {
    private FlowerFragmentHalper helper;

    public CharterFragment(FlowerFragmentHalper helper) {
        this.helper = helper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.charter_fragmnet,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new CharterFragmentUI(view,helper);
    }
}
