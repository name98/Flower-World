package com.flowerworld.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.MainActivity;
import com.flowerworld.R;

import com.flowerworld.connections.OrdersFragmentHelper;
import com.flowerworld.items.OrderItem;
import com.flowerworld.items.adapters.OrderItemAdapter;

import java.util.ArrayList;


public class OrdersFragmentUI {
    private boolean completed;
    private View view;
    private Handler handler;

    public OrdersFragmentUI(boolean completed, View view) {
        this.completed = completed;
        this.view = view;
        start();
    }

    private void start() {
        String userId = ((MainActivity) view.getContext()).getApp().getDbHelper().getKey();
        initHandler();
        startHandle(userId);
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                craeteUI((ArrayList<OrderItem>) msg.obj);
            }
        };
    }

    private void startHandle(final String id) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Message msg = Message.obtain();
                    msg.obj = new OrdersFragmentHelper(id, completed).getItems();
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Log.d("HFUI: ", e.toString());
                }

            }
        });
        t.start();
    }

    private void craeteUI(ArrayList<OrderItem> items) {
        RecyclerView recyclerView = view.findViewById(R.id.ordersFragmentRV);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        OrderItemAdapter adapter = new OrderItemAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}
