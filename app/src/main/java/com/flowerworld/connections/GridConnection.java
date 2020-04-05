package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.fragments.GridFragment;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;

import java.util.ArrayList;

public class GridConnection {
    private GridFragment parent;

    public GridConnection(GridFragment parent) {
        this.parent = parent;
    }

    public void bind(String ids, String userId) {
        createThreadForProductItems(ids, userId);
    }

    private ArrayList<FlowerItem> getProductItemsArray(String ids, String userId) {
        ArrayList<String> idsArray = Methods.strParser(ids, " ");
        ArrayList<FlowerItem> temp = new ArrayList<>();
        for (int i = 0; i < idsArray.size(); i++) {
            temp.add(DataBase.getProductById(idsArray.get(i), userId));
        }
        return temp;
    }

    private void createThreadForProductItems(final String ids, final String userId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getProductItemsArray(ids, userId);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }
}
