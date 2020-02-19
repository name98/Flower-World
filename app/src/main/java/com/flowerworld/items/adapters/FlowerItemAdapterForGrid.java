package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.fragments.Router;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class FlowerItemAdapterForGrid extends RecyclerView.Adapter<FlowerItemAdapterForGrid.FlowerItemHolder> {

    private ArrayList<FlowerItem> items = new ArrayList<>();

    public void setItems(ArrayList<FlowerItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlowerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_item, parent, false);
        return new FlowerItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerItemHolder holder, int position) {
        FlowerItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class FlowerItemHolder extends RecyclerView.ViewHolder {

        FlowerItemHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(FlowerItem flowerItem) {
            setNameProduct(flowerItem.getName());
            setPriceProduct(flowerItem.getPrice());
            setRatingProduct(Float.valueOf(flowerItem.getRating()));
            setPaneListener(flowerItem.getId());
            setBuyButtonListener(flowerItem.getId());
            setProductImage(flowerItem.getImageUrl());
        }

        private void setNameProduct(String nameProduct) {
            TextView productNameTextView = itemView.findViewById(R.id.flowerItemProductNameTextView);
            productNameTextView.setText(nameProduct);
        }

        private void setPriceProduct(String priceProduct) {
            TextView priceProductPriceTextView = itemView.findViewById(R.id.flowerItemProductPriceTextView);
            priceProductPriceTextView.setText(Methods.formatRuble(priceProduct));
        }

        private void setRatingProduct(float ratingProduct) {
            RatingBar ratingProductRatingBar = itemView.findViewById(R.id.flowerItemRatingBar);
            ratingProductRatingBar.setRating(ratingProduct);
        }

        private void setPaneListener(final int idProduct) {
            ConstraintLayout mainPaneConstraintLayout = itemView.findViewById(R.id.flowerItemConstraintLayout);
            mainPaneConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addAboutProductFragment(idProduct);
                }
            });
        }

        private void setBuyButtonListener(int idProduct) {
            Button buyButton = itemView.findViewById(R.id.flowerItemBuyButton);
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //открыть фрагмент покупки
                }
            });
        }

        private void setProductImage(String urlImage){
            SimpleDraweeView productImageSimpleDraweeView = itemView.findViewById(R.id.flowerItemSimpleDraweeView);
            productImageSimpleDraweeView.setImageURI(Uri.parse(urlImage));
        }

        private void addAboutProductFragment(int idProduct) {
            ((MainActivity) itemView.getContext()).getApp().getRouter().addFrament("flowerPage",String.valueOf(idProduct));
        }
    }
}
