package com.flowerworld.items;

public class FlowerItem {
    private String name;
    private String imageUrl;
    private String rating;
    private String price;
    private boolean isFollow;
    private int id;

    public FlowerItem() {
    }

    public FlowerItem(String name, String imageUrl, String rating, String price, int id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.price = price;
        this.id = id;
    }

    public FlowerItem(String name, String imageUrl, String rating, String price, int id, boolean isFollow) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.price = price;
        this.id = id;
        this.isFollow = isFollow;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FlowerItem(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public boolean isFollow() {
        return isFollow;
    }
}
