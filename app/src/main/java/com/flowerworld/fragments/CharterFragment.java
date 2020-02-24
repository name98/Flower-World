package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;
import com.flowerworld.connections.CharterFragmentHelper;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.ui.CharterFragmentUI;

public class CharterFragment extends Fragment implements FragmentSetDataInterface {
    private static final String KEY_FOR_ID_PRODUCT = "productId";
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.charter_fragmnet,container,false);
    }

    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int id = getArguments().getInt(KEY_FOR_ID_PRODUCT);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                ProgressBar progressBar = getView().findViewById(R.id.charterFragmentProgressbar);
                progressBar.setVisibility(View.INVISIBLE);
                LinearLayout linearLayout = getView().findViewById(R.id.charterFragmentLinLay);
                linearLayout.setVisibility(View.VISIBLE);
                new CharterFragmentUI(getView(), CharterFragmentHelper.getPRODUCT());
            }
        };

        initValues(id);



    }

    @SuppressLint("HandlerLeak")
    public void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                FlowerItem item = (FlowerItem) msg.obj;
                bind(item);
            }
        };
    }

    private void initValues(final int id){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                CharterFragmentHelper.bind(id);
                Message msg = new Message();
                msg.setTarget(handler);
                handler.sendMessage(msg);
            }
        });
        t.start();
    }

    private void bind(FlowerItem product) {

    }

    static CharterFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(KEY_FOR_ID_PRODUCT,id);
        CharterFragment fragment = new CharterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }
}
