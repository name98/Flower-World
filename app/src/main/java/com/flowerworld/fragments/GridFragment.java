package com.flowerworld.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;

import com.flowerworld.connections.GridFragmentHelper;

import com.flowerworld.items.adapters.FlowerItemAdapterForGrid;


import java.util.Objects;

public class GridFragment extends Fragment {

    private static final String KEY_FOR_IDS = "keyIds";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        String ids = getArguments().getString(KEY_FOR_IDS);
        GridFragmentHelper.bind(ids);
        initViews();
    }

    private void initViews() {
        RecyclerView gridItemsRecycleView = Objects.requireNonNull(getView()).findViewById(R.id.gridItemsRecycleView);
        GridLayoutManager gridLayout = (new GridLayoutManager(getContext(), 2));
        gridItemsRecycleView.setLayoutManager(gridLayout);
        FlowerItemAdapterForGrid adapter = new FlowerItemAdapterForGrid();
        adapter.setItems(GridFragmentHelper.getITEMS());
        gridItemsRecycleView.setAdapter(adapter);
    }

    static GridFragment newInstance(String ids) {
        Bundle args = new Bundle();
        args.putString(KEY_FOR_IDS, ids);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
