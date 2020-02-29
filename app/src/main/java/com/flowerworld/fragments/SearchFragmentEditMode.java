package com.flowerworld.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;

import java.util.Objects;

public class SearchFragmentEditMode extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment_enter_text_mode,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setToolBar();
        setFocus();
    }

    private void setFocus() {
        EditText yourEditText= Objects.requireNonNull(getView()).findViewById(R.id.search_fragment_toolbar_text_edit_text);
        yourEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);

    }

    public static SearchFragmentEditMode newInstance() {
        return new SearchFragmentEditMode();
    }

    private void setToolBar() {
        Toolbar toolbar = Objects.requireNonNull(getView()).findViewById(R.id.search_fragment_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        assert parentActivity != null;
        parentActivity.setSupportActionBar(toolbar);
        Button cancelButton = getView().findViewById(R.id.search_toolbar_cancel_button);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = getView().findViewById(R.id.search_fragment_toolbar_text_edit_text);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                Router.removeFragmentByTag(getContext(), Router.SEARCH_FRAGMENT_EDIT_MODE);
            }
        };
        toolbar.setNavigationOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }
}
