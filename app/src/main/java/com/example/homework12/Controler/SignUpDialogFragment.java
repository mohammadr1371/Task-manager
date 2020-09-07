package com.example.homework12.Controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework12.R;


public class SignUpDialogFragment extends DialogFragment {

    public static final String USERNAME_IN_DIALOG = "username";
    public static final String PASSWORD_IN_DIALOG = "password";
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private EditText mEditTextPassword2;
    private Button mSignUp;
    private Button mCancel;

    public static SignUpDialogFragment newInstance() {
        SignUpDialogFragment fragment = new SignUpDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_sign_up_dialog, null);
        findView(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(view);
        setListeners();
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    private void findView(View view) {
        mEditTextUsername = view.findViewById(R.id.editText_username_in_dialog);
        mEditTextPassword = view.findViewById(R.id.editText_password_in_dialog);
        mEditTextPassword2 = view.findViewById(R.id.editText_password2_in_dialog);
        mSignUp = view.findViewById(R.id.button_sign_up_in_sign_up_dialog);
        mCancel = view.findViewById(R.id.button_cancel_in_sign_up_dialog);
    }

    private void setListeners(){
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEditTextUsername.getText().toString();
                String password = mEditTextPassword.getText().toString();
                String password2 = mEditTextPassword2.getText().toString();
                if(username.length() == 0 || password.length() == 0 || password2.length() == 0){
                    Toast.makeText(getActivity(), "Please fill blank state", Toast.LENGTH_SHORT).show();
                } else if (!(password.equals(password2))){
                    Toast.makeText(getActivity(), "Your passwords do not match to gather", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Your sign up successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra(USERNAME_IN_DIALOG, username);
                    intent.putExtra(PASSWORD_IN_DIALOG, password);
                    getTargetFragment().onActivityResult(StarterFragment.SIGN_UP_DIALOG_REQUEST_CODE,
                            Activity.RESULT_OK, intent);
                    dismiss();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}