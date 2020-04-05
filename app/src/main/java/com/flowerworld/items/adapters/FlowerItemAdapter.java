package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.fragments.Router;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;


import java.util.ArrayList;

public class FlowerItemAdapter extends RecyclerView.Adapter<FlowerItemAdapter.FlowerItemViewHolder> {

    private ArrayList<FlowerItem> flowerItemArrayList;
    private int itemCount = 0;

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        notifyDataSetChanged();
    }

    public void setFlowerItemArrayList(ArrayList<FlowerItem> flowerItemArrayList) {
        this.flowerItemArrayList = flowerItemArrayList;
        itemCount = this.flowerItemArrayList.size();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlowerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_item_home_fragment,parent,false);
        return new FlowerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FlowerItemViewHolder holder, int position) {
        if(flowerItemArrayList != null){
            holder.bind(flowerItemArrayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    class FlowerItemViewHolder extends RecyclerView.ViewHolder {

        FlowerItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind (FlowerItem item) {
            setClickListener(item.getId());
            setProductImage(item.getImageUrl());
            setProductName(item.getName());
            setProductPrice(item.getPrice());
            setProductRating(Float.valueOf(item.getRating()));
            offProgress();
        }

        private void setProductImage(String url) {
            SimpleDraweeView productImage = itemView.findViewById(R.id.flowerPictureMini);
            System.out.println(url);
            productImage.setImageURI(Uri.parse(url));
        }

        private void setProductName(String name) {
            TextView productName = itemView.findViewById(R.id.flower_item_home_fragment_product_title_text_view);
            productName.setText(name);
        }

        private void setProductPrice(String price) {
            TextView productPrice = itemView.findViewById(R.id.flower_item_home_fragment_product_price_text_view);
            productPrice.setText(Methods.formatRuble(price));
        }

        private void setProductRating(float f) {
            RatingBar productRatingBar = itemView.findViewById(R.id.flowerRatingBarMini);
            productRatingBar.setRating(f);
        }

        private void setClickListener(final int id) {
            CardView productPane = itemView.findViewById(R.id.flowerCardView);
            productPane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.addFlowerFragment(itemView.getContext(),id);
                }
            });
        }

        private void offProgress() {
            RelativeLayout progressPane = itemView.findViewById(R.id.flower_layout_progress_pane);
            progressPane.setVisibility(View.INVISIBLE);
        }
    }
}
