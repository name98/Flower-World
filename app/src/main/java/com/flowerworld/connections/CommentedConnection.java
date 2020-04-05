package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;

public class CommentedConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String userId) {
        getData(userId);
    }

    private void getData(final String userId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = DataBase.getItemsByCommented(userId);
                parent.sendMessage(msg);
            }
        });
        t.start();
    }
}
