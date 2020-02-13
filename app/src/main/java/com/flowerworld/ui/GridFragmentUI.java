package com.flowerworld.ui;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.DataMethod;
import com.flowerworld.connections.FlowerFragmentHalper;
import com.flowerworld.connections.GridFragmentHelper;
import com.flowerworld.items.adapters.FlowerItemAdapter;

import org.json.JSONArray;


public class GridFragmentUI {
    Handler handler;
    View view;
    String ids;

    public GridFragmentUI(String ids, View view) {
        this.ids = ids;
        this.view=view;
        init();
        start();
    }
    private void init(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                GridFragmentHelper helper = (GridFragmentHelper) msg.obj;
                createUI(helper);
            }
        };
    }
    private void start(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = Message.obtain();
                    msg.obj = new GridFragmentHelper(ids);
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                }
                catch (Exception e){
                    Log.d("GFUI: ", e.toString());
                }
            }
        });
        t.start();
    }
    private void createUI(GridFragmentHelper helper){
        final RecyclerView recyclerView = view.findViewById(R.id.recVGrid);
        FlowerItemAdapter adapter = new FlowerItemAdapter(helper.getItems());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
    }
}
