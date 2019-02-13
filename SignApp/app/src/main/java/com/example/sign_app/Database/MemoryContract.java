package com.example.sign_app.Database;

import android.provider.BaseColumns;

public class MemoryContract {

    private MemoryContract() {
    }

    public static final class MemoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "User_Database";
        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
    }
}