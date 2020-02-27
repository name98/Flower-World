package com.flowerworld;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.fragments.Router;

public class MainActivity extends AppCompatActivity  {

    private Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {

        super.onResume();
        Fresco.initialize(this);
        Router.start(this);
        System.out.println("start213123123");
    }

    public Application getApp() {

        return app;
    }
    public void res() {
        onRestart();
    }

    public void backPressed(){
        DataBaseHelper helper = new DataBaseHelper(this);
        if (helper.isEmpty())
            finish();
    }

    public void backPressedFromMain() {
        DataBaseHelper helper = new DataBaseHelper(this);
        if (!helper.isEmpty())
            finish();
    }




}
