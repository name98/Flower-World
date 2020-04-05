package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetTwoMessage;
import com.flowerworld.items.CharterItem;
import com.flowerworld.items.FlowerItem;

public class CharterConnection {
    private FragmentSetTwoMessage parent;

    public void setParent(FragmentSetTwoMessage parent) {
        this.parent = parent;
    }

    public void bind(int id) {
        createThreadToReadCharter(id);
    }

    private void createThreadToReadCharter(final int id) {
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

    public void bind2(CharterItem charter) {
        createThreadToSendCharter(charter);
    }

    private void createThreadToSendCharter(final CharterItem charter) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = insertDate(charter);
                parent.sendMessage2(msg);
            }
        });
        t.start();

    }

    private boolean insertDate(CharterItem charter) {
        DataBase.insertCharter(charter);
        return true;
    }


}
