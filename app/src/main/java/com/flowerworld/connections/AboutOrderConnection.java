package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;

public class AboutOrderConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String orderId) {
        createThread(orderId);
    }

    private void createThread(final String orderId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = DataBase.getOrderInformation(orderId);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }
}
