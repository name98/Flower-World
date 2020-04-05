package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.fragments.Router;
import com.flowerworld.items.NewsItem;

import java.util.ArrayList;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemHolder> {
    private ArrayList<NewsItem> newsItems = new ArrayList<>();

    void setNewsItems(ArrayList<NewsItem> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsItemHolder holder, final int position) {
        holder.setImage(newsItems.get(position));
        holder.setTitle(newsItems.get(position));
        holder.newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addGridFragment(holder.newsTitleText.getContext(), newsItems.get(position).getIds(), newsItems.get(position).getTitleNews());
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class NewsItemHolder extends RecyclerView.ViewHolder {
        TextView newsTitleText;
        SimpleDraweeView newImage;

        NewsItemHolder(@NonNull View itemView) {
            super(itemView);
            newsTitleText = itemView.findViewById(R.id.news_item_head_title_text_view);
            newImage = itemView.findViewById(R.id.newsImage);
        }

        void setImage(NewsItem newsItem) {
            String uri = newsItem.getUrlImage();
            newImage.setImageURI(Uri.parse(uri));
        }

        void setTitle(NewsItem newsItem) {
            newsTitleText.setText(newsItem.getTitleNews());
        }
    }
}
