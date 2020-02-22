package com.flowerworld.ui;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.connections.ShopFragmentHelper;
import com.flowerworld.items.adapters.ProductsGridAdapter;

public class ShopFragmentUI {
    private String id;
    View view;
    Handler handler;
    public ShopFragmentUI(View view, String id){
        this.view=view;
        this.id = id;
        intit();
        start();
    }
    private void intit(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                ShopFragmentHelper helper = (ShopFragmentHelper) msg.obj;
                createUI(helper);
            }
        };
    }
    private void start(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Message msg = Message.obtain();
                    msg.obj = new ShopFragmentHelper(id);
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                }
                catch (Exception e){
                    Log.d("ShFUI: ", e.toString());
                }
            }
        });
        t.start();
    }
    private void createUI(ShopFragmentHelper helper){
        TextView name = view.findViewById(R.id.shopPageNameShop);
        TextView address = view.findViewById(R.id.shopPageAddress);
        TextView annotat = view.findViewById(R.id.shopPageAnnotation);
        TextView uslD = view.findViewById(R.id.shopPageUslDost);
        SimpleDraweeView shopPhoto =view.findViewById(R.id.shopItemImg);
        RecyclerView recyclerViewGrid = view.findViewById(R.id.gridItemsRecycleView);

        name.setText(helper.getId());
        address.setText(helper.getAddress());
        annotat.setText(helper.getAnnotantion());
        uslD.setText(helper.getDelivery());
        shopPhoto.setImageURI(Uri.parse(helper.getLogo()));
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        ProductsGridAdapter adapter = new ProductsGridAdapter();
        adapter.setItems(helper.getItems());
        recyclerViewGrid.setAdapter(adapter);

    }
}
