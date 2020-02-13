package com.flowerworld.connections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "auth";
    public static final String TABLE_NAME= "user";
    private static final String LOGIN = "login";
    private static final String PASSWORD= "password";
    private static final String KEY = "id";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(" +
                KEY+" integer primary key,"+
                LOGIN + " text," +
                PASSWORD + " text"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public void add(String login, String pass, int key){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN, login);
        contentValues.put(PASSWORD, pass);
        contentValues.put(KEY,key);
        db.insert(TABLE_NAME,null,contentValues);
    }
    public String getLogin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("Select "+ LOGIN +" from "+TABLE_NAME+" where "+KEY+"=1;",null);
        String str = "";
        if (cr.moveToFirst()) {
            do {
                str+=cr.getString(0);
            } while (cr.moveToNext());
        }
        return str;
    }
    public String getKey(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("Select "+ KEY+" from "+TABLE_NAME+";",null);
        String str = "";
        if (cr.moveToFirst()) {
            do {
                str+=cr.getString(0);
            } while (cr.moveToNext());
        }
        return str;
    }



}
