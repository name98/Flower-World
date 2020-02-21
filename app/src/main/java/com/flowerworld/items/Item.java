package com.flowerworld.items;


import java.util.ArrayList;

public class Item {
    private String flowerItemTitle;
    private ArrayList<String> products;


    public void setFlowerItemTitle(String flowerItemTitle) {
        this.flowerItemTitle = flowerItemTitle;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public String getFlowerItemTitle() {
        return flowerItemTitle;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public Item(String flowerItemTitle, ArrayList<String> products) {
        this.flowerItemTitle = flowerItemTitle;
        this.products = products;
    }
}
