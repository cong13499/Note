package com.example.note;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE = "DataItem.db";
    private static final int VESION = 1;

    private static final String TABLE_NAME = "ITEMS";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TITLE";
    private static final String COL_3 = "CONTENT";

    private static final String CREATE_TABLE_ENTRY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COL_2 + " TEXT, "
            + COL_3 + " TEXT) ";
    private static final String DELETE_TABLE_ENTRY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SELECT_ALL_DATA = "SELECT * FROM " + TABLE_NAME;

    public Database(@Nullable Context context) {
        super(context, DATABASE, null, VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_ENTRY);
        db.execSQL(CREATE_TABLE_ENTRY);
    }

    public void insertData(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, note.getTitle());
        contentValues.put(COL_3, note.getContent());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<Note> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_DATA, null);

        cursor.moveToFirst();
        if (!cursor.moveToFirst()) return null;

        ArrayList<Note> list = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(COL_1));
            String title = cursor.getString(cursor.getColumnIndex(COL_2));
            String content = cursor.getString(cursor.getColumnIndex(COL_3));

            list.add(new Note(id, title, content));
            cursor.moveToNext();
        }

        return list;
    }

    public int deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = " + id, null);
    }

    public boolean updateData(int id, Note item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, item.getTitle());
        contentValues.put(COL_3, item.getContent());

        Log.d("THIS_ID", id + " " + item.getTitle() + "\n" + item.getContent());

        db.update(TABLE_NAME, contentValues, " ID = " + id, null);

        return true;
    }
}