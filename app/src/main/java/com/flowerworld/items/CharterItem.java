package com.flowerworld.items;

public class CharterItem {
    private String address;
    private String receiver;
    private String idProduct;
    private String idUser;
    private String date;
    private String time;


    public void setAddress(String address) {
        this.address = address;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getState() {
        return "ожидание";
    }
}
