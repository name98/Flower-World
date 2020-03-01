package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;

import com.flowerworld.connections.GridConnection;

import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.adapters.ProductsGridAdapter;
import com.google.android.material.appbar.AppBarLayout;


import java.util.ArrayList;
import java.util.Objects;

public class GridFragment extends Fragment {

    private static final String KEY_FOR_IDS = "key_ids";
    private static final String KEY_FOR_TITLE = "key_title";
    private Handler handlerForProductItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolBar();
        setVisibilityProgressBar(true);
        assert getArguments() != null;
        String ids = getArguments().getString(KEY_FOR_IDS);
        bind(ids);
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handlerForProductItems = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<FlowerItem> products = (ArrayList<FlowerItem>) msg.obj;
                setProductItems(products);
            }
        };
    }

    static GridFragment newInstance(String ids, String title) {
        Bundle args = new Bundle();
        args.putString(KEY_FOR_IDS, ids);
        args.putString(KEY_FOR_TITLE, title);
        System.out.println(title);
        GridFragment fragment = new GridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setProductItems(ArrayList<FlowerItem> products) {
        RecyclerView gridItemsRecycleView = Objects.requireNonNull(getView()).findViewById(R.id.grid_fragment_product_list_recycle_view);
        GridLayoutManager gridLayout = (new GridLayoutManager(getContext(), 2));
        gridItemsRecycleView.setLayoutManager(gridLayout);
        ProductsGridAdapter adapter = new ProductsGridAdapter();
        adapter.setItems(products);
        gridItemsRecycleView.setAdapter(adapter);
        setVisibilityProgressBar(false);
    }

    public void sendMessage(Message msg) {
        handlerForProductItems.sendMessage(msg);
    }

    private void bind(String ids) {
        setHandler();
        GridConnection connection = new GridConnection(this);
        connection.bind(ids);
    }

    private void setVisibilityProgressBar(boolean visibility) {
        if (visibility)
            Router.addProgressFragment(getContext());
        else
            Router.removeFragmentByTag(getContext(), Router.PROGRESS_FRAGMENT_TAG);
    }

    private void setToolBar() {
        assert getArguments() != null;
        String title = getArguments().getString(KEY_FOR_TITLE);
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.GRID_FRAGMENT_TAG);
            }
        });
    }
}
