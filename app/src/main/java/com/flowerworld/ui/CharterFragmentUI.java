package com.flowerworld.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.CharterFragmentHelper;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.FlowerFragmentHalper;
import com.flowerworld.connections.LoginFragmentHelper;
import com.flowerworld.items.CharterItem;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.MyDate;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CharterFragmentUI {
    private View view;

    private Handler handler;
    private FlowerItem item;
    public CharterFragmentUI(View view, FlowerItem item)  {
        this.view = view;
        this.item = item;
        start();
    }
    private void start(){
        final TextView date = view.findViewById(R.id.orderDate);
        final TextView time = view.findViewById(R.id.orderTime);
        SimpleDraweeView simpleDraweeView = view.findViewById(R.id.flowerIconImage);
        TextView textView = view.findViewById(R.id.flowerIconName);
        simpleDraweeView.setImageURI(Uri.parse(item.getImageUrl()));
        textView.setText(item.getName());
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE,1);
        now = cal.getTime();

        final Date finalNow = now;


        //date.setText(finalNow.getYear()+"/"+ finalNow.getMonth()+"/"+ finalNow.getDay());
        MyDate date1 = new MyDate(finalNow);
        date.setText(date1.getDate());
        //date.setText(finalNow.toString());
        time.setText(date1.getTime());
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, finalNow.getYear()+1900, finalNow.getMonth(), finalNow.getDay());
                dDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay+":"+minute);
                    }
                },finalNow.getHours(),finalNow.getMinutes(),true);
                tDialog.show();

            }

        });
        init();

    }
    private void init(){
        Button procces = view.findViewById(R.id.orderGo);
        procces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText receiver = view.findViewById(R.id.orderReceiver);
                EditText address = view.findViewById(R.id.orderAddress);
                TextView date =  view.findViewById(R.id.orderDate);
                TextView time = view.findViewById(R.id.orderTime);
                DataBaseHelper helper = ((MainActivity) view.getContext()).getApp().getDbHelper();
                String userID = helper.getKey();


                CharterItem itemCh = new CharterItem(address.getText().toString(),
                        receiver.getText().toString(),
                        String.valueOf(item.getId()), userID,
                        date.getText().toString(),
                        time.getText().toString(),item.getImageUrl(),"ожидание");
                initHandler();
                startHandler(itemCh);



            }
        });
    }
    @SuppressLint("HandlerLeak")
    private void initHandler(){
        handler= new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Ваш заказ принят!")
                        .setMessage("Чтобы следить за статусом заказа перейдите в 'Активные заказы'")

                        .setCancelable(false)
                        .setNegativeButton("Ок",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ((MainActivity) view.getContext())
                                                .getApp().getRouter()
                                                .remove("charterFragment");
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };
    }
    private void startHandler(final CharterItem item){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //new CharterFragmentHelper(item);
                    Message msg = Message.obtain();
                    msg.obj = true;
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                }
                catch (Exception e){
                    Log.d("ChFUI: ", e.toString());
                }

            }
        });
        t.start();
    }


}
