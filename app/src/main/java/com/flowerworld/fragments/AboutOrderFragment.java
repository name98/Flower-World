package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.AboutOrderModel;
import com.flowerworld.ui.AboutOrderFragmentUI;

import java.util.Objects;

public class AboutOrderFragment extends Fragment {
    private String orderId;
    private AboutOrderModel model;
    private FlowerItem item;
    private Handler handler;

    AboutOrderFragment(String orderId) {
        this.orderId = orderId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_order_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeHandler();
        startHandler();

    }

    private void createTextViews() {


        View fragmentView = this.getView();
        assert fragmentView != null;
        TextView receiver = fragmentView.findViewById(R.id.aboutOrderFragmentRecieverText);
        TextView date = fragmentView.findViewById(R.id.aboutOrderFragmentTimeText);
        TextView state = fragmentView.findViewById(R.id.aboutOrderFragmentStateText);
        TextView cost = fragmentView.findViewById(R.id.aboutOrderFragmentCostText);
        receiver.setText(model.getReceiver());
        date.setText(model.getDate());
        state.setText(model.getState());
        cost.setText(model.getCost());

    }

    private void createFlowerItemView() {
        View fragmentView = this.getView();
        assert fragmentView != null;
        TextView productName = fragmentView.findViewById(R.id.flowerNameMini);
        RatingBar productRating = fragmentView.findViewById(R.id.flowerRatingBarMini);
        TextView productPrice = fragmentView.findViewById(R.id.flowerPriceMini);
        SimpleDraweeView productImage = fragmentView.findViewById(R.id.flowerPictureMini);
        productName.setText(item.getName());
        productPrice.setText(item.getPrice());
        productRating.setRating(Float.valueOf(item.getRating()));
        productImage.setImageURI(Uri.parse(item.getImageUrl()));
    }

    private void setViewClickListeners() {
        View fragmentView = this.getView();
        assert fragmentView != null;
        Button okButton = fragmentView.findViewById(R.id.aboutOrderFragmentOkButton);
        CardView itemCard = fragmentView.findViewById(R.id.flowerCardView);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).getApp()
                        .getRouter().remove("aboutOrderFragment");
            }
        });
        itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).getApp()
                        .getRouter().addFragment("flowerPage", String.valueOf(item.getId()));
            }
        });
    }

    private void startHandler() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = Message.obtain();
                    AboutOrderFragmentUI fragmentUI = new AboutOrderFragmentUI();
                    fragmentUI.setIdOrder(orderId);
                    fragmentUI.initializeVariables();
                    item = fragmentUI.getItem();
                    model = fragmentUI.getModel();
                    msg.obj = true;
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Log.d("AbOrUI: ", e.toString());
                    Message msg = Message.obtain();
                    msg.obj = false;
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                }
            }
        });
        t.start();
    }

    @SuppressLint("HandlerLeak")
    private void initializeHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                boolean isSuccsess = (boolean) msg.obj;
                if (isSuccsess) {
                    createTextViews();
                    createFlowerItemView();
                    setViewClickListeners();
                } else
                    Log.d("error: ", "in fragment");
            }
        };
    }


}
