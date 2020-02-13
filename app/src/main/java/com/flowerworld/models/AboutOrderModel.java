package com.flowerworld.models;

public class AboutOrderModel {
    private String receiver;
    private String date;
    private String state;
    private String cost;
    private String productId;

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
        this.cost = cost + " руб.";
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
