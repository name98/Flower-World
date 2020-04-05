package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.OrderConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.OrderItem;
import com.flowerworld.items.adapters.OrderItemAdapter;


import java.util.ArrayList;
import java.util.Objects;

public class OrdersFragment extends Fragment implements FragmentSetDataInterface {
    private static final String IS_COMPLETED_KEY = "completed_key";
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Router.addProgressFragment(getContext());
        setHandler();
        assert getArguments() != null;
        boolean isCompleted = getArguments().getBoolean(IS_COMPLETED_KEY);
        setToolbar();
        setTitle(isCompleted);
        DataBaseHelper helper = new DataBaseHelper(getContext());
        String userId = helper.getId();
        OrderConnection connection = new OrderConnection();
        connection.setParent(this);
        connection.bind(isCompleted, userId);
    }

    public static OrdersFragment newInstance(boolean isCompleted) {
        Bundle args = new Bundle();
        args.putBoolean(IS_COMPLETED_KEY, isCompleted);
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
        if (orders.size() == 0) {
            RelativeLayout emptyRelativeLayout = view.findViewById(R.id.orders_fragment_empty_relative_layout);
            emptyRelativeLayout.setVisibility(View.VISIBLE);
        }
        else {
            RecyclerView recyclerView = view.findViewById(R.id.ordersFragmentRV);
            LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            OrderItemAdapter adapter = new OrderItemAdapter();
            adapter.setOrders(orders);
            recyclerView.setAdapter(adapter);
        }
        Router.removeFragmentByTag(getContext(), Router.PROGRESS_FRAGMENT_TAG);
    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        actionBar.setTitle("");
        assert actionBar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.ORDERS_FRAGMENT_TAG);
            }
        });
    }
    private void setTitle(boolean isCompleted) {
        TextView tittle = Objects.requireNonNull(getView()).findViewById(R.id.orders_fragment_title_text_view);
        if (!isCompleted)
            tittle.setText("Активные заказы");
        else
            tittle.setText("Завершенные заказы");
    }




}
