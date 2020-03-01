package com.flowerworld.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.database.DataBase;
import com.flowerworld.items.UserItem;

public class PersonFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.person_fragment,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        UserItem user = getUserItem();
        setViews(user);
        setListeners();
        setExit();
    }

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    private UserItem getUserItem() {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        UserItem userItem = new UserItem();
        userItem.setUserEmail(helper.get(DataBaseHelper.EMAIL));
        userItem.setUserId(Integer.valueOf(helper.get(DataBaseHelper.KEY)));
        userItem.setUserName(helper.get(DataBaseHelper.USER_NAME));
        userItem.setUserPassword(helper.get(DataBaseHelper.PASSWORD));

        return userItem;
    }

    private void setViews(UserItem user) {
        View view = getView();
        assert view != null;
        TextView userNameTextView = view.findViewById(R.id.person_fragment_user_name_text_view);
        TextView emailTextView = view.findViewById(R.id.person_fragment_user_email_text_view);
        userNameTextView.setText(user.getUserName());
        emailTextView.setText(user.getUserEmail());
    }

    private void setListeners() {
        final View view = getView();
        assert view != null;
        Button activeOrders =  view.findViewById(R.id.person_fragment_active_orders_button);
        activeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addOrderFragment(view.getContext(), false);
            }
        });
        Button completedOrders = view.findViewById(R.id.personFragmentCompledOrdersBt);
        completedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addOrderFragment(view.getContext(), true);
            }
        });
    }
    private void setExit() {
        Button exitButton = getView().findViewById(R.id.person_fragment_exit_button);
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

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void removeAndRecreate() {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        helper.remove();
        Router.removeAllFragments(getActivity());
    }
}
