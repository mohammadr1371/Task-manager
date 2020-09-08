package com.example.homework12.Controler;

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

import com.example.homework12.R;
import com.example.homework12.Repository.TaskRepository;

import java.util.HashMap;

public class StarterFragment extends Fragment {

    public static final String NAME_OF_TASKS = "Name of tasks";
    public static final String NUMBER_OF_TASKS = "Number of tasks";
    public static final String USERNAME = "username";
    public static final String SING_IN_DIALOG_FRAGMENT = "Sing in Dialog Fragment";
    public static final String SIGN_UP_DIALOG_FRAGMENT = "sign up dialog fragment";
    public static final int SIGN_UP_DIALOG_REQUEST_CODE = 0;

    private HashMap<String, String> mUserNameMap = new HashMap<>();

    private String inputUsername;

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;

    private Button mButtonLogin;
    private Button mButtonSignUp;

    private TaskRepository mTaskRepository;

    public static StarterFragment newInstance() {
        StarterFragment fragment = new StarterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskRepository.getInstance();
        mUserNameMap.put("admin" , "5187");
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
                inputUsername = String.valueOf(mEditTextUserName.getText());
                String inputPasswordString = String.valueOf(mEditTextPassword.getText());
                if (inputUsername.length() == 0 || inputPasswordString.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter information", Toast.LENGTH_LONG).show();
                } else {
                    if (mUserNameMap.containsKey(inputUsername)) {
                        if (mUserNameMap.get(inputUsername).equals(inputPasswordString)) {
                            mTaskRepository.addArrayListOfUser(inputUsername);
                            Intent intent = new Intent(getActivity(), TaskViewPagerActivity.class);
                            intent.putExtra(USERNAME, inputUsername);
                            getActivity().startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Your password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                } else{
                    Toast.makeText(getActivity(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                }
            }
        }
    });

        mButtonSignUp.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
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
            mUserNameMap.put(username, password);
        }
    }
}