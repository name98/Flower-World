package com.flowerworld.connections;

import android.os.Message;


import com.flowerworld.database.DataBase;
import com.flowerworld.fragments.HomeFragment;

import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;

import java.util.ArrayList;

public class HomeConnection {
    private HomeFragment parent;

    public HomeConnection(HomeFragment parent) {
        this.parent = parent;
    }

    public void bindFirstStep() {
        createThreadForItems();
        createThreadForNewsItems();
        creteThreadForShopsItems();
    }

    private ArrayList<Item> getCategoriesData() {
        return DataBase.getCategoriesArray();

    }

    private void createThreadForItems() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getCategoriesData();
                parent.sendCategoriesHandlerMassage(msg);
            }
        });
        thread.start();
    }

    private void createThreadForNewsItems() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getNewsData();
                parent.sendNewsHandlerMessage(msg);
            }
        });
        thread.start();
    }

    private ArrayList<NewsItem> getNewsData() {
        return DataBase.getNewsArray();
    }

    private ArrayList<ShopItem> getShopData() {
        return DataBase.getShopsArray();
    }

    private void creteThreadForShopsItems() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getShopData();
                parent.senShopsHandlerMessage(msg);
            }
        });
        thread.start();
    }

}
