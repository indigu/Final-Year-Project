package com.example.sign_app.Models;

import android.database.Cursor;

public class Memory {
    private String title;


    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_IMAGE = 2;

    public Memory(Cursor cursor) {
        this.title = cursor.getString(COL_TITLE);

    }

    public Memory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}