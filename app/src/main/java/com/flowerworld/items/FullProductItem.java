package com.flowerworld.items;

import java.util.ArrayList;

public class FullProductItem {
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
    private String sumRate;
    private String numRaters;
    private String price;
    private boolean isFollow;

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
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

    public String getSumRate() {
        return sumRate;
    }

    public String getNumRaters() {
        return numRaters;
    }

    public String getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public void setSizeL(String sizeL) {
        this.sizeL = sizeL;
    }

    public void setSizeH(String sizeH) {
        this.sizeH = sizeH;
    }

    public void setItems(ArrayList<FlowerImagesItem> items) {
        this.items = items;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setNumberOfRates(ArrayList<Integer> numberOfRates) {
        this.numberOfRates = numberOfRates;
    }

    public void setSumRate(String sumRate) {
        this.sumRate = sumRate;
    }

    public void setNumRaters(String numRaters) {
        this.numRaters = numRaters;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}
