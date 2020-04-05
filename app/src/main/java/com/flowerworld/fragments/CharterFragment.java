package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.R;
import com.flowerworld.connections.CharterConnection;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.interfaces.FragmentSetTwoMessage;
import com.flowerworld.items.CharterItem;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.models.MyDate;

import java.util.Date;
import java.util.Objects;

public class CharterFragment extends Fragment implements FragmentSetTwoMessage {
    private static final String KEY_FOR_ID_PRODUCT = "productId";
    private Handler handlerForGetting;
    private Handler handlerForSending;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.charter_fragmnet, container, false);
    }

    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Router.addProgressFragment(getContext());
        setToolbar();
        setHandlerForGetting();
        setHandlerForSending();
        assert getArguments() != null;
        int id = getArguments().getInt(KEY_FOR_ID_PRODUCT);
        CharterConnection connection = new CharterConnection();
        connection.setParent(this);
        connection.bind(id);
    }

    @SuppressLint("HandlerLeak")
    private void setHandlerForGetting() {
        handlerForGetting = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                FlowerItem product = (FlowerItem) msg.obj;
                bind(product);
            }
        };
    }

    private void bind(FlowerItem product) {
        setViews(product);
        setListener(String.valueOf(product.getId()));
        Router.removeFragmentByTag(getContext(), Router.PROGRESS_FRAGMENT_TAG);
    }

    private void setViews(FlowerItem product) {
        View view = getView();
        assert view != null;
        TextView dateTextView = view.findViewById(R.id.orderDate);
        TextView timeTextView = view.findViewById(R.id.orderTime);
        SimpleDraweeView productImageSimpleDV = view.findViewById(R.id.flowerIconImage);
        TextView productNameTextView = view.findViewById(R.id.flowerIconName);
        productImageSimpleDV.setImageURI(Uri.parse(product.getImageUrl()));
        productNameTextView.setText(product.getName());
        Date nowDate = new Date();
        Calendar calendar;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DATE, 1);
            nowDate = calendar.getTime();
        }
        setDatePicker(nowDate);
        MyDate dateFormat = new MyDate(nowDate);
        dateTextView.setText(dateFormat.getDate());
        timeTextView.setText(dateFormat.getTime());
    }

    private void setListener(final String productId) {
        final View view = getView();
        assert view != null;
        Button buyButton = view.findViewById(R.id.orderGo);
        final EditText receiverEditText = view.findViewById(R.id.orderReceiver);
        final EditText addressEditText = view.findViewById(R.id.orderAddress);
        final TextView dateTextView = view.findViewById(R.id.orderDate);
        final TextView timeTextView = view.findViewById(R.id.orderTime);
        final FragmentSetTwoMessage parent = this;
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper helper = new DataBaseHelper(getContext());
                String useId = helper.getId();
                CharterItem charter = new CharterItem();
                charter.setAddress(addressEditText.getText().toString());
                charter.setDate(dateTextView.getText().toString());
                charter.setIdUser(useId);
                charter.setIdProduct(productId);
                charter.setReceiver(receiverEditText.getText().toString());
                charter.setTime(timeTextView.getText().toString());
                CharterConnection connection = new CharterConnection();
                connection.setParent(parent);
                connection.bind2(charter);
            }
        });
    }

    private void setDatePicker(final Date now) {
        View view = getView();
        assert view != null;
        final TextView dateTextView = view.findViewById(R.id.orderDate);
        final TextView timeTextView = view.findViewById(R.id.orderTime);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTextView.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, now.getYear() + 1900, now.getMonth(), now.getDate());
                dDialog.show();
            }
        });
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeTextView.setText(hourOfDay + ":" + minute);
                    }
                }, now.getHours(), now.getMinutes(), true);
                tDialog.show();
            }
        });
    }


    static CharterFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_FOR_ID_PRODUCT, id);
        CharterFragment fragment = new CharterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void sendMessage(Message msg) {
        handlerForGetting.sendMessage(msg);
    }

    @Override
    public void sendMessage2(Message msg) {
        handlerForSending.sendMessage(msg);
    }

    private void endCharter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ваш заказ принят!")
                .setMessage("Чтобы следить за статусом заказа перейдите в 'Активные заказы'")
                .setCancelable(false)
                .setNegativeButton("Ок",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainFragment parentFragment = (MainFragment) Router.getFragmentByTag(getContext(), Router.MAIN_FRAGMENT_TAG);
                                if (parentFragment != null)
                                    ((SettingsFragment) ChildRouter.getFragmentByTag(parentFragment, ChildRouter.SETTINGS_FRAGMENT_TAG)).reloadValues();
                                Router.removeFragmentByTag(getContext(), Router.CHARTER_FRAGMENT_TAG);

                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressLint("HandlerLeak")
    private void setHandlerForSending() {
        handlerForSending = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                boolean isPerformed = (boolean) msg.obj;
                if (isPerformed)
                    endCharter();
                else Router.removeFragmentByTag(getContext(), Router.CHARTER_FRAGMENT_TAG);
            }
        };
    }

    private void setToolbar() {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Новый заказ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.removeFragmentByTag(getContext(), Router.CHARTER_FRAGMENT_TAG);
            }
        });
    }
}
