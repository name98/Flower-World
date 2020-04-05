package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;

public class GridSearchesConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String text, String userId) {
        createThread(text, userId);
    }

    private void createThread(final String text, final String userId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = DataBase.getProductsByTag(text, userId);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }
}
