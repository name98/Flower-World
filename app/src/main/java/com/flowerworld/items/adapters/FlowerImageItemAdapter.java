package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.items.FlowerImagesItem;

import java.util.ArrayList;

public class FlowerImageItemAdapter extends RecyclerView.Adapter<FlowerImageItemAdapter.FlowerItemHolder> {
    private ArrayList<FlowerImagesItem> flowerImagesItemArrayList;


    @NonNull
    @Override
    public FlowerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_page_images_banner, parent, false);
        return new FlowerImageItemAdapter.FlowerItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerItemHolder holder, int position) {
        holder.setFlowerImage(flowerImagesItemArrayList.get(position));
    }

    public void setFlowerImagesItemArrayList(ArrayList<FlowerImagesItem> flowerImagesItemArrayList) {
        this.flowerImagesItemArrayList = flowerImagesItemArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return flowerImagesItemArrayList.size();
    }

    class FlowerItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView flowerImage;

        FlowerItemHolder(@NonNull View itemView) {
            super(itemView);
            flowerImage = itemView.findViewById(R.id.imageBannerFP);
        }

        void setFlowerImage(FlowerImagesItem flowerImage) {
            String uri = flowerImage.getImageUrl();
            this.flowerImage.setImageURI(Uri.parse(uri));
        }
    }
}
