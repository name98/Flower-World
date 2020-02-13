package com.flowerworld.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.DataMethod;

import com.flowerworld.items.adapters.FlowerItemAdapter;
import com.flowerworld.ui.GridFragmentUI;

import org.json.JSONArray;


import java.util.ArrayList;

public class GridFragment extends Fragment {
    private String ids;
    private String title;
    public GridFragment(String title, String ids) {
        this.title = title;
        this.ids = ids;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new GridFragmentUI(ids,view);
    }


}
