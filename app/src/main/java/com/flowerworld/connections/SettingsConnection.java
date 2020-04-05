package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;

public class SettingsConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String email) {
        startThread(email);
    }

    private void startThread(final String email) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = DataBase.getSettingsData(email);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }
}
