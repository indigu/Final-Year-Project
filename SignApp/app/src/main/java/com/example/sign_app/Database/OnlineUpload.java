package com.example.sign_app.Database;

import com.google.firebase.database.Exclude;

public class OnlineUpload {
    private String name;
    private String imageUrl;
    private String key;

    public OnlineUpload() {
        //empty constructor
    }

    public OnlineUpload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            this.name = "Name me!";
        }
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

}
