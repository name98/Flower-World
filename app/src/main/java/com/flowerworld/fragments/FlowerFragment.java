package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.drawee.view.SimpleDraweeView;

import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.FollowButton;
import com.flowerworld.connections.ProductConnection;
import com.flowerworld.interfaces.ProductGetData;
import com.flowerworld.items.CommentItem;
import com.flowerworld.items.FlowerImagesItem;
import com.flowerworld.items.FullProductItem;
import com.flowerworld.items.RatingItem;
import com.flowerworld.items.adapters.CommentItemAdapter;
import com.flowerworld.items.adapters.FlowerImageItemAdapter;
import com.flowerworld.methods.Methods;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;


import java.util.ArrayList;
import java.util.Objects;


public class FlowerFragment extends Fragment implements ProductGetData {
    private final static String PRODUCT_ID_KEY = "product_id";
    private Handler handlerSetComment;
    private Handler handlerSetProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Router.addProgressFragment(getContext());
        int productId = getArguments().getInt(PRODUCT_ID_KEY);
        setProductHandler();
        setCommentHandler();
        ProductConnection connection = new ProductConnection();
        connection.setParent(this);
        connection.bind(productId,Integer.valueOf(getUserId()));
    }

    static FlowerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY, id);
        FlowerFragment fragment = new FlowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("HandlerLeak")
    private void setCommentHandler() {
        handlerSetComment = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<CommentItem> comments = (ArrayList<CommentItem>) msg.obj;
                setComments(comments);
                Router.removeProgressFragment(getContext());
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void setTextViews(FullProductItem product) {
        View view = getView();
        assert view != null;
        Button priceTextView = view.findViewById(R.id.flowerPageBuyBt);
        TextView descriptionTextView = view.findViewById(R.id.flowerPageAnnotation);
        TextView heightProductTextView = view.findViewById(R.id.flowerPageSizeFlowerH);
        TextView widthProductTextView = view.findViewById(R.id.flowerPageSizeFlowerL);
        TextView compoundTextView = view.findViewById(R.id.flowerPageSostav);
        TextView ratingProductTextView = view.findViewById(R.id.flowerPageTextForRatingB);
        TextView shopName = view.findViewById(R.id.flowerPageShop);
        TextView productTitleTextView= view.findViewById(R.id.product_fragment_product_name_text_view);
        productTitleTextView.setText(product.getName());
        priceTextView.setText("Купить\n" + Methods.formatRuble(product.getPrice()));
        descriptionTextView.setText(product.getAnnotation());
        heightProductTextView.setText(product.getSizeH());
        widthProductTextView.setText(product.getSizeL());
        compoundTextView.setText(product.getCompound());
        ratingProductTextView.setText(product.getSumRate());
        shopName.setText(product.getShopName());
        setLikeButton(product.isFollow(), product.getId());
    }

    private void setViewPager2(ArrayList<FlowerImagesItem> images) {
        System.out.println(images.size());
        ViewPager2 productImagesViewPager2 = Objects.requireNonNull(getView()).findViewById(R.id.flowerPageViewP);
        FlowerImageItemAdapter adapter = new FlowerImageItemAdapter();
        adapter.setFlowerImagesItemArrayList(images);
        productImagesViewPager2.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setProductRate(FullProductItem product) {
        View view = getView();
        assert view != null;
        RatingBar productRating = view.findViewById(R.id.flowerPageRatingB);
        ProgressBar oneStartProgressBar = view.findViewById(R.id.flowerPageProgressFor1);
        ProgressBar twoStartProgressBar = view.findViewById(R.id.flowerPageProgressFor2);
        ProgressBar treeStartProgressBar = view.findViewById(R.id.flowerPageProgressFor3);
        ProgressBar fourStartProgressBar = view.findViewById(R.id.flowerPageProgressFor4);
        ProgressBar fiveStartProgressBar = view.findViewById(R.id.flowerPageProgressFor5);
        TextView numberOfRatesTextView = view.findViewById(R.id.flowerPageNumberRaters);
        RatingItem ratingItem = new RatingItem();
        ratingItem.setOne(product.getNumberOfRates().get(0));
        ratingItem.setTwo(product.getNumberOfRates().get(1));
        ratingItem.setTree(product.getNumberOfRates().get(2));
        ratingItem.setFour(product.getNumberOfRates().get(3));
        ratingItem.setFive(product.getNumberOfRates().get(4));

        int numRates = product.getNumberOfRates().get(0) +
                product.getNumberOfRates().get(1) +
                product.getNumberOfRates().get(2) +
                product.getNumberOfRates().get(3) +
                product.getNumberOfRates().get(4);
        numberOfRatesTextView.setText(numRates + " оценок");
        oneStartProgressBar.setMax(numRates);
        twoStartProgressBar.setMax(numRates);
        treeStartProgressBar.setMax(numRates);
        fourStartProgressBar.setMax(numRates);
        fiveStartProgressBar.setMax(numRates);
        productRating.setRating((float) ratingItem.getGeneral());
        oneStartProgressBar.setProgress(product.getNumberOfRates().get(0));
        twoStartProgressBar.setProgress(product.getNumberOfRates().get(1));
        treeStartProgressBar.setProgress(product.getNumberOfRates().get(2));
        fourStartProgressBar.setProgress(product.getNumberOfRates().get(3));
        fiveStartProgressBar.setProgress(product.getNumberOfRates().get(4));
    }

    private void setShop(String logoUrl) {
        SimpleDraweeView shopPhoto = Objects.requireNonNull(getView()).findViewById(R.id.flowerPageShopPhoto);
        shopPhoto.setImageURI(Uri.parse(logoUrl));
    }

    private void setComments(ArrayList<CommentItem> comments) {
        CommentItemAdapter adapter = new CommentItemAdapter();
        adapter.setComments(comments);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView commentsRecycleView = Objects.requireNonNull(getView()).findViewById(R.id.flowerPageRecView);
        commentsRecycleView.setAdapter(adapter);
        commentsRecycleView.setLayoutManager(manager);
        setButtonToComment(adapter);
    }

    private void setButtonToComment(CommentItemAdapter adapter) {
        Button rateButton = Objects.requireNonNull(getView()).findViewById(R.id.flowerPageOpenChangeFragmentButton);
        if (adapter.haveMy)
            rateButton.setText("Изменить отзыв");
        else
            rateButton.setText("Оставить отзы");
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                int productId = getArguments().getInt(PRODUCT_ID_KEY);
                Router.addCreateCommentFragment(getContext(), productId);
            }
        });
    }

    private void setBuyButton(final int id) {
        Button buyButton = Objects.requireNonNull(getView()).findViewById(R.id.flowerPageBuyBt);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addCharterFragment(getContext(), id);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void setProductHandler() {
        handlerSetProduct = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                FullProductItem product = (FullProductItem) msg.obj;
                bind(product);
            }
        };
    }

    @Override
    public void sendMessageForSetProduct(Message msg) {
        handlerSetProduct.sendMessage(msg);
    }

    @Override
    public void sendMessageForSetComment(Message msg) {
        handlerSetComment.sendMessage(msg);
    }

    private void bind(FullProductItem product) {
        setTextViews(product);
        setViewPager2(product.getItems());
        setBuyButton(Integer.valueOf(product.getId()));
        setShop(product.getShopLogo());
        setProductRate(product);
        setCardListener(product.getShopName());
        setToolbar();
    }

    private void setCardListener(final String shopName) {
        CardView shopPaneCardView = Objects.requireNonNull(getView()).findViewById(R.id.flowerPageGoToShop);
        shopPaneCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addShopFragment(getContext(), shopName);
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.flower_fragment_toolbar);
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.PRODUCT_FRAGMENT_TAG);
            }
        });
        CollapsingToolbarLayout collapsingToolbar = getView().findViewById(R.id.flower_fragment_collapsing_toolbar);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.appColorWhite));
    }

    private void setLikeButton(boolean isFollow, final String productId) {
        final String userId = getUserId();
        final Button follow = Objects.requireNonNull(getView()).findViewById(R.id.product_fragment_follow_button);
        setButtonFollow(isFollow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasFollow = follow.getText().equals("Отложено");
                setButtonFollow(!wasFollow);

                if(wasFollow){
                    FollowButton.setUnLike(userId, productId);
                }
                else
                    FollowButton.setLike(userId, productId);
                reloadValues();

            }
        });
    }
    private void reloadValues() {
        MainFragment parentFragment = (MainFragment) Router.getFragmentByTag(getContext(), Router.MAIN_FRAGMENT_TAG);
        if (parentFragment != null)
            ((SettingsFragment) ChildRouter.getFragmentByTag(parentFragment, ChildRouter.SETTINGS_FRAGMENT_TAG)).reloadValues();
    }

    private String getUserId() {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        return helper.get(DataBaseHelper.KEY);
    }

    private void setButtonFollow(boolean isFollow){
        Button follow = Objects.requireNonNull(getView()).findViewById(R.id.product_fragment_follow_button);
        if (isFollow){
            follow.setText("Отложено");
            follow.setBackgroundColor(getResources().getColor(R.color.my_orange_active));
        }
        else {
            follow.setText("Отложить");
            follow.setBackgroundColor(getResources().getColor(R.color.my_orange));
        }
    }

}