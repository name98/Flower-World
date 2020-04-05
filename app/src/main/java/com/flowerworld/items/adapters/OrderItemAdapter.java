package com.flowerworld.items.adapters;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.flowerworld.R;
import com.flowerworld.fragments.Router;
import com.flowerworld.items.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemHolder> {
    private ArrayList<OrderItem> items = new ArrayList<>();

    public void setOrders(ArrayList<OrderItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.charter_item, parent, false);
        return new OrderItemHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final OrderItemHolder holder, int position) {
        OrderItem order = items.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderItemHolder extends RecyclerView.ViewHolder {

        OrderItemHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(OrderItem order) {
            setViews(order);
            setListener(Integer.valueOf(order.getId()));
        }

        private void setViews(OrderItem order) {
            TextView dateTextView = itemView.findViewById(R.id.charterItemDateText);
            TextView stateTextView = itemView.findViewById(R.id.charterItemStateText);
            TextView nameProductTextView = itemView.findViewById(R.id.flowerIconName);
            TextView idOrderTextView = itemView.findViewById(R.id.charterItemIdOrderText);
            nameProductTextView.setText(String.format("%s %s", itemView.getResources().getString(R.string.product), order.getNameProduct()));
            stateTextView.setText(String.format("%s %s", itemView.getResources().getString(R.string.order_state), order.getState()));
            dateTextView.setText(String.format("%s %s", itemView.getResources().getString(R.string.order_from), order.getDate()));
            idOrderTextView.setText(String.format("%s %s", itemView.getResources().getString(R.string.order_id), order.getId()));
        }

        private void setListener(final int id) {
            CardView cardView = itemView.findViewById(R.id.charterCard);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.addAboutOrderFragment(itemView.getContext(), String.valueOf(id));
                }
            });
        }
    }
}
