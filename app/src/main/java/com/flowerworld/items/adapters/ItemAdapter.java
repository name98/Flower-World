package com.flowerworld.items.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.flowerworld.R;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Item> itemArrayList;
    private ArrayList<NewsItem> newsItemArrayList;
    private ArrayList<ShopItem> shopItems;
    private RecyclerView.RecycledViewPool viewPool= new RecyclerView.RecycledViewPool();

    private int posForItemArrayList = 0;


    public ItemAdapter(ArrayList<Item> itemArrayList, ArrayList<NewsItem> newsItemArrayList, ArrayList<ShopItem> shopItems) {
        this.itemArrayList = itemArrayList;
        this.newsItemArrayList = newsItemArrayList;
        this.shopItems = shopItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case 1:
                View view2= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_news,parent,false);
                return new ItemHolder2(view2);
            case 2:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops_recycle_view,parent,false);
                return new ItemHolder3(view3);
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_flowers_layout,parent,false);

                return new ItemHolder(view);



        }




    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 1:
                ItemHolder2 itemHolder2=(ItemHolder2) holder;

                NewsItemAdapter adapter=new NewsItemAdapter(this.newsItemArrayList,itemHolder2.viewPager2);
                itemHolder2.viewPager2.setAdapter(adapter);
                break;
            case 2:
                ItemHolder3 itemHolder3 = (ItemHolder3) holder;
                LinearLayoutManager layoutManager = new LinearLayoutManager(itemHolder3.recyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL,false);
                layoutManager.setInitialPrefetchItemCount(shopItems.size());
                ShopItemAdapter adapter1 = new ShopItemAdapter(this.shopItems);
                itemHolder3.recyclerView.setLayoutManager(layoutManager);
                itemHolder3.recyclerView.setAdapter(adapter1);
                itemHolder3.recyclerView.setRecycledViewPool(viewPool);
                break;
            default:
                ItemHolder itemHolder=(ItemHolder) holder;
                Item item = itemArrayList.get(posForItemArrayList);
                itemHolder.itemTags.setText(item.getFlowerItemTitle());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemHolder.itemTags.getContext(), LinearLayoutManager.HORIZONTAL,false);
                linearLayoutManager.setInitialPrefetchItemCount(itemArrayList.size());
                FlowerItemAdapter flowerItemAdapter = new FlowerItemAdapter(item.getFlowerItemArrayList());
                itemHolder.rvFlowerItem.setLayoutManager(linearLayoutManager);
                itemHolder.rvFlowerItem.setAdapter(flowerItemAdapter);
                itemHolder.rvFlowerItem.setRecycledViewPool(viewPool);
                posForItemArrayList++;
                break;



        }

    }




    @Override
    public int getItemCount() {
        return itemArrayList.size()+2;
    }
    @Override
    public int getItemViewType(int position){
        if(position  == 0)
            return 1;
        if(position == 3)
            return 2;
        else return 3;
    }
    class ItemHolder extends RecyclerView.ViewHolder{
        TextView itemTags;
        RecyclerView rvFlowerItem;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            itemTags = itemView.findViewById(R.id.tagForMiniFlowers);
            rvFlowerItem = itemView.findViewById(R.id.miniFlowersRecycleView);


        }

    }
    class ItemHolder2 extends RecyclerView.ViewHolder{
        ViewPager2 viewPager2;

        ItemHolder2(@NonNull View itemView) {
            super(itemView);
            viewPager2 = itemView.findViewById(R.id.newsViewPager2);
        }
    }
    class ItemHolder3 extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView shops;
        ItemHolder3(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.shopsRecV);
            shops = itemView.findViewById(R.id.shopsText);
        }



    }

}
