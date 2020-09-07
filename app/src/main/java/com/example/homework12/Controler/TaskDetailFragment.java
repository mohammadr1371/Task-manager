package com.example.homework12.Controler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework12.Models.Task;
import com.example.homework12.R;
import com.example.homework12.Repository.TaskRepository;

import java.util.Date;

public class TaskDetailFragment extends Fragment {

    public static final String TASK_IN_DETAIL = "task in detail";
    public static final String DATE_PICKER_DIALOG_FRAGMENT = "date picker dialog fragment";
    public static final int DATE_PICKER_REQUEST_CODE = 0;

    private TaskRepository mTaskRepository;

    private Task mTaskInDetail;

    private TextView mTextViewTaskTitleInDetail;
    private TextView mTextViewTaskStateInDetail;
    private TextView mTextViewTaskDescriptionInDetail;

    private Button mButtonTaskDateInDetail;
    private Button mButtonTaskTimeInDetail;

    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(TASK_IN_DETAIL, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskInDetail = (Task) getArguments().getSerializable(TASK_IN_DETAIL);
        mTaskRepository = TaskRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        findViews(view);
        initView();
        setListeners();
        return view;
    }

    public void findViews(View view){
        mTextViewTaskTitleInDetail = view.findViewById(R.id.textView_detail_task_title);
        mTextViewTaskStateInDetail = view.findViewById(R.id.textView_detail_task_state);
        mTextViewTaskDescriptionInDetail = view.findViewById(R.id.textView_detail_task_description);
        mButtonTaskDateInDetail = view.findViewById(R.id.button_detail_date);
        mButtonTaskTimeInDetail = view.findViewById(R.id.button_detail_time);
    }

    public void initView(){
        mTextViewTaskTitleInDetail.setText(mTaskInDetail.getName());
        mTextViewTaskStateInDetail.setText(mTaskInDetail.getState());
        mTextViewTaskDescriptionInDetail.setText(mTaskInDetail.getDescription());
        mButtonTaskDateInDetail.setText(String.valueOf(mTaskInDetail.getDate()));
    }

    public void setListeners(){
        mButtonTaskDateInDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mTaskInDetail.getDate());
                datePickerDialogFragment.setTargetFragment(TaskDetailFragment.this, DATE_PICKER_REQUEST_CODE);
                datePickerDialogFragment.show(getFragmentManager(), DATE_PICKER_DIALOG_FRAGMENT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }

        if(requestCode == DATE_PICKER_REQUEST_CODE){
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerDialogFragment.USER_SELECTED_DATE);
            mButtonTaskDateInDetail.setText(userSelectedDate.toString());
            mTaskInDetail.setDate(userSelectedDate);
            mTaskRepository.setTask(mTaskInDetail);

        }
    }
}