package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.FlowerItem;

public class CharterConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(int id) {
        createThread(id);
    }

    private void createThread(final int id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getProduct(id);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }

    private FlowerItem getProduct(int id) {
        return DataBase.getProductById(String.valueOf(id));
    }


}
