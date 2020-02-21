package com.flowerworld.connections;

import android.util.Log;

import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.HomeFragmentObjects;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragmentHelper {
    private HomeFragmentObjects objects;
    public HomeFragmentHelper(){
        ArrayList<Item> resultArrayList=new ArrayList<>();
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        ArrayList<ShopItem> shopItems = new ArrayList<>();

        JSONArray categ = new DataMethod().fromScript(Scripts.allData("Categories"));
        ArrayList<FlowerItem> flowerItems = new ArrayList<>();
        try {
            for(int i=0;i<categ.length();i++){
                JSONObject jsonObject = categ.getJSONObject(i);
                String s = jsonObject.getString("goods");
                ArrayList<String> ids = Methods.strParser(s," ");
                for(int j=0;j<ids.size();j++){
                    flowerItems.add(new FlowerItemHelper(ids.get(j)).getFlowerItem());
                }
                //resultArrayList.add(new Item(categ.getJSONObject(i).getString("categories"),new ArrayList<FlowerItem>(flowerItems)));
                flowerItems.clear();


            }
        }
        catch (Exception e){
            Log.d("HomeFragmentConstr1: ",e.toString());
        }
        try {
            JSONArray news  =new DataMethod().fromScript(Scripts.allData("news"));
            for (int i=0;i<news.length();i++){
                JSONObject jsonObject = news.getJSONObject(i);
                newsItems.add(new NewsItem(jsonObject.getString("Картинка"),
                        jsonObject.getString("Название"),
                        jsonObject.getString("ids")));
            }
        }
        catch (Exception e){
            Log.d("HomeFragmentConstr2: ",e.toString());
        }
        try {
            JSONArray shops = new DataMethod().fromScript(Scripts.ALL_MINI_SHOPS);
            for (int i=0;i<shops.length();i++){
                JSONObject jsonObject = shops.getJSONObject(i);
                shopItems.add(new ShopItem(jsonObject.getString("логотип"),
                        jsonObject.getString("название")));
            }
        }
        catch (Exception e){
            Log.d("HomeFragmentConstr3: ",e.toString());
        }
        objects = new HomeFragmentObjects(newsItems,resultArrayList,shopItems);
    }

    public HomeFragmentObjects getObjects() {
        return objects;
    }
}
