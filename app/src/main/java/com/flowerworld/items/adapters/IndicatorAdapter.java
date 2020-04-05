package com.flowerworld.items.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;

import java.util.ArrayList;

public class IndicatorAdapter extends RecyclerView.Adapter<IndicatorAdapter.IndicatorHolder> {
    private int sizeList = 0;
    private int activeViewPosition = 0;

    void setParam(int activeViewPosition, int sizeList) {
        this.sizeList = sizeList;
        this.activeViewPosition = activeViewPosition;
        notifyDataSetChanged();
    }

    public void setActive(int position) {
        int oldActive = activeViewPosition;
        activeViewPosition = position;
        notifyItemChanged(oldActive);
        notifyItemChanged(activeViewPosition);
    }

    @NonNull
    @Override
    public IndicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IndicatorHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.indicator_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorHolder holder, int position) {
        System.out.println("POSITION ON RV:" + position);
        boolean flag = position == activeViewPosition;
        holder.setState(flag);
    }

    @Override
    public int getItemCount() {
        return sizeList;
    }

    class IndicatorHolder extends RecyclerView.ViewHolder{
        private RelativeLayout indicatorRelativeLayout;

        IndicatorHolder(@NonNull View itemView) {
            super(itemView);
            indicatorRelativeLayout = itemView.findViewById(R.id.indicator_item_indicator_relative_layout);
        }

        void setState(boolean flag) {
            if (flag)
                indicatorRelativeLayout.setBackgroundColor(itemView.getResources().getColor(R.color.active_red));
            else
                indicatorRelativeLayout.setBackgroundColor(itemView.getResources().getColor(R.color.text_light_grey));
        }
    }
}
