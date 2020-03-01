package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flowerworld.R;
import com.flowerworld.connections.SearchConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.adapters.SearchesListAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment implements FragmentSetDataInterface {
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHandler();
        setListener();
        SearchConnection connection = new SearchConnection();
        connection.setParent(this);
        connection.bind();
    }

    private void setListener() {
        FrameLayout textEditFrameLayout = Objects.requireNonNull(getView()).findViewById(R.id.search_text_pane_frame_layout);
        textEditFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addSearchFragmentEditMode(getContext(), "");
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                ArrayList<String> searches = (ArrayList) msg.obj;
                setList(searches);
            }
        };
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    private void setList(ArrayList<String> searches) {
        View view = getView();
        assert view != null;
        RecyclerView popularSearchesRecycleView = view.findViewById(R.id.search_fragment_popular_searches_recycle_view);
        SearchesListAdapter adapter = new SearchesListAdapter();
        adapter.setPopularSearches(searches);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getActivity().getDrawable(R.drawable.divider));
        popularSearchesRecycleView.addItemDecoration(itemDecorator);
        popularSearchesRecycleView.setAdapter(adapter);
        popularSearchesRecycleView.setLayoutManager(manager);
    }


}
