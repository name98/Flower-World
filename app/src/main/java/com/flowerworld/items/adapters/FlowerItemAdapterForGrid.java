package com.flowerworld.items.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.items.FlowerItem;

import java.util.ArrayList;

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
        }

        private void setNameProduct(String nameProduct) {
            TextView productNameTextView = itemView.findViewById(R.id.flowerItemProductNameTextView);
            productNameTextView.setText(nameProduct);
        }

        private void setPriceProduct(String priceProduct) {
            TextView priceProductPriceTextView = itemView.findViewById(R.id.flowerItemProductPriceTextView);
            priceProductPriceTextView.setText(priceProduct);
        }

        private void setRatingProduct(float ratingProduct) {
            RatingBar ratingProductRatingBar = itemView.findViewById(R.id.flowerItemRatingBar);
            ratingProductRatingBar.setRating(ratingProduct);
        }

        private void setPaneListener(int idProduct) {
            LinearLayout mainPaneLinerLayout = itemView.findViewById(R.id.flowerItemMainPaneLinerLayout);
            mainPaneLinerLayout.setBackgroundColor(itemView.getResources().getColor(R.color.appColorWhite));
            mainPaneLinerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //открыть страницу с цветами
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
    }
}
