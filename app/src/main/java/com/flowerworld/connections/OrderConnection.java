package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.OrderItem;

import java.util.ArrayList;

public class OrderConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(boolean isCompleted, String userId) {
        createThreadToGetOrders(isCompleted,userId);
    }

    private void createThreadToGetOrders(final boolean isCompleted, final String userID) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getOrders(isCompleted,userID);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }
    private ArrayList<OrderItem> getOrders(boolean isCompleted, String userID) {
        return DataBase.getOrdersByUserId(isCompleted,userID);
    }
}
