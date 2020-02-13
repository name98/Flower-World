package com.flowerworld.connections;

import com.flowerworld.models.AboutOrderModel;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AboutOrderHelper {
    private String idOrder;
    private AboutOrderModel order = new AboutOrderModel();
    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }
    public void createOrder(){
        JSONArray orderValues = new DataMethod().fromScript(Scripts.aboutOrderById(idOrder));
        try {
            JSONObject aboutOrder = orderValues.getJSONObject(0);
            order.setCost(aboutOrder.getString("цена"));
            order.setDate(aboutOrder.getString("число")+" в "+
                    aboutOrder.getString("время"));
            order.setProductId(aboutOrder.getString("товар"));
            order.setReceiver(aboutOrder.getString("Получатель"));
            order.setState(aboutOrder.getString("статус"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public AboutOrderModel getOrderModel() {
        return order;
    }
}
