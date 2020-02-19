package com.flowerworld.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.DataMethod;

import com.flowerworld.connections.GridFragmentHelper;
import com.flowerworld.items.adapters.FlowerItemAdapter;
import com.flowerworld.items.adapters.FlowerItemAdapterForGrid;


import org.json.JSONArray;


import java.util.ArrayList;

public class GridFragment extends Fragment {

    private String ids;
    private String title;
    private static final String KEY_FOR_IDS = "keyIds";

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String ids = getArguments().getString(KEY_FOR_IDS);
        GridFragmentHelper.bind(ids);
        initViews();
    }

    private void initViews(){
        RecyclerView gridItemsRecycleView = getView().findViewById(R.id.gridItemsRecycleView);
        GridLayoutManager gridLayout = (new GridLayoutManager(getContext(),2));
        gridItemsRecycleView.setLayoutManager(gridLayout);
        FlowerItemAdapterForGrid adapter = new FlowerItemAdapterForGrid();
        adapter.setItems(GridFragmentHelper.getITEMS());
        gridItemsRecycleView.setAdapter(adapter);
    }

    public static GridFragment newInstance(String ids) {

        Bundle args = new Bundle();
        args.putString(KEY_FOR_IDS,ids);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
