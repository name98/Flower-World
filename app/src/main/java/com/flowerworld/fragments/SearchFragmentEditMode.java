package com.flowerworld.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;

import java.util.Objects;

public class SearchFragmentEditMode extends Fragment {
    public static final String FLOWER_FRAGMENT = "flower_fragment";
    public static final String GRID_FRAGMENT = "grid_fragment";
    public static final String LIST_FRAGMENT = "list_fragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment_enter_text_mode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setToolBar();
        setFocus(true);
        setListeners();
    }

    private void setFocus(boolean hasFocus) {
        EditText searchPaneEditText = Objects.requireNonNull(getView()).findViewById(R.id.search_fragment_toolbar_text_edit_text);
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        if (hasFocus) {
            searchPaneEditText.requestFocus();
            imm.showSoftInput(searchPaneEditText, InputMethodManager.SHOW_IMPLICIT);
        }
        else {
            searchPaneEditText.clearFocus();
            imm.hideSoftInputFromWindow(searchPaneEditText.getWindowToken(), 0);
        }
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
                EditText searchText = Objects.requireNonNull(getView()).findViewById(R.id.search_fragment_toolbar_text_edit_text);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                Router.removeFragmentByTag(getContext(), Router.SEARCH_FRAGMENT_EDIT_MODE);
            }
        };
        toolbar.setNavigationOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }

    private void setListeners() {
        final EditText searchText = getView().findViewById(R.id.search_fragment_toolbar_text_edit_text);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switchChild(LIST_FRAGMENT, searchText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        searchText.addTextChangedListener(watcher);
    }

    private void switchChild(String type, String text) {
        if (text.length() < 3 && !type.equals(FLOWER_FRAGMENT))
            return;
        if (type.equals(LIST_FRAGMENT)) {
            SearchFragmentRouter.reloadListFragment(this, text);
            SearchFragmentRouter.showFragmentByTag(this, SearchFragmentRouter.LIST_FRAGMENT_TAG);
            SearchFragmentRouter.hideFragmentByTag(this, SearchFragmentRouter.GRID_FRAGMENT_SEARCH_TAG);
        }

        if (type.equals(GRID_FRAGMENT)) {
            SearchFragmentRouter.reloadGridFragment(this, text);
            SearchFragmentRouter.hideFragmentByTag(this, SearchFragmentRouter.LIST_FRAGMENT_TAG);
            SearchFragmentRouter.showFragmentByTag(this, SearchFragmentRouter.GRID_FRAGMENT_SEARCH_TAG);
        }

        if (type.equals(FLOWER_FRAGMENT)) {
            Router.addFlowerFragment(getContext(), Integer.valueOf(text));

        }
    }

    void sendMessageToParent (String type, String text) {
        switchChild(type, text);
        setFocus(false);
    }


}
