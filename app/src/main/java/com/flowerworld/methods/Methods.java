package com.flowerworld.methods;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flowerworld.connections.DataBaseHelper;

import java.util.ArrayList;

public class Methods {
    public static ArrayList<String> strParser(String str,String delimiter){
        String tempArray[]=str.split(delimiter);
        ArrayList<String> tempArrayList=new ArrayList<>();
        for (String s:tempArray){
            tempArrayList.add(s);
        }
        return tempArrayList;
    }
    public static boolean isAuth(DataBaseHelper helper){
        SQLiteDatabase db = helper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DataBaseHelper.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int bool = mcursor.getInt(0);
        if(bool>0)
            return true;
        else return false;
    }
}
