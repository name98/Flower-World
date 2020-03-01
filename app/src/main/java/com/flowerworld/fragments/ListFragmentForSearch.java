package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.ListForSearchConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.interfaces.ParentFragment;
import com.flowerworld.items.SearchItem;
import com.flowerworld.items.adapters.SearchItemAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ListFragmentForSearch extends Fragment implements FragmentSetDataInterface, ParentFragment {
    private Handler handler;
    public static final String KEY_FOR_TEXT = "text_key";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment_for_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHandler();
        assert getArguments() != null;
        String text = getArguments().getString(KEY_FOR_TEXT);
        createConnection(text);
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<SearchItem> searches = (ArrayList) msg.obj;
                bind(searches);
            }
        };
    }

    private void bind(ArrayList<SearchItem> searches) {
        setList(searches);
    }

    private void setList(ArrayList<SearchItem> searches) {
        View view = getView();
        assert view != null;
        RecyclerView searchesRecycleView = view.findViewById(R.id.search_fragment_edit_mode_recycle_view);
        SearchItemAdapter adapter = new SearchItemAdapter();
        adapter.setParent(this);
        adapter.setSearches(searches);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.divider)));
        searchesRecycleView.addItemDecoration(itemDecorator);
        searchesRecycleView.setAdapter(adapter);
        searchesRecycleView.setLayoutManager(manager);
    }

    private void createConnection(String text) {
        ListForSearchConnection connection = new ListForSearchConnection();
        connection.setParent(this);
        connection.bind(text);
    }

    public static ListFragmentForSearch newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(KEY_FOR_TEXT, text);
        ListFragmentForSearch fragment = new ListFragmentForSearch();
        fragment.setArguments(args);
        return fragment;
    }

    public void sendParentMessage(String type, String text) {
        ((SearchFragmentEditMode) getParentFragment()).sendMessageToParent(type, text);
    }
}
