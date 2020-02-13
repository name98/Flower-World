package com.flowerworld.items;

public class NewsItem {
    private String urlImage;
    private String titleNews;
    private String ids;

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;

    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getTitleNews() {
        return titleNews;
    }

    public String getIds() {
        return ids;
    }

    public NewsItem(String urlImage, String titleNews, String ids) {
        this.urlImage = urlImage;
        this.titleNews = titleNews;
        this.ids = ids;
    }
}
