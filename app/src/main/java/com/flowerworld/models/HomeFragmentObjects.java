package com.flowerworld.models;

import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;

import java.util.ArrayList;

public class HomeFragmentObjects {
    private ArrayList<NewsItem> newsItems = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<ShopItem> shopItems = new ArrayList<>();

    public HomeFragmentObjects(ArrayList<NewsItem> newsItems, ArrayList<Item> items, ArrayList<ShopItem> shopItems) {
        this.newsItems = newsItems;
        this.items = items;
        this.shopItems = shopItems;
    }

    public ArrayList<NewsItem> getNewsItems() {
        return newsItems;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<ShopItem> getShopItems() {
        return shopItems;
    }
}
