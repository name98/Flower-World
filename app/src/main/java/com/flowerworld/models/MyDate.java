package com.flowerworld.models;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDate {
    private Date date;
    private ArrayList<String> format=new ArrayList<>();
    public MyDate(Date date) {
        this.date = date;
        doFormat();


    }
    public MyDate(){
        date =new Date();
    }


    private void doFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String sf = sdf.format(date);
        String temp[] = sf.split("-");
        String temp2 = temp[temp.length-1];

        String temp3[] = temp2.split(" ");
        String temp4 = temp3[temp3.length-1];
        String temp5[] = temp4.split(":");
        for (int i=0;i<2;i++){
            format.add(temp[i]);
        }
        format.add(temp[temp.length-1].split(" ")[0]);
        for (int i=0;i<2;i++){
            format.add(temp5[i]);
        }

    }

    public String getDay(){
        return format.get(2);
    }
    public String getYear(){
        return format.get(0);
    }
    public String getMounth(){
        return format.get(1);
    }
    public String getHour(){
        return format.get(3);
    }
    public String getMinute(){
        return format.get(4);
    }

    public String getDate(){
        return getDay()+"/"+getMounth()+"/"+getYear();
    }
    public String getTime(){
        return getHour()+":"+getMinute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getToDayForDB(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
