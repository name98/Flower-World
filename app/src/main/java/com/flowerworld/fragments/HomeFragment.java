package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.flowerworld.R;
import com.flowerworld.connections.HomeConnection;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;
import com.flowerworld.items.adapters.ItemAdapter;


import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private Handler handlerForCategories;
    private Handler handlerForNews;
    private Handler handlerForShops;
    private ItemAdapter categoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    private void bind() {
        refreshFragment();
        switchProgress(true);
        categoriesAdapter = new ItemAdapter();
        setHandler();
        HomeConnection connection = new HomeConnection(this);
        connection.bindFirstStep();
    }

    @SuppressLint("HandlerLeak")
    private void setHandler(){
        handlerForCategories = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<Item> categories = (ArrayList<Item>) msg.obj;
                setCategories(categories);
            }
        };
        handlerForNews = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<NewsItem> news = (ArrayList<NewsItem>) msg.obj;
                setNews(news);
            }
        };
        handlerForShops = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<ShopItem> shops = (ArrayList<ShopItem>) msg.obj;
                setShops(shops);
            }
        };

    }


    static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private void setItemsRecycleView() {
        switchProgress(false);
        RecyclerView itemRecycleView = Objects.requireNonNull(getView()).findViewById(R.id.recycleViewForRecycleViews);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        categoriesAdapter.reloadAdapter();
        itemRecycleView.setAdapter(categoriesAdapter);
        itemRecycleView.setLayoutManager(manager);
    }

    public void sendCategoriesHandlerMassage(Message msg) {
        handlerForCategories.sendMessage(msg);
    }

    public void sendNewsHandlerMessage(Message msg) {
        handlerForNews.sendMessage(msg);
    }

    public void senShopsHandlerMessage(Message msg) {
        handlerForShops.sendMessage(msg);
    }

    private void setCategories(ArrayList<Item> categories) {
        System.out.println("catLoad");
        categoriesAdapter.setItemArrayList(categories);
        reloadAdapter();
    }

    private void setNews(ArrayList<NewsItem> news) {
        System.out.println("newsLoad");
        categoriesAdapter.setNewsItems(news);
        reloadAdapter();
    }

    private void setShops(ArrayList<ShopItem> shops) {
        System.out.println("shopsLoad");
        categoriesAdapter.setShopItems(shops);
        reloadAdapter();
    }

    private void switchProgress(boolean isOn) {
        ProgressBar progressBar = getView().findViewById(R.id.homeFragmentProgressBar);
        SwipeRefreshLayout refreshLayout = getView().findViewById(R.id.home_fragment_swipe_refresh_layout);
        if(isOn){
            refreshLayout.setRefreshing(false);
            refreshLayout.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            refreshLayout.setEnabled(true);
        }
    }

    private void refreshFragment() {
        SwipeRefreshLayout refreshLayout = getView().findViewById(R.id.home_fragment_swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bind();
                switchProgress(true);
            }
        });
    }

    private void reloadAdapter() {
        setItemsRecycleView();
    }

}
