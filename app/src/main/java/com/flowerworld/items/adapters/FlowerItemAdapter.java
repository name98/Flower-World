package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;


import java.util.ArrayList;

public class FlowerItemAdapter extends RecyclerView.Adapter<FlowerItemAdapter.FlowerItemViewHolder>{
    private ArrayList<FlowerItem> flowerItemArrayList=new ArrayList<FlowerItem>();

    public FlowerItemAdapter(ArrayList<FlowerItem> flowerItemArrayList) {
        this.flowerItemArrayList = flowerItemArrayList;
    }

    @NonNull
    @Override
    public FlowerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_layout,parent,false);
        return new FlowerItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final FlowerItemViewHolder holder, int position) {
        final FlowerItem flowerItem = flowerItemArrayList.get(position);
        holder.flowerTitle.setText(flowerItem.getName());
        holder.flowerPrice.setText(Methods.formatRuble(flowerItem.getPrice()));
        float rating=Float.valueOf(flowerItem.getRating());
        holder.flowerRating.setRating(rating);
        System.out.println(flowerItem.getImageUrl());
        Uri uri= Uri.parse(flowerItem.getImageUrl());
        holder.flowerImage.setImageURI(uri);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) holder.getFlowerTitle().getContext())
                        .getApp()
                        .getRouter()
                        .addFragment("flowerPage",
                                String.valueOf(flowerItem.getId()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return flowerItemArrayList.size();
    }

    class FlowerItemViewHolder extends RecyclerView.ViewHolder {
        private TextView flowerTitle;
        SimpleDraweeView flowerImage;
        TextView flowerPrice;
        RatingBar flowerRating;
        CardView cardView;
        public FlowerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            Fresco.initialize(itemView.getContext());
            flowerImage=itemView.findViewById(R.id.flowerPictureMini);
            flowerTitle=itemView.findViewById(R.id.flowerNameMini);
            flowerPrice=itemView.findViewById(R.id.flowerPriceMini);
            flowerRating=itemView.findViewById(R.id.flowerRatingBarMini);
            cardView=itemView.findViewById(R.id.flowerCardView);

        }

        public TextView getFlowerTitle() {
            return flowerTitle;
        }
    }
}
