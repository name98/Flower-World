package com.flowerworld.ui;



import com.flowerworld.connections.AboutOrderHelper;
import com.flowerworld.connections.FlowerItemHelper;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.models.AboutOrderModel;


public class AboutOrderFragmentUI {
    private String idOrder;
    private AboutOrderModel model;
    private FlowerItem item;

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;

    }


    public void initializeVariables() {
        AboutOrderHelper helper = new AboutOrderHelper();
        helper.setIdOrder(idOrder);
        helper.createOrder();
        model = helper.getOrderModel();
        item = new FlowerItemHelper(model.getProductId()).getFlowerItem();
    }

    public AboutOrderModel getModel() {
        return model;
    }

    public FlowerItem getItem() {
        return item;
    }
}
