package com.flowerworld.connections;

import android.util.Log;

import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;

public class LoginFragmentHelper {
    private String pass;
    private String email;
    private boolean auth = false;
    private int key=0;

    public LoginFragmentHelper(String pass, String email) {
        this.pass = pass;
        this.email = email;
        start();
    }
    private void start(){
        try{
            JSONArray array = new DataMethod().fromScript(Scripts.auth(email,pass));
            System.out.println(email+pass);
            if (array.length()==0)
                auth = false;
            else {
                auth = true;
                key = array.getJSONObject(0).getInt("ID");
            }

            System.out.println("auth is " + auth);

        }
        catch (Exception e){
            Log.d("LFH: ",e.toString());
        }
    }
    public final boolean isAuth(){
        return auth;
    }

    public int getKey() {
        return key;
    }
}
