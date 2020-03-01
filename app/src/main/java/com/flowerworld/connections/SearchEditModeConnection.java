package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;

public class SearchEditModeConnection {
    private FragmentSetDataInterface parent;
    private Thread thread;


    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String text) {
        if (!text.equals(""))
            createThread(text);
    }

    private void createThread(final String text) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = DataBase.getSearches(text);
                parent.sendMessage(msg);
            }
        });
        System.out.println("starting");
        thread.start();
    }

    public void stopThread() {

        System.out.println("stopping");
        if (thread != null && thread.isInterrupted()) {
            System.out.println("stopped");
            thread.interrupt();
        }
    }
}
