package com.example.TaskManager.Controler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.TaskManager.Models.User;
import com.example.TaskManager.R;
import com.example.TaskManager.Repository.AppRepository;

import java.util.List;

public class StarterFragment extends Fragment {

    public static final String SIGN_UP_DIALOG_FRAGMENT = "sign up dialog fragment";
    public static final int SIGN_UP_DIALOG_REQUEST_CODE = 0;
    public static final String USERNAME_IN_STARTER = "username in starter";

    private String inputUsername;

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;

    private Button mButtonLogin;
    private Button mButtonSignUp;

    private AppRepository mAppRepository;

    public static StarterFragment newInstance() {
        StarterFragment fragment = new StarterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppRepository = AppRepository.getInstance(getActivity());
//        mUserNameMap.put("admin" , "5187");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starter, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    public void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.editText_user_name);
        mEditTextPassword = view.findViewById(R.id.editText_password);
        mButtonLogin = view.findViewById(R.id.button_login);
        mButtonSignUp = view.findViewById(R.id.button_sing_up);
    }

    public void setListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> usersList = mAppRepository.mITaskDatabaseDao().getUserList();
                inputUsername = String.valueOf(mEditTextUserName.getText());
                String inputPasswordString = String.valueOf(mEditTextPassword.getText());
                if (inputUsername.length() == 0 || inputPasswordString.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter information", Toast.LENGTH_LONG).show();
                } else {
                    if (usersList.size() == 0) {
                        Toast.makeText(getActivity(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                    } else {
                        if (searchUser(inputUsername) != null) {
                            User user = searchUser(inputUsername);
                            if (!user.getPassword().equalsIgnoreCase(inputPasswordString)) {
                                Toast.makeText(getActivity(), "Your password is incorrect", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(getActivity(), TaskViewPagerActivity.class);
                                intent.putExtra(USERNAME_IN_STARTER, inputUsername);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpDialogFragment signUpDialogFragment = SignUpDialogFragment.newInstance();
                signUpDialogFragment.setTargetFragment(StarterFragment.this, SIGN_UP_DIALOG_REQUEST_CODE);
                signUpDialogFragment.show(getFragmentManager(), SIGN_UP_DIALOG_FRAGMENT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED || data == null) {
            return;
        }

        if (requestCode == StarterFragment.SIGN_UP_DIALOG_REQUEST_CODE) {
            String username = data.getStringExtra(SignUpDialogFragment.USERNAME_IN_DIALOG);
            String password = data.getStringExtra(SignUpDialogFragment.PASSWORD_IN_DIALOG);
            User user = new User(username, password);
            mAppRepository.mITaskDatabaseDao().insertUser(user);
        }
    }

    public User searchUser(String username) {
        List<User> users = mAppRepository.mITaskDatabaseDao().getUserList();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
}