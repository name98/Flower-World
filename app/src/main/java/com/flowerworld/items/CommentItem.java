package com.flowerworld.items;

public class CommentItem {
    private String comment;
    private String author;
    private String date;
    private int rate;
    private boolean isMy;
    private String id;

    public String getId() {
        return id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setMy(boolean my) {
        isMy = my;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public int getRate() {
        return rate;
    }

    public boolean isMy() {
        return isMy;
    }
}
