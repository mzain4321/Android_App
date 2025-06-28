package com.zain.myapp1.models;

public class ItemModel {
    int imageRes;
    String title;

    public ItemModel(int imageRes, String title) {
        this.imageRes = imageRes;
        this.title = title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getTitle() {
        return title;
    }
}
