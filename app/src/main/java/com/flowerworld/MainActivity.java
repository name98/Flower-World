package com.flowerworld;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.flowerworld.fragments.Router;

public class MainActivity extends AppCompatActivity  {

    private Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Router.start(this);
    }

    public Application getApp() {
        return app;
    }


}
