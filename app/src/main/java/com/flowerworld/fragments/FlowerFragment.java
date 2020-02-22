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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.interfaces.ProductGetData;
import com.flowerworld.items.CommentItem;
import com.flowerworld.items.FlowerImagesItem;
import com.flowerworld.items.FullProductItem;
import com.flowerworld.items.adapters.CommentItemAdapter;
import com.flowerworld.items.adapters.FlowerImageItemAdapter;
import com.flowerworld.ui.FlowerFragmentUI;

import java.util.ArrayList;
import java.util.Objects;

import static com.flowerworld.R.*;

public class FlowerFragment extends Fragment implements ProductGetData {
    private final static String PRODUCT_ID_KEY = "product_id";
    private Handler handlerSetProduct;
    private Handler handlerSetComment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.product_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Router.addProgressFragment(getContext());
        setProductHandler();


        assert getArguments() != null;
        new FlowerFragmentUI(String.valueOf(getArguments().getInt(PRODUCT_ID_KEY)), view);
    }

    static FlowerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY, id);
        FlowerFragment fragment = new FlowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    void setCommentHandler

    @SuppressLint("SetTextI18n")
    private void setTextViews(FullProductItem product) {
        View view = getView();
        assert view != null;
        TextView nameTextView = view.findViewById(id.flowerPageNameFlower);
        TextView priceTextView = view.findViewById(id.flowerPagePriceTextV);
        TextView descriptionTextView = view.findViewById(id.flowerPageAnnotation);
        TextView heightProductTextView = view.findViewById(id.flowerPageSizeFlowerH);
        TextView widthProductTextView = view.findViewById(id.flowerPageSizeFlowerL);
        TextView compoundTextView = view.findViewById(id.flowerPageSostav);
        TextView ratingProductTextView = view.findViewById(id.flowerPageTextForRatingB);
        TextView shopName = view.findViewById(id.flowerPageShop);
        nameTextView.setText(product.getName());
        priceTextView.setText(product.getPrice());
        descriptionTextView.setText(product.getAnnotation());
        heightProductTextView.setText(product.getSizeH());
        widthProductTextView.setText(product.getSizeL());
        compoundTextView.setText(product.getCompound());
        ratingProductTextView.setText(product.getSumRate().toString());
        shopName.setText(product.getShopName());
    }

    private void setViewPager2(ArrayList<FlowerImagesItem> images) {
        ViewPager2 productImagesViewPager2 = getView().findViewById(id.flowerPageViewP);
        FlowerImageItemAdapter adapter = new FlowerImageItemAdapter();
        adapter.setFlowerImagesItemArrayList(images);
        productImagesViewPager2.setAdapter(adapter);
    }

    private void setProductRate(FullProductItem product) {
        View view = getView();
        assert view != null;
        RatingBar productRating = view.findViewById(id.flowerPageRatingB);
        ProgressBar oneStartProgressBar = view.findViewById(id.flowerPageProgressFor1);
        ProgressBar twoStartProgressBar = view.findViewById(id.flowerPageProgressFor2);
        ProgressBar treeStartProgressBar = view.findViewById(id.flowerPageProgressFor3);
        ProgressBar fourStartProgressBar = view.findViewById(id.flowerPageProgressFor4);
        ProgressBar fiveStartProgressBar = view.findViewById(id.flowerPageProgressFor5);
        TextView numberOfRatesTextView = view.findViewById(id.flowerPageNumberRaters);
        int numRates = product.getNumberOfRates().get(0) +
                product.getNumberOfRates().get(1) +
                product.getNumberOfRates().get(2) +
                product.getNumberOfRates().get(3) +
                product.getNumberOfRates().get(4);
        numberOfRatesTextView.setText(numRates);

        oneStartProgressBar.setMax(numRates);
        twoStartProgressBar.setMax(numRates);
        treeStartProgressBar.setMax(numRates);
        fourStartProgressBar.setMax(numRates);
        fiveStartProgressBar.setMax(numRates);
        productRating.setRating(product.getSumRate().floatValue());
        oneStartProgressBar.setProgress(product.getNumberOfRates().get(0));
        twoStartProgressBar.setProgress(product.getNumberOfRates().get(1));
        treeStartProgressBar.setProgress(product.getNumberOfRates().get(2));
        fourStartProgressBar.setProgress(product.getNumberOfRates().get(3));
        fiveStartProgressBar.setProgress(product.getNumberOfRates().get(4));
    }

    private void setShop(String logoUrl) {
        SimpleDraweeView shopPhoto = Objects.requireNonNull(getView()).findViewById(id.flowerPageShopPhoto);
        shopPhoto.setImageURI(Uri.parse(logoUrl));
    }

    private void setComments(ArrayList<CommentItem> comments) {
        CommentItemAdapter adapter = new CommentItemAdapter();
        adapter.setComments(comments);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView commentsRecycleView = Objects.requireNonNull(getView()).findViewById(id.flowerPageRecView);
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
        Button buyButton = getView().findViewById(R.id.flowerItemBuyButton);
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
    }
}