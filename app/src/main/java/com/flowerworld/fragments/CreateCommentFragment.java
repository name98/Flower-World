package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.CreateCommentFragmentHelper;
import com.flowerworld.connections.DataBaseHelper;

import com.flowerworld.models.MyDate;

import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.N)
public class CreateCommentFragment extends Fragment {
    private String idProduct;
    private CreateCommentFragmentHelper createHelper;
    private Handler checkIsCommented;


    CreateCommentFragment(String idProduct) {
        this.idProduct = idProduct;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_comment_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initFirstlyValues();
    }

    private boolean isUserCommented(){
        return createHelper.isCommented();
    }
    @SuppressLint("HandlerLeak")
    private void initCheckCommented(){
        checkIsCommented = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if((boolean) msg.obj){
                    isChangeMode();
                }
                else isCreateMode();
            }
        };
    }
    private void startCheckCommentedHandle(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = isUserCommented();
                msg.setTarget(checkIsCommented);
                checkIsCommented.sendMessage(msg);
            }
        });
        t.start();
    }

    private void isChangeMode(){
        View view = getView();
        assert view != null;
        EditText comment = view.findViewById(R.id.createCommentEnterText);
        comment.setText(createHelper.getComment());
        RatingBar rated = view.findViewById(R.id.createCommentRating);
        rated.setRating(createHelper.getRate()*2);
        TextView textRate = view.findViewById(R.id.createCommentResultRateTextView);
        textRate.setText(getResultTextRate(createHelper.getRate()));
        initButtonForChangeMode();

    }
    private void isCreateMode(){
        initButtonForCreateComment();
        TextView textRate = Objects.requireNonNull(getView()).findViewById(R.id.createCommentResultRateTextView);
        textRate.setText(getResultTextRate(5));
    }

    private void initFirstlyValues(){
        createHelper= new CreateCommentFragmentHelper();
        createHelper.setProductId(this.idProduct);
        DataBaseHelper dBHelper = new DataBaseHelper(this.getContext());
        createHelper.setUserId(dBHelper.getKey());
        initCheckCommented();
        startCheckCommentedHandle();
        initRateBar();
    }

    private String getResultTextRate(int i){
        switch (i) {
            case 1:
                return "Плохо";
            case 2:
                return "Не очень";
            case 3:
                return "Нормально";
            case 4:
                return "Хорошо";
            case 5:
                return "Отлично";
            default:
                return "";
        }
    }

    private String getToDay(){
        MyDate date =new MyDate();
        return date.getToDayForDB();
    }

    private void initButtonForChangeMode(){
        View view =getView();
        assert view != null;
        final Button changeComment = view.findViewById(R.id.createCommentSendCommentButton);
        changeComment.setText("изменить");
        final EditText comment = view.findViewById(R.id.createCommentEnterText);
        final RatingBar rated = view.findViewById(R.id.createCommentRating);
        final String dayOfChange = getToDay();
        changeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHelper.setComment(comment.getText().toString());
                createHelper.setDate(dayOfChange);
                createHelper.setRate(Math.round(rated.getProgress()/2));
                createHelper.upDateComment();
                upDateComment();
                createDialog();
            }
        });

    }

    private void upDateComment(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                createHelper.upDateComment();

            }
        });
        t.start();
    }
    private void initRateBar(){
        final RatingBar rated = getView().findViewById(R.id.createCommentRating);
        final TextView resultRate= getView().findViewById(R.id.createCommentResultRateTextView);
        rated.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                resultRate.setText(getResultTextRate(Math.round(rated.getProgress()/2)));
            }
        });
    }
    private void initButtonForCreateComment(){
        View view =getView();
        assert view != null;
        Button changeComment = view.findViewById(R.id.createCommentSendCommentButton);
        changeComment.setText("отправить");
        final EditText comment = view.findViewById(R.id.createCommentEnterText);

        final RatingBar rated = view.findViewById(R.id.createCommentRating);
        changeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHelper.setRate(Math.round(rated.getProgress()/2));
                createHelper.setDate(getToDay());
                createHelper.setComment(comment.getText().toString());
                addData();
                createDialog();

            }
        });


    }

    private void addData(){
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                createHelper.sendData();

            }
        });
        t.start();
    }
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Успешно")
                .setMessage("Спасибо за отзыв")

                .setCancelable(false)
                .setNegativeButton("Ок",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((MainActivity) getActivity())
                                        .getApp().getRouter()
                                        .remove("createCommentFragment");
                                ((MainActivity) getActivity())
                                        .getApp().getRouter()
                                        .reload("flowerPage");
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
