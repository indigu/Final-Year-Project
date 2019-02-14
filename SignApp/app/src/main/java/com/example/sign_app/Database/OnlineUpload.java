package com.example.sign_app.Database;

public class OnlineUpload {
    private String mName;
    private String mImageUrl;

    public OnlineUpload(){
        //empty constructor
    }

    public OnlineUpload(String name, String imageUrl){
        if (name.trim().equals("")){
            name = "Name me!";
        }
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
