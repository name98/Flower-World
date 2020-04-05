package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.SettingsConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.UserItem;

import java.util.Map;

public class SettingsFragment extends Fragment implements FragmentSetDataInterface {
    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sittings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initHandler();
        initUserAndLoadData();
    }

    private void setView(Map<String, String> data) {
        View parent = getView();
        assert parent != null;
        TextView activeCountTextView = parent.findViewById(R.id.sittings_fragment_active_orders_count_text_view);
        TextView completedCountTextView = parent.findViewById(R.id.sittings_fragment_completed_orders_count_text_view);
        TextView followCountTextView = parent.findViewById(R.id.sittings_fragment_follows_orders_count_text_view);
        TextView commentedCountTextView = parent.findViewById(R.id.sittings_fragment_products_with_comment_count_text_view);
        activeCountTextView.setText(data.get("active_count") + " заказ(а)");
        completedCountTextView.setText(data.get("completed_count") + " заказ(а)");
        followCountTextView.setText(data.get("follow_count") + " добавленных");
        commentedCountTextView.setText(data.get("commented_count") + " добавленных");
        setListeners();
    }

    private void setListeners() {
        View parent = getView();
        assert parent != null;
        CardView activeOrdersCardView = parent.findViewById(R.id.sittings_fragment_active_orders_card_view);
        CardView completedOrdersCardView = parent.findViewById(R.id.sittings_fragment_completed_orders_card_view);
        CardView followOrdersCardView = parent.findViewById(R.id.sittings_fragment_follows_orders_card_view);
        CardView aboutUsCardView = parent.findViewById(R.id.sittings_fragment_about_app_card_view);
        CardView commentedOrdersCardView = parent.findViewById(R.id.sittings_fragment_products_with_comment_card_view);
        CardView logoutCardView = parent.findViewById(R.id.sittings_fragment_logout_card_view);
        activeOrdersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addOrderFragment(getContext(), false);
            }
        });
        completedOrdersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addOrderFragment(getContext(), true);
            }
        });
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Подтвердите действие");
        alertDialog.setMessage("Выход из Flower World?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeAndRecreate();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        followOrdersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addFollowFragment(getContext());
            }
        });
        commentedOrdersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addCommentedFragment(getContext());
            }
        });

        aboutUsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addAboutAppFragment(getContext());
            }
        });

    }

    private void removeAndRecreate() {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        helper.remove();
        Router.removeAllFragments(getActivity());
    }

    private void initUserAndLoadData() {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        UserItem userItem = new UserItem();
        userItem.setUserEmail(helper.get(DataBaseHelper.EMAIL));
        userItem.setUserId(Integer.valueOf(helper.get(DataBaseHelper.KEY)));
        userItem.setUserName(helper.get(DataBaseHelper.USER_NAME));
        userItem.setUserPassword(helper.get(DataBaseHelper.PASSWORD));
        View view = getView();
        assert view != null;
        TextView userNameTextView = view.findViewById(R.id.sittings_fragment_user_name_text_view);
        TextView emailTextView = view.findViewById(R.id.settings_fragment_user_email_text_view);
        userNameTextView.setText(userItem.getUserName());
        emailTextView.setText(userItem.getUserEmail());
        SettingsConnection connection = new SettingsConnection();
        connection.setParent(this);
        connection.bind(userItem.getUserEmail());
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                setView((Map<String, String>) msg.obj);
            }
        };
    }


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public void reloadValues() {
        initUserAndLoadData();
        System.out.println("hsakjdhasjkdhjasdk");
    }
}
