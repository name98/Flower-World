package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.models.UserData;

public class SignInConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(UserData userData) {
        tryAdding(userData);
    }

    private void tryAdding(final UserData userData) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = DataBase.insertUserData(userData);
                parent.sendMessage(msg);
            }
        });
        t.start();
    }
}
