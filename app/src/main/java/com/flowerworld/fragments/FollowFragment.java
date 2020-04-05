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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.FolllowConnection;
import com.flowerworld.fragments.Router;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.adapters.OrderItemAdapter;
import com.flowerworld.items.adapters.ProductsGridAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class FollowFragment extends Fragment implements FragmentSetDataInterface {

    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Router.addProgressFragment(getContext());
        setHandler();
        getData();
        setTitle();
        setToolbar();
    }


    private String getUserId(){
        DataBaseHelper helper = new DataBaseHelper(getActivity());
        return helper.get(DataBaseHelper.KEY);
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
                ArrayList<FlowerItem> items = (ArrayList<FlowerItem>) msg.obj;
                setList(items);
            }
        };
    }

    private void setList(ArrayList <FlowerItem> items) {
        View parent = getView();
        assert parent != null;
        if (items.size() == 0) {
            RelativeLayout emptyRelativeLayout = parent.findViewById(R.id.orders_fragment_empty_relative_layout);
            emptyRelativeLayout.setVisibility(View.VISIBLE);
        }
        else {
            RecyclerView itemsRecycleView = parent.findViewById(R.id.ordersFragmentRV);
            GridLayoutManager gridLayout = (new GridLayoutManager(getContext(), 2));
            itemsRecycleView.setLayoutManager(gridLayout);
            ProductsGridAdapter adapter = new ProductsGridAdapter();
            adapter.setItems(items);
            adapter.setUserId(getUserId());
            adapter.setFollowPage(true);
            itemsRecycleView.setAdapter(adapter);
        }

        Router.removeFragmentByTag(getContext(), Router.PROGRESS_FRAGMENT_TAG);
    }

    private void setTitle() {
        View parent = getView();
        assert parent != null;
        TextView titleTextView = parent.findViewById(R.id.orders_fragment_title_text_view);
        titleTextView.setText("Отложенные");
    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.FOLLOW_PRODUCTS_TAG);
            }
        });
    }

    private void getData() {
        FolllowConnection connection = new FolllowConnection();
        connection.setParent(this);
        connection.bind(getUserId());
    }

    public static FollowFragment newInstance() {

        return new FollowFragment();
    }

}
