package com.example.homework12.Controler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.homework12.Models.Task;
import com.example.homework12.R;
import com.example.homework12.Repository.TaskRepository;

public class AddFragment extends Fragment {

    public static final String USERNAME_IN_ADD_FRAGMENT = "username in add fragment";
    private String mUsername;

    private TaskRepository mTaskRepository;

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;

    private RadioButton mRadioButtonTodo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;

    private Button mButtonAddData;
    private Button mButtonAddTask;

    public static AddFragment newInstance(String username) {
        AddFragment fragment = new AddFragment();
        Bundle arg = new Bundle();
        arg.putString(USERNAME_IN_ADD_FRAGMENT, username);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskRepository.getInstance();
        mUsername = getArguments().getString(AddFragment.USERNAME_IN_ADD_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        findViews(view);
        setListeners();
        return view;
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
                        mTaskRepository.insertTodoTaskList(mEditTextTitle.getText().toString(), mUsername, mEditTextDescription.getText().toString());
                    } else if(mRadioButtonDoing.isChecked()){
                        mTaskRepository.insertDoingTaskList(mEditTextTitle.getText().toString(), mUsername, mEditTextDescription.getText().toString());
                    } else if(mRadioButtonDone.isChecked()){
                        mTaskRepository.insertDoneTaskList(mEditTextTitle.getText().toString(), mUsername, mEditTextDescription.getText().toString());
                    }
                    getActivity().finish();
                }
            }
        });
    }
}