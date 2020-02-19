package com.flowerworld.ui;

import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.HomeFragmentHelper;
import com.flowerworld.items.adapters.ItemAdapter;
import com.flowerworld.models.HomeFragmentObjects;


public class HomeFragmentUI {
    private View view;
    Handler handler;

    public HomeFragmentUI(View view) {


        this.view = view;
        initHandler();
        start();
    }
    public void initHandler(){
        handler  = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                ProgressBar progressBar = view.findViewById(R.id.homeFragmentProgressBar);
                progressBar.setVisibility(View.INVISIBLE);
                HomeFragmentObjects objects = (HomeFragmentObjects) msg.obj;
                RecyclerView recyclerView = view.findViewById(R.id.recycleViewForRecycleViews);
                LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                ItemAdapter adapter = new ItemAdapter(objects.getItems(),objects.getNewsItems(),objects.getShopItems());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        };
    }
    private void start(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            try {

                Message msg = Message.obtain();
                msg.obj = new HomeFragmentHelper().getObjects();
                msg.setTarget(handler);
                handler.sendMessage(msg);
            }
            catch (Exception e){
                Log.d("HFUI: ", e.toString());
            }

            }
        });
        t.start();
    }

}
