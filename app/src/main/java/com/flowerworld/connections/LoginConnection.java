package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.UserItem;

public class LoginConnection {
    private FragmentSetDataInterface parent;

    public void setParent(FragmentSetDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String email, String userPassword) {
        createThreadForLogIn(email, userPassword);
    }

    private void createThreadForLogIn(final String email, final String userPassword) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg  = Message.obtain();
                UserItem user = getUser(email , userPassword);
                if (user == null) {
                    msg.what = 1;
                    msg.arg1 = 0;
                }
                else {
                    msg.what = 2;
                    msg.arg1 = 1;
                    msg.obj = user;
                }
                parent.sendMessage(msg);
            }
        });
        thread.start();
    }

    private UserItem getUser(String email, String password) {
        return DataBase.getUserByEmailAndPassword(email, password);
    }


}
