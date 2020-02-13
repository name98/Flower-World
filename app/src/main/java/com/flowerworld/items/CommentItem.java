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

    public CommentItem(String comment, String author, String date, int rate, boolean isMy, String id) {
        this.comment = comment;
        this.author = author;
        this.date = date;
        this.rate = rate;
        this.isMy = isMy;
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
