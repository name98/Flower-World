package com.flowerworld.items.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.items.CommentItem;

import java.util.ArrayList;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.Holder> {
    private ArrayList<CommentItem> comments;
    private int myCommentIndex;
    public boolean haveMy = false;

    public void setComments(ArrayList<CommentItem> comments) {
        notifyDataSetChanged();
        this.comments = comments;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CommentItem commentItem = comments.get(position);
        holder.setAuthor(commentItem.getAuthor());
        holder.setComment(commentItem.getComment());
        holder.setDate(commentItem.getDate());
        holder.setRate(commentItem.getRate());
        holder.setRateText(createItemHeadline(commentItem.getRate()));
        if (commentItem.isMy()) {
            holder.setLightGreenColorPane();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    private String createItemHeadline(int rate) {
        switch (rate) {
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

    class Holder extends RecyclerView.ViewHolder {
        private TextView comment;
        private TextView author;
        private TextView date;
        private TextView rateText;
        private RatingBar rate;
        private RelativeLayout pane;

        Holder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.commentItemCommentTextView);
            author = itemView.findViewById(R.id.commentItemAuthorTextView);
            date = itemView.findViewById(R.id.commentItemDateTextView);
            rate = itemView.findViewById(R.id.commentItemRatingBar);
            rateText = itemView.findViewById(R.id.commentItemCommentHeadline);
            pane = itemView.findViewById(R.id.commentItemRL);
        }

        void setComment(String comment) {
            this.comment.setText(comment);
        }

        void setAuthor(String author) {
            this.author.setText(author);
        }

        void setDate(String date) {
            this.date.setText(date);
        }

        void setRateText(String rateText) {
            this.rateText.setText(rateText);
        }

        void setRate(int rate) {
            this.rate.setRating(rate);
        }


        void setLightGreenColorPane() {
            pane.setBackgroundColor(Color.parseColor("#7AFFB3"));
        }
    }

    public CommentItem getMyComment() {
        return comments.get(myCommentIndex);
    }

    public void checkMyComment() {
        for (int i = 0; i < comments.size(); i++)
            if (comments.get(i).isMy()) {
                haveMy = true;
                myCommentIndex = i;
            }
    }
}
