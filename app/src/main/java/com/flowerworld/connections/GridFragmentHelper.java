package com.flowerworld.connections;

import android.util.Log;

import com.flowerworld.fragments.GridFragment;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GridFragmentHelper {

    private String ids;
    private ArrayList<FlowerItem> items = new ArrayList<>();
    public GridFragmentHelper(String ids){
        this.ids=ids;
        start();
    }
    private void start(){
        ArrayList<String> strings = Methods.strParser(ids, " ");
        for (int i=0;i<strings.size();i++){
            try {
                items.add(new FlowerItemHelper(strings.get(i)).getFlowerItem());
            }
            catch (Exception e){
                Log.d("GFH: ", e.toString());
            }
        }
    }

    public ArrayList<FlowerItem> getItems() {
        return items;
    }
}
