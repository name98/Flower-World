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
        System.out.println("SELECT g.время, g.число, g.статус, g.товар, a1.цена, g.Получатель \n" +
                "                    FROM\n" +
                "                    `заказы` as `g` \n" +
                "                    LEFT JOIN `товары` as `a1` ON (`a1`.`id` = `g`.`товар`)    \n" +
                "                   WHERE g.id = " + 1 + ";");
    }

    public Application getApp() {
        return app;
    }


}
