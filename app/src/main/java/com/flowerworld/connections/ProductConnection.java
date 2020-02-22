package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.ProductGetData;
import com.flowerworld.items.FullProductItem;



public class ProductConnection {
    private ProductGetData parent;

    public void setParent(ProductGetData parent) {
        this.parent = parent;
    }

    public void bind(int id) {
        createThreadToSendProduct(id);

    }

    private FullProductItem getProduct (int id) {
        return DataBase.getFullProduct(id);
    }

    private void createThreadToSendProduct(final int id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getProduct(id);
                parent.sendMessageForSetProduct(msg);
            }
        });
        thread.start();
    }

    private void createThreadToSendComments(int id) {

    }


}
