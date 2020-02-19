package com.flowerworld.connections;

import com.flowerworld.items.CharterItem;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CharterFragmentHelper {

    private static FlowerItem PRODUCT;

    public static void bind(int id) {
        start(id);
    }

    private static void init(int id) {
        JSONArray array = new DataMethod().fromScript(Scripts.flowerItem(String.valueOf(id)));
        try {
            JSONObject flowerObject = array.getJSONObject(0);
            PRODUCT.setName(flowerObject.getString("название"));
            PRODUCT.setRating(flowerObject.getString("рейтинг"));
            PRODUCT.setPrice(flowerObject.getString("цена"));
            PRODUCT.setImageUrl(Methods.strParser(flowerObject.getString("картинки")," ").get(0));
            PRODUCT.setId(id);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private static void start(final int id) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                init(id);
            }
        });
        t.start();
    }

    public static FlowerItem getPRODUCT() {
        return PRODUCT;
    }


}
