package com.flowerworld.items;

import com.flowerworld.models.MyDate;

public class CharterItem {
    private String address;
    private String receiver;
    private String idProduct;
    private String idUser;
    private String date;
    private String time;
    private String state ="waiting";
    private String icoUrl;

    public CharterItem(String address, String receiver, String idProduct, String idUser, String  date,String time, String icoUrl, String state) {
        this.address = address;
        this.receiver = receiver;
        this.idProduct = idProduct;
        this.idUser = idUser;
        this.date = date;
        this.time=time;
        this.icoUrl=icoUrl;
        this.state=state;
    }

    public void setState(String state) {
        this.state = state;
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
        return state;
    }
}
