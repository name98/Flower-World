package com.flowerworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flowerworld.MainActivity;
import com.flowerworld.R;

import java.util.Objects;


public class WelcomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //setAnimations();
        //setViews();
        initButtons();
    }

    private void initButtons() {
        View parent = getView();
        assert parent != null;
        Button signInButton = parent.findViewById(R.id.welcome_fragment_sign_in_button);
        Button loginButton = parent.findViewById(R.id.welcome_fragment_login_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addSignInFragment(getContext());
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addLoginFragment(getContext());
                //setAnimations();
            }
        });
    }

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onDestroy() {
        ((MainActivity) Objects.requireNonNull(getActivity())).backPressed();
        super.onDestroy();
    }
}
