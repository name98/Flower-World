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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.connections.AboutOrderConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.AboutOrderItem;
import com.flowerworld.items.FlowerItem;

import java.util.Objects;


public class AboutOrderFragment extends Fragment implements FragmentSetDataInterface {
    private static final String ORDER_KEY = "order_id";
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_order_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        Router.addProgressFragment(getContext());
        String orderId = getArguments().getString(ORDER_KEY);
        setHandler();
        AboutOrderConnection connection = new AboutOrderConnection();
        connection.setParent(this);
        connection.bind(orderId);
    }

    private void setTextViews(AboutOrderItem order) {
        View fragmentView = this.getView();
        assert fragmentView != null;
        TextView receiver = fragmentView.findViewById(R.id.aboutOrderFragmentRecieverText);
        TextView date = fragmentView.findViewById(R.id.aboutOrderFragmentTimeText);
        TextView state = fragmentView.findViewById(R.id.aboutOrderFragmentStateText);
        TextView cost = fragmentView.findViewById(R.id.aboutOrderFragmentCostText);
        receiver.setText(order.getReceiver());
        date.setText(order.getDate());
        state.setText(order.getState());
        cost.setText(order.getCost());

    }

    private void setProductViews(FlowerItem product) {
        View fragmentView = this.getView();
        assert fragmentView != null;
        TextView productName = fragmentView.findViewById(R.id.flowerNameMini);
        RatingBar productRating = fragmentView.findViewById(R.id.flowerRatingBarMini);
        TextView productPrice = fragmentView.findViewById(R.id.flowerPriceMini);
        SimpleDraweeView productImage = fragmentView.findViewById(R.id.flowerPictureMini);
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());
        productRating.setRating(Float.valueOf(product.getRating()));
        productImage.setImageURI(Uri.parse(product.getImageUrl()));
        RelativeLayout paneRelativeLayout = fragmentView.findViewById(R.id.flower_layout_progress_pane);
        paneRelativeLayout.setVisibility(View.INVISIBLE);
    }

    private void setListeners(final int id) {
        View fragmentView = this.getView();
        assert fragmentView != null;
        Button okButton = fragmentView.findViewById(R.id.aboutOrderFragmentOkButton);
        CardView itemCard = fragmentView.findViewById(R.id.flowerCardView);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.ABOUT_ORDER_FRAGMENT_TAG);
            }
        });
        itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addFlowerFragment(getContext(), id);
            }
        });
    }

    public static AboutOrderFragment newInstance(String orderId) {
        Bundle args = new Bundle();
        args.putString(ORDER_KEY, orderId);
        AboutOrderFragment fragment = new AboutOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                AboutOrderItem order = (AboutOrderItem) msg.obj;
                bind(order);
            }
        };
    }

    private void bind(AboutOrderItem order) {
        setTextViews(order);
        setProductViews(order.getProduct());
        setListeners(Integer.valueOf(order.getProductId()));
        setToolbar(order.getOrderId());
        Router.removeFragmentByTag(getContext(), Router.PROGRESS_FRAGMENT_TAG);
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    private void setToolbar(String title) {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Заказ №" + title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.ABOUT_ORDER_FRAGMENT_TAG);
            }
        });
    }
}
