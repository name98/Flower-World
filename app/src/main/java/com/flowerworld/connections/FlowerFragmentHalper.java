package com.flowerworld.connections;

import android.util.Log;

import com.flowerworld.items.FlowerImagesItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONObject;

import java.util.ArrayList;

public class FlowerFragmentHalper {
    private String name;

    private String id;
    private String shopName;
    private String shopLogo;
    private String sizeL;
    private String sizeH;
    private ArrayList<FlowerImagesItem> items = new ArrayList<>();
    private String compound;
    private String annotation;
    private ArrayList<Integer> numberOfRates = new ArrayList<>();
    private Double sumRate;
    private String numRaters;
    private String prices;


    public FlowerFragmentHalper(String id){
        try {
            JSONObject flower = new DataMethod().fromScript(Scripts.fullData(id)).getJSONObject(0);
            name = flower.getString("название");

            shopLogo = flower.getString("логотип");
            shopName = flower.getString("магазин");
            sizeL = flower.getString("длина");
            sizeH = flower.getString("ширина");
            compound = flower.getString("состав");
            annotation = flower.getString("описание");
            ArrayList<String> temp = Methods.strParser(flower.getString("картинки")," ");
            for (int i=0;i<temp.size();i++){
                items.add(new FlowerImagesItem(temp.get(i)));
            }
            numberOfRates.add(flower.getInt("один"));
            numberOfRates.add(flower.getInt("два"));
            numberOfRates.add(flower.getInt("три"));
            numberOfRates.add(flower.getInt("четыре"));
            numberOfRates.add(flower.getInt("пять"));
            sumRate = flower.getDouble("рейтинг");
            this.id=id;
            int max = 0;
            for (int i=0; i<5;i++){
                max +=numberOfRates.get(i);
            }
            numRaters = String.valueOf(max);
            prices = flower.getString("цена");

        }
        catch (Exception e){
            Log.d("FlowerFragmentHalper: ", e.toString());
        }


    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public String getSizeL() {
        return sizeL;
    }

    public String getSizeH() {
        return sizeH;
    }

    public ArrayList<FlowerImagesItem> getItems() {
        return items;
    }

    public String getCompound() {
        return compound;
    }

    public String getAnnotation() {
        return annotation;
    }

    public ArrayList<Integer> getNumberOfRates() {
        return numberOfRates;
    }

    public Double getSumRate() {
        return sumRate;
    }

    public String getNumRaters() {
        return numRaters;
    }

    public String getPrices() {
        return prices;
    }
}
