package com.flowerworld.connections;

import com.flowerworld.MainActivity;
import com.flowerworld.items.OrderItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersFragmentHelper {
    private ArrayList<OrderItem> items=new ArrayList<>();

    public OrdersFragmentHelper(String id, boolean compled) throws JSONException {
        JSONArray js;
        if (compled)
            js = new DataMethod().fromScript(Scripts.ordersByIdC(id));
        else
            js = new DataMethod().fromScript(Scripts.ordersById(id));
        ArrayList<String> temp = new ArrayList<>();
        for (int i =0;i<js.length();i++){
            String s = js.getJSONObject(i).getString("картинки");
            JSONObject object = js.getJSONObject(i);
            items.add(new OrderItem(object.getString("id"),
                            object.getString("название"),
                            Methods.strParser(s," ").get(0),
                            object.getString("число"),
                            object.getString("статус")));

        }
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }
}
