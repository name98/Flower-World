package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.LoginConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.UserItem;

import java.util.Objects;

public class LoginFragment extends Fragment implements FragmentSetDataInterface {
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHandler();
        setViews();
        //new LoginFragmentUI(view);
    }

    private void setViews() {
        View view = getView();
        assert view != null;
        final TextView emailTextView= view.findViewById(R.id.login_fragment_email_head_text_view);
        final TextView passwordTextView= view.findViewById(R.id.login_fragment_password_head_text_view);
        TextView signInTextView = view.findViewById(R.id.login_fragment_open_sign_in_fragment_text_view);
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.addSignInFragment(getActivity());
            }
        });
        final Button enter = view.findViewById(R.id.loginFragmentEnterBt);
        enter.setEnabled(false);
        final EditText email = view.findViewById(R.id.log_in_fragment_email);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    emailTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    emailTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });


        final EditText pass = view.findViewById(R.id.log_in_fragment_password);

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    passwordTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    passwordTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pass.getText().toString().equals("") && !email.getText().toString().equals(""))
                    enter.setEnabled(true);
                else
                    enter.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        email.addTextChangedListener(watcher);
        pass.addTextChangedListener(watcher);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogIn(email.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void tryLogIn(String email, String userPassword) {
        LoginConnection connection = new LoginConnection();
        connection.setParent(this);
        connection.bind(email, userPassword);
    }

    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                int isTrue = msg.arg1;
                if (isTrue == 0)
                    setErrorDialog();
                else {
                    UserItem user = (UserItem) msg.obj;
                    setDatabase(user);
                }

            }
        };
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    private void setErrorDialog() {
        TextView errorAuthTextView = Objects.requireNonNull(getView()).findViewById(R.id.login_fragment_error_auth_text_view);
        errorAuthTextView.setVisibility(View.VISIBLE);

    }

    private void setDatabase(UserItem userItem) {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        helper.add(userItem);
        Objects.requireNonNull(getActivity()).recreate();
        Router.removeFragmentByTag(getActivity(), Router.LOGIN_FRAGMENT_TAG);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onDestroy() {
        ((MainActivity) Objects.requireNonNull(getActivity())).backPressed();
        super.onDestroy();

    }
}
