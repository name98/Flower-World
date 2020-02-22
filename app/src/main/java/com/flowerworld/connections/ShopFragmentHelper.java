package com.flowerworld.connections;



import android.util.Log;

import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopFragmentHelper {

    private String id;
    private String logo;
    private String address;
    private String delivery;
    private String annotantion;
    private ArrayList<FlowerItem> items = new ArrayList<>();
    public ShopFragmentHelper (String id){
        this.id=id;
        start();
    }

    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getAddress() {
        return address;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getAnnotantion() {
        return annotantion;
    }

    public ArrayList<FlowerItem> getItems() {
        return items;
    }

    private void start(){
        try {
            JSONObject jsonObject = new DataMethod().fromScript(Scripts.fullDataShop(id)).getJSONObject(0);
            logo = jsonObject.getString("логотип");

            address = jsonObject.getString("адрес") + "\n" +
                    jsonObject.getString("телефон") + "\n" +
                    jsonObject.getString("почта");
            delivery = jsonObject.getString("тип") + "\n" +
                    jsonObject.getString("внутри_МКАДа") + "\n" +
                    jsonObject.getString("за_МКАДом");
            annotantion = jsonObject.getString("описание");
            JSONArray array = new DataMethod().fromScript(Scripts.flowerItemByShop(id));
            for(int i =0; i<array.length();i++){
                JSONObject temp = array.getJSONObject(i);
                String name = temp.getString("название");

                String rate = temp.getString("рейтинг");
                String images = Methods.strParser(temp.getString("картинки")," ").get(0);
                String price = temp.getString("цена");

                items.add(new FlowerItem(name,images, rate, price, temp.getInt("ID")));
            }
        }
        catch (Exception e){
            Log.d("ShFH: ", e.toString());
        }

    }
}
