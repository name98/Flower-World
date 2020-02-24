package com.flowerworld.items;

public class OrderItem {
    private String id;
    private String nameProduct;
    private String ico;
    private String date;
    private String state;

    public OrderItem(String id, String nameProduct, String ico, String date, String state) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.ico = ico;
        this.date = date;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getImage() {
        return ico;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }
}


