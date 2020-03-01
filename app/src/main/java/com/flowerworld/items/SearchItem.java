package com.flowerworld.items;

public class SearchItem {
    private String text;
    private String type;
    private int id;

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
