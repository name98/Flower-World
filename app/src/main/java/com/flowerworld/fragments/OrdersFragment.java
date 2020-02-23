package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.OrderConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.OrderItem;
import com.flowerworld.items.adapters.OrderItemAdapter;
import com.flowerworld.ui.OrdersFragmentUI;

import java.util.ArrayList;

public class OrdersFragment extends Fragment implements FragmentSetDataInterface {
    private static final String IS_COMPLETED_KEY = "completed_key";
    private Handler handler;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHandler();
        OrderConnection connection = new OrderConnection();
        connection.setParent(this);
        assert getArguments() != null;
        boolean isCompleted = getArguments().getBoolean(IS_COMPLETED_KEY);
        DataBaseHelper helper = new DataBaseHelper(getContext());
        String userId = helper.getKey();
        connection.bind(isCompleted,userId);
        //new OrdersFragmentUI(completed,view);
    }

    public static OrdersFragment newInstance(boolean isCompleted) {
        Bundle args = new Bundle();
        args.putBoolean(IS_COMPLETED_KEY,isCompleted);
        OrdersFragment fragment = new OrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<OrderItem> orders = (ArrayList<OrderItem>) msg.obj;
                setViews(orders);
            }
        };
    }

    private void setViews(ArrayList<OrderItem> orders) {
        View view = getView();
        assert view != null;
        RecyclerView recyclerView = view.findViewById(R.id.ordersFragmentRV);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        OrderItemAdapter adapter = new OrderItemAdapter(orders);
        recyclerView.setAdapter(adapter);
    }
}
