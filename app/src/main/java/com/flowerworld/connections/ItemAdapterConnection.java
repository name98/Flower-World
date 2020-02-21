package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.adapters.ItemAdapter;

import java.util.ArrayList;

public class ItemAdapterConnection {
    private ItemAdapter.ItemHolder parent;

    public ItemAdapterConnection(ItemAdapter.ItemHolder parent) {
        this.parent = parent;
    }

    public void bind(ArrayList<String> ids) {
        createThreadForProducts(ids);
    }

    private ArrayList<FlowerItem> getProducts(ArrayList<String> ids) {
        ArrayList<FlowerItem> temp = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            temp.add(DataBase.getProductById(ids.get(i)));
        }
        return temp;
    }

    private void createThreadForProducts(final ArrayList<String> ids) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getProducts(ids);
                parent.sendProductsMessage(msg);
            }
        });
        thread.start();
    }
}
