package com.flowerworld.items;

public class AboutOrderItem {
    private FlowerItem product;
    private String receiver;
    private String date;
    private String state;
    private String cost;
    private String productId;

    public FlowerItem getProduct() {
        return product;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getCost() {
        return cost;
    }

    public String getProductId() {
        return productId;
    }

    public void setProduct(FlowerItem product) {
        this.product = product;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
