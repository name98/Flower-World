package com.flowerworld.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;
import com.flowerworld.connections.LoginConnection;
import com.flowerworld.connections.SignInConnection;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.UserItem;
import com.flowerworld.models.UserData;

import java.util.Objects;

public class SignInFragment extends Fragment implements FragmentSetDataInterface {

    private static final String ERROR_PASSWORD = "Пароли должны содержать не менее восьми символов, в том числе не менее 1 буквы и 1 цифры.";
    private static final String ERROR_EMAIL = "Не дейсвительный адрес эл. почты";
    private static final String ERROR_REPEAT_PASSWORD = "Пароли не совпадают";
    private static final String EMPTY_PHONE = "Пожалуйста, укажите телефон";
    private static final String EMPTY_PASSWORD = "Пожалуйста, укажите новый пароль";
    private static final String EMPTY_FIRST_NAME = "Пожалуйста, укажите Имя";
    private static final String EMPTY_SECOND_NAME = "Пожалуйста, укажите Фамилию";
    private static final String EMPTY_EMAIL = "Пожалуйста, укажите адрес эл. почты";
    private static final String ERROR_REPEAT_EMAIL = "Пользователь с таким эл. адресом уже существует";
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signin_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHandler();
        createViews();
        setListeners();
    }

    private boolean isCorrectValues() {
        View parent = getView();
        assert parent != null;
        EditText firstNameEditText = parent.findViewById(R.id.sing_in_fragment_first_name);
        if (firstNameEditText.getText().toString().isEmpty()) {
            showErrorMessage(EMPTY_FIRST_NAME);
            return false;
        }
        EditText secondNameEditText = parent.findViewById(R.id.sing_in_fragment_second_name);
        if (secondNameEditText.getText().toString().isEmpty()) {
            showErrorMessage(EMPTY_SECOND_NAME);
            return false;
        }
        EditText telephoneEditText = parent.findViewById(R.id.sign_in_fragment_telephone);
        if (telephoneEditText.getText().toString().isEmpty()) {
            showErrorMessage(EMPTY_PHONE);
            return false;
        }
        EditText emailEditText = parent.findViewById(R.id.sign_in_fragment_email);
        if (emailEditText.getText().toString().isEmpty()) {
            showErrorMessage(EMPTY_EMAIL);
            return false;
        }
        EditText passwordEditText = parent.findViewById(R.id.sign_in_password);
        if (passwordEditText.getText().toString().isEmpty()) {
            showErrorMessage(EMPTY_PASSWORD);
            return false;
        }
        EditText repeatPasswordEditText = parent.findViewById(R.id.sign_in_fragment_repeat_password);
        if (repeatPasswordEditText.getText().toString().isEmpty() || !repeatPasswordEditText.getText()
                .toString()
                .equals(passwordEditText.getText().toString())) {
            showErrorMessage(ERROR_REPEAT_PASSWORD);
            return false;
        }
        return true;
    }

    private boolean isCorrectEmail() {
        View parent = getView();
        assert parent != null;
        EditText emailEditText = parent.findViewById(R.id.sign_in_fragment_email);
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(emailEditText.getText().toString());
        boolean isValid = m.matches();
        if (!isValid)
            showErrorMessage(ERROR_EMAIL);
        return m.matches();
    }

    private boolean isCorrectPassword() {
        View parent = getView();
        assert parent != null;
        EditText passwordEditText = parent.findViewById(R.id.sign_in_password);

        boolean isValid = isValidatePassword(passwordEditText.getText().toString());
        if (!isValid)
            showErrorMessage(ERROR_PASSWORD);
        return isValid;
    }

    private void showErrorMessage(String message) {
        TextView errorTextView = Objects.requireNonNull(getView()).findViewById(R.id.sign_in_fragment_error_message_text_view);
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
    }

    private void createViews() {
        View parent = getView();
        assert parent != null;
        TextView errorTextView = parent.findViewById(R.id.sign_in_fragment_error_message_text_view);
        EditText firstNameEditText = parent.findViewById(R.id.sing_in_fragment_first_name);
        EditText secondNameEditText = parent.findViewById(R.id.sing_in_fragment_second_name);
        EditText patNameEditText = parent.findViewById(R.id.sing_in_fragment_patr_name);
        EditText telephoneEditText = parent.findViewById(R.id.sign_in_fragment_telephone);
        EditText emailEditText = parent.findViewById(R.id.sign_in_fragment_email);
        EditText passwordEditText = parent.findViewById(R.id.sign_in_password);
        EditText repeatPasswordEditText = parent.findViewById(R.id.sign_in_fragment_repeat_password);
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        errorTextView.setVisibility(View.GONE);
        firstNameEditText.setText("");
        secondNameEditText.setText("");
        patNameEditText.setText("");
        telephoneEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        repeatPasswordEditText.setText("");
        firstNameEditText.requestFocus();
        assert imm != null;
        imm.showSoftInput(firstNameEditText, InputMethodManager.SHOW_IMPLICIT);
        setButtonListener();
    }

    private void setButtonListener() {
        Button addNewUser = Objects.requireNonNull(getView()).findViewById(R.id.sign_in_fragment_add_new_user_button);
        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectValues() && isCorrectEmail() && isCorrectPassword())
                    sendMessageToDB();
            }
        });
    }

    private void sendMessageToDB() {
        UserData userData = createData();
        SignInConnection connection = new SignInConnection();
        connection.setParent(this);
        connection.bind(userData);
    }

    private UserData createData() {
        View parent = getView();
        UserData userData = new UserData();
        assert parent != null;
        EditText firstNameEditText = parent.findViewById(R.id.sing_in_fragment_first_name);
        EditText secondNameEditText = parent.findViewById(R.id.sing_in_fragment_second_name);
        EditText patNameEditText = parent.findViewById(R.id.sing_in_fragment_patr_name);
        EditText telephoneEditText = parent.findViewById(R.id.sign_in_fragment_telephone);
        EditText emailEditText = parent.findViewById(R.id.sign_in_fragment_email);
        EditText passwordEditText = parent.findViewById(R.id.sign_in_password);
        userData.setEmail(emailEditText.getText().toString());
        userData.setfName(firstNameEditText.getText().toString());
        userData.setPassword(passwordEditText.getText().toString());
        userData.setPhone(telephoneEditText.getText().toString());
        userData.setsName(secondNameEditText.getText().toString());
        if (!patNameEditText.getText().toString().isEmpty())
            userData.setPatName(patNameEditText.getText().toString());
        return userData;
    }

    @Override
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    public void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                boolean isAdded;
                if (msg.what == 1) {
                    isAdded = (boolean) msg.obj;
                    if (isAdded) {
                        tryLogin();
                    } else
                        showErrorMessage(ERROR_REPEAT_EMAIL);
                }
                if (msg.what == 2) {
                    goToHome((UserItem) msg.obj);
                }
            }
        };
    }

    private void goToHome(UserItem userItem) {
        DataBaseHelper helper = new DataBaseHelper(getContext());
        helper.add(userItem);
        Objects.requireNonNull(getActivity()).recreate();
        Router.removeFragmentByTag(getActivity(), Router.SIGN_IN_FRAGMENT_TAG);
        Router.removeFragmentByTag(getActivity(), Router.LOGIN_FRAGMENT_TAG);
    }

    private void tryLogin() {
        UserData userData = createData();
        LoginConnection connection = new LoginConnection();
        connection.setParent(this);
        connection.bind(userData.getEmail(), userData.getPassword());
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    private boolean isValidatePassword(String password){
        boolean valid = true;

        if (password.length() > 15 || password.length() < 8)
        {
            System.out.println("Password should be less than 15 and more than 8 characters in length.");
            valid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            System.out.println("Password should contain atleast one upper case alphabet");
            valid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            System.out.println("Password should contain atleast one lower case alphabet");
            valid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            System.out.println("Password should contain atleast one number.");
            valid = false;
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars ))
        {
            System.out.println("Password should contain atleast one special character");
            valid = false;
        }
        if (valid)
        {
            System.out.println("Password is valid.");
        }
        return valid;
    }

    private void setListeners() {
        View parent = getView();
        assert parent != null;
        final EditText firstNameEditText = parent.findViewById(R.id.sing_in_fragment_first_name);
        EditText secondNameEditText = parent.findViewById(R.id.sing_in_fragment_second_name);
        EditText patNameEditText = parent.findViewById(R.id.sing_in_fragment_patr_name);
        EditText telephoneEditText = parent.findViewById(R.id.sign_in_fragment_telephone);
        EditText emailEditText = parent.findViewById(R.id.sign_in_fragment_email);
        EditText passwordEditText = parent.findViewById(R.id.sign_in_password);
        EditText repeatPasswordEditText = parent.findViewById(R.id.sign_in_fragment_repeat_password);

        final TextView firstNameTextView = parent.findViewById(R.id.sign_in_fragment_head_fname_text_view);
        final TextView secondNameTextView = parent.findViewById(R.id.sign_in_fragment_head_sname_text_view);
        final TextView patNameTextView = parent.findViewById(R.id.sign_in_fragment_head_pname_text_view);
        final TextView emailTextView = parent.findViewById(R.id.sign_in_fragment_head_email_text_view);
        final TextView phoneTextView = parent.findViewById(R.id.sign_in_fragment_head_phone_text_view);
        final TextView passwordTextView = parent.findViewById(R.id.sign_in_fragment_head_password_text_view);
        final TextView rpasswordTextView = parent.findViewById(R.id.sign_in_fragment_head_rpassword_text_view);
        firstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    firstNameTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    firstNameTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        secondNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    secondNameTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    secondNameTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        patNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    patNameTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    patNameTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    emailTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    emailTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        telephoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    phoneTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    phoneTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    passwordTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    passwordTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });
        repeatPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    rpasswordTextView.setTextColor(getResources().getColor(R.color.active_red));
                else
                    rpasswordTextView.setTextColor(getResources().getColor(R.color.text_light_grey));
            }
        });



    }
}
