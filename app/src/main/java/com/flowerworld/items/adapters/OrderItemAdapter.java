package com.flowerworld.items.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.fragments.Router;
import com.flowerworld.items.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemHolder> {
    private ArrayList<OrderItem> items=new ArrayList<>();

    public OrderItemAdapter(ArrayList<OrderItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.charter_item,parent,false);
        return new OrderItemHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final OrderItemHolder holder, int position) {
        final OrderItem item = items.get(position);
        holder.nameProduct.setText(item.getNameProduct());
        holder.ico.setImageURI(Uri.parse(item.getIco()));
        holder.idOrder.setText("#"+item.getId());
        holder.state.setText(item.getState());
        holder.date.setText("Заказ от "+item.getDate());

        if (items.get(position).getState().equals("доставлено")){

            holder.layout.setBackgroundResource(R.color.positiveState);
        }
        if(items.get(position).getState().equals("ожидание")){
            holder.layout.setBackgroundResource(R.color.waitState);
        }
        if(items.get(position).getState().equals("отправка"))
            holder.layout.setBackgroundResource(R.color.sendState);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addAboutOrderFragment(holder.cardView.getContext(),item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderItemHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private SimpleDraweeView ico;
        private TextView date;
        private TextView state;
        private TextView nameProduct;
        private TextView idOrder;
        private RelativeLayout layout;
        OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.charterCard);
            date = itemView.findViewById(R.id.charterItemDateText);
            state=itemView.findViewById(R.id.charterItemStateText);
            nameProduct=itemView.findViewById(R.id.flowerIconName);
            ico=itemView.findViewById(R.id.flowerIconImage);
            idOrder=itemView.findViewById(R.id.charterItemIdOrderText);
            layout=itemView.findViewById(R.id.charterItemFrameForState);
        }
    }
}
