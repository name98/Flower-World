package com.flowerworld.items;


import java.util.ArrayList;

public class Item {
    private String flowerItemTitle;
    private ArrayList<FlowerItem> flowerItemArrayList;

    public void setFlowerItemTitle(String flowerItemTitle) {
        this.flowerItemTitle = flowerItemTitle;
    }

    public void setFlowerItemArrayList(ArrayList<FlowerItem> flowerItemArrayList) {
        this.flowerItemArrayList = flowerItemArrayList;
    }

    public String getFlowerItemTitle() {
        return flowerItemTitle;
    }

    public ArrayList<FlowerItem> getFlowerItemArrayList() {
        return flowerItemArrayList;
    }

    public Item(String flowerItemTitle, ArrayList<FlowerItem> flowerItemArrayList) {
        this.flowerItemTitle = flowerItemTitle;
        this.flowerItemArrayList = flowerItemArrayList;
    }
}
