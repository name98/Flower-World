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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.GridSearchesConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.adapters.ProductsGridAdapter;

import java.util.ArrayList;
public class GridSearchesFragment extends Fragment implements FragmentSetDataInterface {
    private Handler handler;
    static final String TAG_KEY = "tag_key";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setProgress(true);
        setHandler();
        assert getArguments() != null;
        String tag = getArguments().getString(TAG_KEY);
        setConnection(tag);
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<FlowerItem> products = (ArrayList) msg.obj;
                bind(products);
            }
        };
    }

    private void bind(ArrayList<FlowerItem> products) {
        setViews(products);
    }

    public static GridSearchesFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(TAG_KEY, text);
        GridSearchesFragment fragment = new GridSearchesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setConnection(String tag) {
        GridSearchesConnection connection = new GridSearchesConnection();
        connection.setParent(this);
        connection.bind(tag);
    }

    private void setViews(ArrayList<FlowerItem> products) {
        RecyclerView gridItemsRecycleView = getView().findViewById(R.id.grid_fragment_product_list_recycle_view);
        GridLayoutManager gridLayout = (new GridLayoutManager(getContext(), 2));
        gridItemsRecycleView.setLayoutManager(gridLayout);
        ProductsGridAdapter adapter = new ProductsGridAdapter();
        adapter.setItems(products);
        gridItemsRecycleView.setAdapter(adapter);
        setProgress(false);
    }

    private void setProgress(boolean hasToolbar) {
        Fragment parent = getParentFragment();
        if (hasToolbar) {
            Router.addProgressFragment(parent.getContext());
        }
        else
            Router.removeFragmentByTag(parent.getContext(), Router.PROGRESS_FRAGMENT_TAG);
    }



}
