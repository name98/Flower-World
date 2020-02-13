package com.flowerworld.items.adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.items.ShopItem;

import java.util.ArrayList;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemHolder> {
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    public ShopItemAdapter(ArrayList<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }

    @NonNull
    @Override
    public ShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item,parent,false);
        return new ShopItemAdapter.ShopItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {
        holder.setImage(shopItems.get(position));
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    class ShopItemHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView shopPhoto;
        String name;
        public ShopItemHolder(@NonNull View itemView) {
            super(itemView);
            shopPhoto = itemView.findViewById(R.id.shopItemImg);
            shopPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) shopPhoto.getContext())
                            .getApp()
                            .getRouter()
                            .addFrament("shopPage",name);

                    Log.d("shop ", name);
                }
            });
        }
        public void setImage(ShopItem url){
            String uri = url.getUrl();
            name= url.getName();
            shopPhoto.setImageURI(Uri.parse(uri));
        }
    }
}
