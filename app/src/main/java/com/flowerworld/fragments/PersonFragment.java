package com.flowerworld.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.DataMethod;
import com.flowerworld.database.SQLDataBase;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.UserItem;
import com.flowerworld.ui.PersonFragmentUI;

import org.json.JSONArray;
import org.json.JSONObject;

public class PersonFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment,container,false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        UserItem user = getUserItem();
        setViews(user);
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

    }
}
