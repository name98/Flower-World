package com.flowerworld.ui;

import android.annotation.SuppressLint;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.CommentsGetter;
import com.flowerworld.connections.FlowerFragmentHalper;

import com.flowerworld.fragments.Router;
import com.flowerworld.items.CommentItem;
import com.flowerworld.items.adapters.CommentItemAdapter;
import com.flowerworld.items.adapters.FlowerImageItemAdapter;
import com.flowerworld.methods.Methods;

import java.util.ArrayList;


public class FlowerFragmentUI {
    private View view;
    private Handler handler;
    private Handler handler2;
    private String idProduct;

    public FlowerFragmentUI(String id, View view) {
        this.view = view;
        init();
        start(id);
        initHandler2();
        startHandler2(id);
        idProduct=id;
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                createUI((FlowerFragmentHalper) msg.obj);

            }
        };


    }

    private void initBuy(final FlowerFragmentHalper helper) {
        Button buy = view.findViewById(R.id.flowerPageBuyBt);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addCharterFragment(view.getContext(), Integer.valueOf(helper.getId()));
            }
        });
    }

    private void start(final String id) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                FlowerFragmentHalper helper = new FlowerFragmentHalper(id);
                initBuy(helper);
                try {
                    Message msg = Message.obtain();
                    msg.obj = new FlowerFragmentHalper(id);
                    msg.setTarget(handler);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Log.d("FlowerFragmentUI: ", e.toString());
                }
            }
        });
        t.start();

    }

    @SuppressLint("SetTextI18n")
    private void createUI(final FlowerFragmentHalper helper) {
        TextView name = view.findViewById(R.id.flowerPageNameFlower);
        TextView sizeL = view.findViewById(R.id.flowerPageSizeFlowerL);
        TextView sizeH = view.findViewById(R.id.flowerPageSizeFlowerH);
        TextView compound = view.findViewById(R.id.flowerPageSostav);
        TextView annotan = view.findViewById(R.id.flowerPageAnnotation);
        TextView price = view.findViewById(R.id.flowerPagePriceTextV);
        TextView rate = view.findViewById(R.id.flowerPageTextForRatingB);
        ViewPager2 imagePager = view.findViewById(R.id.flowerPageViewP);
        RatingBar ratingBar = view.findViewById(R.id.flowerPageRatingB);
        CardView cardView = view.findViewById(R.id.flowerPageGoToShop);
        TextView shopName = view.findViewById(R.id.flowerPageShop);

        name.setText(helper.getName());
        compound.setText(helper.getCompound());
        annotan.setText(helper.getAnnotation());
        price.setText(Methods.formatRuble(helper.getPrices()));
        rate.setText(helper.getSumRate().toString());
        FlowerImageItemAdapter adapter = new FlowerImageItemAdapter(helper.getItems());
        imagePager.setAdapter(adapter);
        ratingBar.setRating(helper.getSumRate().floatValue());
        shopName.setText(helper.getShopName());
        sizeH.setText(helper.getSizeH());
        sizeL.setText(helper.getSizeL());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) view.getContext())
                        .getApp()
                        .getRouter()
                        .addFragment("shopPage", helper.getShopName());

            }
        });
        TextView allRate = view.findViewById(R.id.flowerPageNumberRaters);
        ProgressBar oneP = view.findViewById(R.id.flowerPageProgressFor1);
        ProgressBar twoP = view.findViewById(R.id.flowerPageProgressFor2);
        ProgressBar treeP = view.findViewById(R.id.flowerPageProgressFor3);
        ProgressBar fourP = view.findViewById(R.id.flowerPageProgressFor4);
        ProgressBar fiveP = view.findViewById(R.id.flowerPageProgressFor5);

        int numRates = helper.getNumberOfRates().get(0) +
                helper.getNumberOfRates().get(1) +
                helper.getNumberOfRates().get(2) +
                helper.getNumberOfRates().get(3) +
                helper.getNumberOfRates().get(4);

        allRate.setText(numRates + " оценок");
        oneP.setMax(numRates);
        treeP.setMax(numRates);
        twoP.setMax(numRates);
        fiveP.setMax(numRates);
        fourP.setMax(numRates);
        oneP.setProgress(helper.getNumberOfRates().get(0));
        twoP.setProgress(helper.getNumberOfRates().get(1));
        treeP.setProgress(helper.getNumberOfRates().get(2));
        fourP.setProgress(helper.getNumberOfRates().get(3));
        fiveP.setProgress(helper.getNumberOfRates().get(4));
        SimpleDraweeView shopPhoto = view.findViewById(R.id.flowerPageShopPhoto);
        shopPhoto.setImageURI(Uri.parse(helper.getShopLogo()));
    }

    private void startHandler2(final String idProduct) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                CommentsGetter commentsGetter = new CommentsGetter(idProduct, ((MainActivity) view.getContext()).getApp().getDbHelper().getKey());
                commentsGetter.initComment();
                msg.obj = commentsGetter.getCommentItems();
                msg.setTarget(handler2);
                handler2.sendMessage(msg);
            }
        });
        t.start();

    }

    @SuppressLint("HandlerLeak")
    private void initHandler2() {
        handler2 = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                initRecycleView((ArrayList<CommentItem>) msg.obj);
            }
        };
    }

    private void initRecycleView(ArrayList<CommentItem> commentItems) {
        RecyclerView commentsRecycleView = this.view.findViewById(R.id.flowerPageRecView);
        CommentItemAdapter adapter = new CommentItemAdapter();

        adapter.setComments(commentItems);
        adapter.checkMyComment();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        commentsRecycleView.setLayoutManager(manager);
        commentsRecycleView.setAdapter(adapter);
        initButton();
    }
    private void initButton(){
        RecyclerView recyclerView = view.findViewById(R.id.flowerPageRecView);
        final CommentItemAdapter adapter = (CommentItemAdapter) recyclerView.getAdapter();
        Button reteButton = view.findViewById(R.id.flowerPageOpenChangeFragmentButton);
        assert adapter != null;
        if (adapter.haveMy)
            reteButton.setText("Изменить отзыв");
        else
            reteButton.setText("Оставить отзыв");
        reteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) view.getContext()).getApp().getRouter().addFragment("createCommentFragment",idProduct);
            }
        });
    }

}
