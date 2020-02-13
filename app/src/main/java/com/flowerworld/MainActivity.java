package com.flowerworld;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity  {

    private Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = new Application(this,
                getSupportFragmentManager());
        getSupportActionBar().hide();

    }

    public Application getApp() {
        return app;
    }
}
