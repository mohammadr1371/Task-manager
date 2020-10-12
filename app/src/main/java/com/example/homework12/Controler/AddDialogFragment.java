package com.example.homework12.Controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.homework12.Models.Task;
import com.example.homework12.R;
import com.example.homework12.Repository.AppRepository;

import java.util.Date;

public class AddDialogFragment extends DialogFragment {

    public static final String USERNAME_IN_ADD_FRAGMENT = "username in add fragment";
    public static final int DATE_PICKER_REQUEST_CODE = 1;
    public static final String DATE_PICKER_DIALOG_FRAGMENT = "date picker dialog fragment";

    private callBacks mCallBacks;

    private Task mTask;

    private String mUsername;

    private AppRepository mAppRepository;

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;

    private RadioButton mRadioButtonTodo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;

    private Button mButtonAddData;
    private Button mButtonAddTask;

    public static AddDialogFragment newInstance(String username) {
        AddDialogFragment fragment = new AddDialogFragment();
        Bundle arg = new Bundle();
        arg.putString(USERNAME_IN_ADD_FRAGMENT, username);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppRepository = AppRepository.getInstance();
        mUsername = getArguments().getString(AddDialogFragment.USERNAME_IN_ADD_FRAGMENT);
        mTask = new Task("task", 0, "description");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        findViews(view);
        setListeners();
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void findViews(View view){
        mEditTextTitle = view.findViewById(R.id.editText_title_in_add);
        mEditTextDescription = view.findViewById(R.id.editText_description);
        mRadioButtonTodo = view.findViewById(R.id.radioButton_todo);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_doing);
        mRadioButtonDone = view.findViewById(R.id.radioButton_done);
        mButtonAddData = view.findViewById(R.id.button_data_in_add);
        mButtonAddTask = view.findViewById(R.id.button_add_task);
    }



    public void setListeners(){

        mButtonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((mEditTextTitle.getText().toString().length() == 0)||
                        (mRadioButtonDone.isChecked()== false && mRadioButtonDoing.isChecked()==false && mRadioButtonTodo.isChecked()==false)
                || mEditTextDescription.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "Please fill task states", Toast.LENGTH_LONG).show();
                } else {
                    if(mRadioButtonTodo.isChecked()){
                        mTask.setName(mEditTextTitle.getText().toString());
                        mTask.setState("Todo");
                        mTask.setDescription(mEditTextDescription.getText().toString());
                        mAppRepository.insertTodoTaskList(mTask, mUsername);
                    } else if(mRadioButtonDoing.isChecked()){
                        mTask.setName(mEditTextTitle.getText().toString());
                        mTask.setState("Doing");
                        mTask.setDescription(mEditTextDescription.getText().toString());
                        mAppRepository.insertDoingTaskList(mTask, mUsername);
                    } else if(mRadioButtonDone.isChecked()){
                        mTask.setName(mEditTextTitle.getText().toString());
                        mTask.setState("Done");
                        mTask.setDescription(mEditTextDescription.getText().toString());
                        mAppRepository.insertDoneTaskList(mTask, mUsername);
                    }
                    if(mCallBacks instanceof TaskViewPagerActivity) {
                        mCallBacks.updateView();
                    }
                    dismiss();
                }
            }
        });

        mButtonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mTask.getDate());
                datePickerDialogFragment.setTargetFragment(AddDialogFragment.this, DATE_PICKER_REQUEST_CODE);
                datePickerDialogFragment.show(getFragmentManager(), DATE_PICKER_DIALOG_FRAGMENT);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBacks = (callBacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Toast.makeText(getActivity(), "Your task added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }

        if (requestCode == DATE_PICKER_REQUEST_CODE) {
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerDialogFragment.USER_SELECTED_DATE);
            mTask.setDate(userSelectedDate);
            mAppRepository.setTask(mTask);
        }
    }

    public interface callBacks{
        public void updateView();
    }
}