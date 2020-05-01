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
