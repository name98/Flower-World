package com.flowerworld.methods;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.DataMethod;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Methods {
    public static ArrayList<String> strParser(String str, String delimiter) {
        String tempArray[] = str.split(delimiter);
        ArrayList<String> tempArrayList = new ArrayList<>();
        for (String s : tempArray) {
            tempArrayList.add(s);
        }
        return tempArrayList;
    }

    public static boolean isAuth(DataBaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DataBaseHelper.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int bool = mcursor.getInt(0);
        if (bool > 0)
            return true;
        else return false;
    }

    public static String formatRuble(String str) {
        if (str.length() <= 3)
            return str+"\u20BD";
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            System.out.println(chars.length);
            builder.append(chars[i]);
            if (i != (chars.length - 1) && i % 3 == 1) {
                builder.append(",");
            }
        }
        String temp = builder.toString();
        char[] chars1 = temp.toCharArray();
        StringBuilder builder1 = new StringBuilder();
        for (int i = chars1.length - 1; i >= 0; i--){
            builder1.append(chars1[i]);
        }
        return builder1.toString()+"\u20BD";
    }

    static FlowerItem getFlowerItemfromId(String id) {

        return null;
    }

}
