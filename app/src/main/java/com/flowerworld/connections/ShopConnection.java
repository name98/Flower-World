package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.FullShopItem;

public class ShopConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String shopName, String userId) {
        createThreadToSendShop(shopName, userId);
    }

    private void createThreadToSendShop(final String shopName, final String userId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = getShop(shopName, userId);
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }

    private FullShopItem getShop(String shopName, String userId) {
        return DataBase.getShopByName(shopName, userId);
    }
}
