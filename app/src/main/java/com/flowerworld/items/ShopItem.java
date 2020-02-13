package com.flowerworld.items;

public class ShopItem {
    private String url;
    private String id;

    public ShopItem(String url, String name) {
        this.url = url;
        this.id = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getName(){
        return id;
    }
}
