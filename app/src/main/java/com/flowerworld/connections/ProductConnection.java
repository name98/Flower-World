package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.ProductGetData;
import com.flowerworld.items.CommentItem;
import com.flowerworld.items.FullProductItem;

import java.util.ArrayList;


public class ProductConnection {
    private ProductGetData parent;

    public void setParent(ProductGetData parent) {
        this.parent = parent;
    }

    public void bind(int id, int myId) {
        createThreadToSendProduct(id);
        createThreadToSendComments(id,String.valueOf(myId));

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

    private void createThreadToSendComments(final int id, final String myId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getComments(id,myId);
                parent.sendMessageForSetComment(msg);
            }
        });
        thread.start();
    }

    private ArrayList<CommentItem> getComments(int id, String myId) {
        return DataBase.getCommentsByProductId(String.valueOf(id),myId);
    }


}
