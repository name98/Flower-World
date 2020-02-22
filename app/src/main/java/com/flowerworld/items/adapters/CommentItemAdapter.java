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
    public boolean haveMy = false;

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
        checkMyComment();
        notifyDataSetChanged();
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
        holder.bind(commentItem);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        Holder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(CommentItem comment) {
            setRateText(createItemHeadline(comment.getRate()));
            setRate(comment.getRate());
            setDate(comment.getDate());
            setAuthor(comment.getAuthor());
            setComment(comment.getComment());
            if (comment.isMy())
                setLightGreenColorPane();
        }

        void setComment(String comment) {
            TextView commentTextView = itemView.findViewById(R.id.commentItemCommentTextView);
            commentTextView.setText(comment);
        }

        void setAuthor(String author) {
            TextView authorTextView = itemView.findViewById(R.id.commentItemAuthorTextView);
            authorTextView.setText(author);
        }

        void setDate(String date) {
            TextView dateTextView = itemView.findViewById(R.id.commentItemDateTextView);
            dateTextView.setText(date);
        }

        void setRateText(String rateText) {
            TextView rateTextView = itemView.findViewById(R.id.commentItemCommentHeadline);
            rateTextView.setText(rateText);
        }

        void setRate(int rate) {
            RatingBar rateRatingBar = itemView.findViewById(R.id.commentItemRatingBar);
            rateRatingBar.setRating(rate);
        }

        void setLightGreenColorPane() {
            RelativeLayout paneRelativeLayout = itemView.findViewById(R.id.commentItemRL);
            paneRelativeLayout.setBackgroundColor(itemView.getResources().getColor(R.color.greenForMyComment));
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
    }

    public void checkMyComment() {
        for (int i = 0; i < comments.size(); i++)
            if (comments.get(i).isMy()) {
                haveMy = true;
            }
    }
}
