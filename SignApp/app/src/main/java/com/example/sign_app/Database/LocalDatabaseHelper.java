package com.example.sign_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sign_app.Models.Memory;

import static android.provider.BaseColumns._ID;
import static com.example.sign_app.Database.LocalDatabaseContract.MemoryEntry.TABLE_NAME;


public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "User Database";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    LocalDatabaseContract.MemoryEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    LocalDatabaseContract.MemoryEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    LocalDatabaseContract.MemoryEntry.COLUMN_IMAGE + TEXT_TYPE + " )";

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //This method has been intentionally left empty. There is only one version of the database.
    }

    public Cursor readAllMemories() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public boolean addMemory(Memory memory) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LocalDatabaseContract.MemoryEntry._ID, memory.getID());
        values.put(LocalDatabaseContract.MemoryEntry.COLUMN_TITLE, memory.getTitle());
        values.put(LocalDatabaseContract.MemoryEntry.COLUMN_IMAGE, memory.getImageAsString());

        return db.insert(TABLE_NAME, null, values) != -1;
    }

    public Cursor getItemID(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + _ID + " FROM " + TABLE_NAME + " WHERE " + _ID + " = '" + title + "'";
        ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}