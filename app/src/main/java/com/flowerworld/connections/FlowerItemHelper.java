package com.flowerworld.connections;

import android.util.Log;

import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONObject;

public class FlowerItemHelper {

    private FlowerItem flowerItem;
    public FlowerItemHelper(String id){
        try {
            String name;
            String imageUrl;
            String rating;
            String price;
            JSONObject js = new DataMethod().fromScript(Scripts.getProductByIdScript(id)).getJSONObject(0);
            name= js.getString("название");
            imageUrl = Methods.strParser(js.getString("картинки")," ").get(0);
            rating = js.getString("рейтинг");
            price  =js.getString("цена");
            Log.d("Name: ", name);
            this.flowerItem=new FlowerItem(name,imageUrl,rating,price,Integer.valueOf(id));
        }
        catch (Exception e){
            Log.d("FlowerItemHelper1: ", e.toString());
        }

    }

    public FlowerItem getFlowerItem() {
        return flowerItem;
    }
}
