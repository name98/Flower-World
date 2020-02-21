package com.flowerworld.items.adapters;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.flowerworld.R;
import com.flowerworld.connections.ItemAdapterConnection;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.ShopItem;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Item> itemArrayList = new ArrayList<>();
    private ArrayList<NewsItem> newsItemArrayList = new ArrayList<>();
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private int posForItemArrayList = 0;



    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
        notifyDataSetChanged();
    }

    public void setNewsItemArrayList(ArrayList<NewsItem> newsItemArrayList) {
        this.newsItemArrayList = newsItemArrayList;
        notifyDataSetChanged();
    }

    public void setShopItems(ArrayList<ShopItem> shopItems) {
        this.shopItems = shopItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_news, parent, false);
                return new NewsHolder(view2);
            case 2:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops_recycle_view, parent, false);
                return new ShopsHolder(view3);
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_shelf, parent, false);
                return new ItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                NewsHolder newsHolder = (NewsHolder) holder;
                newsHolder.bind(newsItemArrayList);
                break;
            case 2:
                ShopsHolder shopsHolder = (ShopsHolder) holder;
                shopsHolder.bind(shopItems);
                break;
            default:
                ItemHolder itemHolder = (ItemHolder) holder;
                System.out.println(posForItemArrayList);
                Item category = itemArrayList.get(posForItemArrayList);
                itemHolder.bind(category);
                posForItemArrayList++;
                break;
        }
    }


    @Override
    public int getItemCount() {
        return itemArrayList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        if (position == 3)
            return 2;
        else return 3;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        Handler categoryHandler;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Item categories) {
            initHandler();
            setItemCount();
            setItemsTitle(categories.getFlowerItemTitle());
            getData(categories.getProducts());
        }

        private void setItemsTitle(String title) {
            TextView titleTextView = itemView.findViewById(R.id.title_for_product_categoues);
            titleTextView.setText(title);
        }

        private void getData(ArrayList<String> ids) {
            ItemAdapterConnection connection = new ItemAdapterConnection(this);
            connection.bind(ids);
        }

        private void setProducts(ArrayList<FlowerItem> products) {
            RecyclerView productsRecycleView = itemView.findViewById(R.id.flower_shelf_recycle_view);
            FlowerItemAdapter adapter = new FlowerItemAdapter();
            adapter.setFlowerItemArrayList(products);
            productsRecycleView.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            productsRecycleView.setLayoutManager(manager);
            productsRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }

        private void setItemCount() {
            RecyclerView productsRecycleView = itemView.findViewById(R.id.flower_shelf_recycle_view);
            FlowerItemAdapter adapter = new FlowerItemAdapter();
            adapter.setItemCount(4);
            productsRecycleView.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            productsRecycleView.setLayoutManager(manager);
            productsRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }

        @SuppressLint("HandlerLeak")
        private void initHandler() {
            categoryHandler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    ArrayList<FlowerItem> products = (ArrayList<FlowerItem>) msg.obj;
                    setProducts(products);
                }
            };
        }

        public void sendProductsMessage(Message msg) {
            categoryHandler.sendMessage(msg);
        }


    }

    class NewsHolder extends RecyclerView.ViewHolder {

        NewsHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(ArrayList<NewsItem> news){
            setNewsItems(news);
        }

        private void setNewsItems(ArrayList<NewsItem> news) {
            ViewPager2 newsItemsViewPager2 = itemView.findViewById(R.id.newsViewPager2);
            NewsItemAdapter adapter = new NewsItemAdapter();
            adapter.setNewsItems(news);
            newsItemsViewPager2.setAdapter(adapter);
        }
    }

    class ShopsHolder extends RecyclerView.ViewHolder {

        ShopsHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(ArrayList<ShopItem> shops) {
            setShopsItems(shops);
        }

        private void setShopsItems(ArrayList<ShopItem> shops) {
            RecyclerView shopsItems = itemView.findViewById(R.id.shops_items_recycle_view);
            ShopItemAdapter adapter = new ShopItemAdapter();
            adapter.setShopItems(shops);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            shopsItems.setLayoutManager(manager);
            shopsItems.setAdapter(adapter);
            shopsItems.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }
    }

}
