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

import com.flowerworld.R;
import com.flowerworld.connections.CommentConnection;
import com.flowerworld.connections.DataBaseHelper;

import com.flowerworld.interfaces.CommentFragmentDataInterface;
import com.flowerworld.items.CommentItem;
import com.flowerworld.models.MyDate;

import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.N)
public class CreateCommentFragment extends Fragment implements CommentFragmentDataInterface {
    private Handler handlerForGetComment;
    private Handler handlerForSendComment;
    private final static String PRODUCT_ID_KEY = "id_key";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_comment_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Router.addProgressFragment(getContext());
        setHandler();
        int productId = getArguments().getInt(PRODUCT_ID_KEY);
        DataBaseHelper dBHelper = new DataBaseHelper(this.getContext());
        String myId = dBHelper.getKey();
        CommentConnection connection = new CommentConnection();
        connection.setParent(this);
        connection.bind(String.valueOf(productId), myId);


    }


    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handlerForGetComment = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                CommentItem comment = (CommentItem) msg.obj;
                bind(comment);
            }
        };
        handlerForSendComment = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                createDialog();
            }
        };
    }

    public void bind(CommentItem comment) {
        if (comment.isMy())
            setViewsChangeMode(comment);
        else
            setViewsCreateMode(comment);
        Router.removeProgressFragment(getContext());
    }

    private void setViewsCreateMode(CommentItem comment) {
        View view = getView();
        assert view != null;
        TextView textRate = Objects.requireNonNull(view).findViewById(R.id.createCommentResultRateTextView);
        textRate.setText(getResultTextRate(5));
        setButton(true);
    }

    private void setButton (boolean isCreateMode) {
        View view = getView();
        DataBaseHelper dBHelper = new DataBaseHelper(this.getContext());
        final String myId = dBHelper.getKey();
        assert view != null;
        Button createCommentButton = view.findViewById(R.id.createCommentSendCommentButton);
        final EditText commentEditText = view.findViewById(R.id.createCommentEnterText);
        final RatingBar rateProductRatingBar = view.findViewById(R.id.createCommentRating);
        final CommentFragmentDataInterface parent = this;
        final CommentItem comment = new CommentItem();
        if (isCreateMode) {
            createCommentButton.setText("отправить");
            comment.setMy(false);

        }
        else {
            comment.setMy(true);
            createCommentButton.setText("Изменить");
        }
        createCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.setRate(Math.round(rateProductRatingBar.getProgress()/2));
                comment.setDate(getToDay());
                comment.setComment(commentEditText.getText().toString());
                CommentConnection connection = new CommentConnection();
                connection.setParent(parent);
                int productId = getArguments().getInt(PRODUCT_ID_KEY);
                connection.unBind(comment, String.valueOf(productId),myId);
            }
        });
    }








    private void setViewsChangeMode(CommentItem comment) {
        setButton(false);
        View view = getView();
        assert view != null;
        EditText commentEditText = view.findViewById(R.id.createCommentEnterText);
        commentEditText.setText(comment.getComment());
        RatingBar rated = view.findViewById(R.id.createCommentRating);
        rated.setRating(comment.getRate()*2);
        TextView textRate = view.findViewById(R.id.createCommentResultRateTextView);
        textRate.setText(getResultTextRate(comment.getRate()));
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







    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Успешно")
                .setMessage("Спасибо за отзыв")
                .setCancelable(false)
                .setNegativeButton("Ок",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Router.removeCreateCommentFragment(getContext());
                                Router.reloadProductFragment(getContext());
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static CreateCommentFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID_KEY,id);
        CreateCommentFragment fragment = new CreateCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void sendMessageForGetHandler(Message msg) {
        handlerForGetComment.sendMessage(msg);
    }

    @Override
    public void sendMessageForSendHandler(Message msg) {
        handlerForSendComment.sendMessage(msg);
    }


}
