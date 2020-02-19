package com.flowerworld.ui;

import android.view.View;
import android.widget.Button;

import com.flowerworld.MainActivity;
import com.flowerworld.R;

public class PersonFragmentUI {
    private View view;

    public PersonFragmentUI(View view) {
        this.view = view;
        createUI();
    }
    private void createUI(){
        Button activeOrders =  view.findViewById(R.id.personFragmentActiveOrdersBT);
        activeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) view.getContext()).getApp().getRouter().addFragment("ordersFragment",false);
            }
        });
        Button compledOrders = view.findViewById(R.id.personFragmentCompledOrdersBt);
        compledOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) view.getContext()).getApp().getRouter().addFragment("ordersFragment",true);
            }
        });
    }
}
