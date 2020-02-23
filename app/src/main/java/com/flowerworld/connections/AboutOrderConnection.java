package com.flowerworld.connections;

import com.flowerworld.interfaces.FragmentSetDataInterface;

public class AboutOrderConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind() {

    }

    private void createThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        })
    }
}
