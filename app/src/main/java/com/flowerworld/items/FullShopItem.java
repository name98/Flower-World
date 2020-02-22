package com.flowerworld.items;

import java.util.ArrayList;

public class FullShopItem {
    private String id;
    private String logo;
    private String address;
    private String delivery;
    private String annotantion;
    private ArrayList<FlowerItem> items = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public void setAnnotantion(String annotantion) {
        this.annotantion = annotantion;
    }

    public void setItems(ArrayList<FlowerItem> items) {
        this.items = items;
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
}
