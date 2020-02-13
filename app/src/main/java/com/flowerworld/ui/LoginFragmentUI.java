package com.flowerworld.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.flowerworld.MainActivity;
import com.flowerworld.R;

import com.flowerworld.connections.LoginFragmentHelper;


public class LoginFragmentUI {
    private View view;
    private Handler handler;
    private EditText email;
    private EditText pass;
    private int key;
    public LoginFragmentUI(View view){
        this.view = view;
        init();
        createUI();

    }

    private void createUI(){

        final Button enter =  view.findViewById(R.id.loginFragmentEnterBt);
        enter.setEnabled(false);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!pass.getText().toString().equals("") && !email.getText().toString().equals(""))
                    enter.setEnabled(true);
                else
                    enter.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        email.addTextChangedListener(watcher);
        pass.addTextChangedListener(watcher);
    }
    private void start(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = Message.obtain();
                    LoginFragmentHelper helper = new LoginFragmentHelper(pass.getText().toString(),email.getText().toString());
                    msg.obj = helper.isAuth();
                    key=helper.getKey();
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                }
                catch (Exception e){
                    Log.d("LFUI: ", e.toString());
                }

            }
        });
        t.start();
    }
    @SuppressLint("HandlerLeak")
    private void init(){
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                boolean auth = (boolean) msg.obj;
                if(auth){
                    quit();
                }
            }
        };
    }
    private void quit(){
        ((MainActivity) view.getContext())
                .getApp()
                .getDbHelper()
                .add(email.getText().toString(),
                        pass.getText().toString(),key);
        ((MainActivity) view.getContext())
                .getApp()
                .getRouter()
                .remove("loginFragment");
        ((MainActivity) view.getContext())
                .getApp()
                .getRouter()
                .addFrament("mainFragment");


    }
}
