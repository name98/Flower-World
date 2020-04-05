package com.flowerworld.items.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.fragments.Router;

import java.util.ArrayList;

public class SearchesListAdapter extends RecyclerView.Adapter<SearchesListAdapter.SearcherHolder> {
    private ArrayList<String> popularSearches = new ArrayList<>();

    public void setPopularSearches(ArrayList<String> popularSearches) {
        this.popularSearches = popularSearches;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearcherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent, false);
        return new SearcherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearcherHolder holder, int position) {
        String inquiry = popularSearches.get(position);
        holder.bind(inquiry);
    }

    @Override
    public int getItemCount() {
        return popularSearches.size();
    }

    static class SearcherHolder extends RecyclerView.ViewHolder {
        private TextView inquiryTextView;

        SearcherHolder(@NonNull View itemView) {
            super(itemView);
            inquiryTextView = itemView.findViewById(R.id.search_fragment_inquiry_text_view);
        }

        private void setInquiryText(String text) {
            inquiryTextView.setText(text);
        }

        private void setListener(final String tag) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.addSearchFragmentEditMode(itemView.getContext(), tag);
                }
            });
        }

        void bind(String text) {
            setInquiryText(text);
            setListener(text);
        }
    }
}
