package com.flowerworld.connections;

import com.flowerworld.database.DataBase;
import com.flowerworld.models.scripts.Scripts;

public class FollowButton {
    public static void setLike(final String userId, final String goodId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DataBase.insertFromScript(Scripts.inserFollow(userId, goodId));
            }
        });
        t.start();
    }

    public static void setUnLike(final String userId, final String goodId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DataBase.insertFromScript(Scripts.deleteFolllow(userId, goodId));
            }
        });
        t.start();
    }
}
