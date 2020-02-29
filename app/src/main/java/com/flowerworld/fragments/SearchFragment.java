package com.flowerworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;

import java.util.Objects;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);

    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setListener();
    }

    private void setListener() {
        FrameLayout textEditFrameLayout = Objects.requireNonNull(getView()).findViewById(R.id.search_text_pane_frame_layout);
        textEditFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addSearchFragmentEditMode(getContext());
            }
        });
    }
}
