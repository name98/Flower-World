package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.connections.ShopConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.FullShopItem;

import com.flowerworld.items.adapters.ProductsGridAdapter;


import java.util.ArrayList;
import java.util.Objects;

public class ShopFragment extends Fragment implements FragmentSetDataInterface {

    private static final String SHOP_NAME_KEY = "shop_key";
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProgreeBar(true);
        setHandler();
        ShopConnection connection = new ShopConnection();
        connection.setParent(this);
        assert getArguments() != null;
        String shopName = getArguments().getString(SHOP_NAME_KEY);
        connection.bind(shopName);
    }

    private void setViews(FullShopItem shop) {
        View view = getView();
        assert view != null;
        TextView name = view.findViewById(R.id.shopPageNameShop);
        TextView address = view.findViewById(R.id.shopPageAddress);
        TextView annotat = view.findViewById(R.id.shopPageAnnotation);
        TextView uslD = view.findViewById(R.id.shopPageUslDost);
        SimpleDraweeView shopPhoto =view.findViewById(R.id.shopItemImg);
        name.setText(shop.getId());
        address.setText(shop.getAddress());
        annotat.setText(shop.getAnnotantion());
        uslD.setText(shop.getDelivery());
        System.out.println(shop.getLogo());
        shopPhoto.setImageURI(Uri.parse(shop.getLogo()));
    }

    private void setGridProducts(ArrayList<FlowerItem> products) {
        RecyclerView recyclerViewGrid = Objects.requireNonNull(getView()).findViewById(R.id.gridItemsRecycleView);
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ProductsGridAdapter adapter = new ProductsGridAdapter();
        adapter.setItems(products);
        recyclerViewGrid.setAdapter(adapter);
        setProgreeBar(false);
    }

    public static ShopFragment newInstance(String shopName) {
        Bundle args = new Bundle();
        args.putString(SHOP_NAME_KEY,shopName);
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    private void bind(FullShopItem shop) {
        setViews(shop);
        setGridProducts(shop.getItems());
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                FullShopItem shop = (FullShopItem) msg.obj;
                bind(shop);
            }
        };
    }

    private void setProgreeBar(boolean isProgress) {
        if(isProgress)
            Router.addProgressFragment(getContext());
        else
            Router.removeProgreesFragment(getContext());
    }


}