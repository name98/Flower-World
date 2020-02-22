package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.fragments.Router;
import com.flowerworld.items.ShopItem;

import java.util.ArrayList;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemHolder> {
    private ArrayList<ShopItem> shopItems = new ArrayList<>();

    void setShopItems(ArrayList<ShopItem> shopItems) {
        this.shopItems = shopItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item,parent,false);
        return new ShopItemAdapter.ShopItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {
        holder.bind(shopItems.get(position));
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    class ShopItemHolder extends RecyclerView.ViewHolder{

        ShopItemHolder(@NonNull View itemView) {
            super(itemView);
        }
        void bind(ShopItem shopItem){
            setListener(shopItem.getName());
            setLogo(shopItem.getUrl());
        }

        private void setListener(final String name) {
            SimpleDraweeView shoplogo = itemView.findViewById(R.id.shopItemImg);
            shoplogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.addShopFragment(itemView.getContext(),name);
                }
            });
        }
        private void setLogo(String urlLogo) {
            SimpleDraweeView shoplogo = itemView.findViewById(R.id.shopItemImg);
            shoplogo.setImageURI(Uri.parse(urlLogo));
        }


    }
}
