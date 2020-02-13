package com.flowerworld;


import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.fragments.Router;
import com.flowerworld.methods.Methods;

public class Application {
    private DataBaseHelper dbHelper;
    private Router router;
    public Application(Context context, FragmentManager manager){
        dbHelper =  new DataBaseHelper(context);
        router = new Router(manager);
        start();
        //testStart();
    }

    public DataBaseHelper getDbHelper() {
        return dbHelper;
    }
    private void start(){
        if(Methods.isAuth(dbHelper))
            router.addFrament("mainFragment");
        else {
            router.addFrament("loginFragment");
        }
    }

    public Router getRouter() {
        return router;
    }
    private void testStart(){
        router.addFrament("test");
    }
}
