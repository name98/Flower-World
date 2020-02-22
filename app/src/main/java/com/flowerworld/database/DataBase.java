package com.flowerworld.database;

import android.util.Log;

import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;
import com.flowerworld.links.UrlLinks;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DataBase {
    static JSONArray getJSONArrayByScript(String script){
        try {
            String data = URLEncoder.encode("script", "UTF-8") + "=" + URLEncoder.encode(script, "UTF-8");
            URL url = new URL(UrlLinks.FROM_SCRIPT);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            line = reader.readLine();
            sb.append(line);
            return new JSONArray(sb.toString());
        } catch (Exception e) {

            return null;
        }
    }

    public static FlowerItem getProductById(String id) {
        JSONArray array = getJSONArrayByScript(Scripts.getProductByIdScript(id));
        FlowerItem product = new FlowerItem();
        try {
            assert array != null;
            JSONObject object = array.getJSONObject(0);
            product.setName(object.getString("название"));
            product.setRating(object.getString("рейтинг"));
            product.setImageUrl(Methods.strParser(object.getString("картинки")," ").get(0));
            product.setPrice(object.getString("цена"));
            product.setId(Integer.valueOf(id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static ArrayList<NewsItem> getNewsArray() {
        JSONArray array = getJSONArrayByScript(Scripts.allData("news"));
        ArrayList<NewsItem> news = new ArrayList<>();
        try {
            assert array != null;
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                news.add(new NewsItem(jsonObject.getString("Картинка"),
                        jsonObject.getString("Название"),
                        jsonObject.getString("ids")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public static ArrayList<Item> getCategoriesArray() {
        JSONArray array = getJSONArrayByScript(Scripts.allData("Categories"));
        ArrayList<Item> categories = new ArrayList<>();
        try {
            assert array != null;
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String s = jsonObject.getString("goods");
                ArrayList<String> ids = Methods.strParser(s, " ");
                categories.add(new Item(array.getJSONObject(i).getString("categories"), ids));
            }
        } catch (Exception e) {
            Log.d("HomeFragmentConstr1: ", e.toString());
        }
        return categories;
    }

    public static ArrayList<ShopItem> getShopsArray() {
        JSONArray array = getJSONArrayByScript(Scripts.ALL_MINI_SHOPS);
        ArrayList<ShopItem> shops = new ArrayList<>();
        try {
            assert array != null;
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                shops.add(new ShopItem(jsonObject.getString("логотип"),
                        jsonObject.getString("название")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return shops;
    }
}
