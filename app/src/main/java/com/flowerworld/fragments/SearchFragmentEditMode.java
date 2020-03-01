package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.SearchEditModeConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.SearchItem;
import com.flowerworld.items.adapters.SearchItemAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragmentEditMode extends Fragment implements FragmentSetDataInterface {
    private Handler handler;
    private SearchEditModeConnection connection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment_enter_text_mode,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setProgressBar(false);
        setToolBar();
        setHandler();
        setFocus();
        setListeners();
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

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        connection = new SearchEditModeConnection();
        connection.setParent(this);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<SearchItem> searchItems = (ArrayList) msg.obj;
                bind(searchItems);
            }
        };
    }

    private void bind(ArrayList<SearchItem> searchItems) {
        System.out.println("started " + searchItems.size());
        setList(searchItems);
    }

    private void setList(ArrayList<SearchItem> searchItems) {
        View view = getView();
        assert view != null;
        RecyclerView searchesRecycleView = view.findViewById(R.id.search_fragment_edit_mode_recycle_view);
        SearchItemAdapter adapter = new SearchItemAdapter();
        adapter.setSearches(searchItems);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.divider)));
        searchesRecycleView.addItemDecoration(itemDecorator);
        searchesRecycleView.setAdapter(adapter);
        searchesRecycleView.setLayoutManager(manager);
        setProgressBar(false);
    }

    private void setListeners() {
        final EditText searchText = getView().findViewById(R.id.search_fragment_toolbar_text_edit_text);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createConnection(searchText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        searchText.addTextChangedListener(watcher);
    }


    private void createConnection(String text) {
        if (text.length() >= 3) {
            setProgressBar(true);
            connection.stopThread();
            connection.bind(text);

        }
        else {
            RecyclerView searchesRecycleView = Objects.requireNonNull(getView()).findViewById(R.id.search_fragment_edit_mode_recycle_view);
            setProgressBar(false);
            SearchItemAdapter adapter = new SearchItemAdapter();
            adapter.setSearches(new ArrayList<SearchItem>());
            searchesRecycleView.setAdapter(adapter);
        }

    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    private void setProgressBar(boolean hasVisible) {
        ProgressBar progressBar = Objects.requireNonNull(getView()).findViewById(R.id.search_fragment_edit_mode_progress_bar);
        if (hasVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }
}
