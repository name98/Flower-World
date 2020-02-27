package com.flowerworld.ui;

import android.view.View;
import android.widget.Button;

import com.flowerworld.R;
import com.flowerworld.fragments.Router;

public class PersonFragmentUI {
    private View view;

    public PersonFragmentUI(View view) {
        this.view = view;
        createUI();
    }
    private void createUI(){
        Button activeOrders =  view.findViewById(R.id.person_fragment_active_orders_button);
        activeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addOrderFragment(view.getContext(), false);
            }
        });
        Button completedOrders = view.findViewById(R.id.personFragmentCompledOrdersBt);
        completedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addOrderFragment(view.getContext(), true);
            }
        });
    }
}
