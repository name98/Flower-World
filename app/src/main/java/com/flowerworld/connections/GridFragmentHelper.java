package com.flowerworld.connections;


import android.util.Log;



import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GridFragmentHelper {

    private static ArrayList<FlowerItem> ITEMS = new ArrayList<>();

    public static void bind(String ids){
        ArrayList<String> itemIds = Methods.strParser(ids," ");
        start(itemIds);
    }

    private static void start(final ArrayList<String> itemIds) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initFlowerItemsArray(itemIds);
                } catch (Exception e) {
                    Log.d("GFUI2: ", e.toString());
                }
            }
        });
        thread.start();
    }

    private static void initFlowerItemsArray(ArrayList<String> itemsId) {

        for(int i = 0; i<itemsId.size(); i++){
            ITEMS.add(setProduct(itemsId.get(i)));
        }
    }

    private static FlowerItem setProduct(String id){
        JSONArray itemArray = new DataMethod().fromScript(Scripts.flowerItem(id));
        try {
            JSONObject itemObject = itemArray.getJSONObject(0);
            FlowerItem item = new FlowerItem();
            item.setId(Integer.valueOf(id));
            item.setImageUrl(Methods.strParser(
                    itemObject.getString("картинки")," "
            ).get(0));
            item.setPrice(itemObject.getString("цена"));
            item.setRating(itemObject.getString("рейтинг"));
            item.setName(itemObject.getString("название"));
            return item;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<FlowerItem> getITEMS() {
        return ITEMS;
    }
}
