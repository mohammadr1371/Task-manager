package com.example.homework12.Controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.homework12.Models.Task;
import com.example.homework12.R;
import com.example.homework12.Repository.TaskRepository;

import java.util.Date;

public class TaskDetailDialogFragment extends DialogFragment {

    public static final String TASK_IN_DETAIL = "task in detail";
    public static final String DATE_PICKER_DIALOG_FRAGMENT = "date picker dialog fragment";
    public static final int DATE_PICKER_REQUEST_CODE = 0;
    public static final int TIME_PICKER_REQUEST_CODE = 1;
    public static final String TIME_PICKER_DIALOG_FRAGMENT = "time picker dialog fragment";

    private callBacks mCallBacks;

    private TaskRepository mTaskRepository;

    private Task mTaskInDetail;

    private TextView mTextViewTaskTitleInDetail;
    private TextView mTextViewTaskStateInDetail;
    private TextView mTextViewTaskDescriptionInDetail;

    private Button mButtonTaskDateInDetail;
    private Button mButtonTaskTimeInDetail;

    private ImageButton mImageButtonDelete;

    public static TaskDetailDialogFragment newInstance(Task task) {
        TaskDetailDialogFragment fragment = new TaskDetailDialogFragment();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    LayoutInflater inflater = LayoutInflater.from(getActivity());
    View view = inflater.inflate(R.layout.fragment_task_detail, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(view)
                .setPositiveButton(android.R.string.ok, null);
        findViews(view);
        initView();
        setListeners();
        AlertDialog dialog = builder.create();
        return dialog;
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

    public void findViews(View view){
        mTextViewTaskTitleInDetail = view.findViewById(R.id.textView_detail_task_title);
        mTextViewTaskStateInDetail = view.findViewById(R.id.textView_detail_task_state);
        mTextViewTaskDescriptionInDetail = view.findViewById(R.id.textView_detail_task_description);
        mButtonTaskDateInDetail = view.findViewById(R.id.button_detail_date);
        mButtonTaskTimeInDetail = view.findViewById(R.id.button_detail_time);
        mImageButtonDelete = view.findViewById(R.id.button_delete_in_task_detail);
    }

    public void initView(){
        mTextViewTaskTitleInDetail.setText(mTaskInDetail.getName());
        mTextViewTaskStateInDetail.setText(mTaskInDetail.getState());
        mTextViewTaskDescriptionInDetail.setText(mTaskInDetail.getDescription());
        mButtonTaskDateInDetail.setText(String.valueOf(mTaskInDetail.getDate()));
        mButtonTaskTimeInDetail.setText(String.valueOf(mTaskInDetail.getDate().getTime()));
    }

    public void setListeners(){
        mButtonTaskDateInDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(mTaskInDetail.getDate());
                datePickerDialogFragment.setTargetFragment(TaskDetailDialogFragment.this, DATE_PICKER_REQUEST_CODE);
                datePickerDialogFragment.show(getFragmentManager(), DATE_PICKER_DIALOG_FRAGMENT);
            }
        });

        mButtonTaskTimeInDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(mTaskInDetail.getDate());
                timePickerDialogFragment.setTargetFragment(TaskDetailDialogFragment.this, TIME_PICKER_REQUEST_CODE);
                timePickerDialogFragment.show(getFragmentManager(), TIME_PICKER_DIALOG_FRAGMENT);
            }
        });

        mImageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskRepository.deleteTask(mTaskInDetail);
                mCallBacks.updateView();
                dismiss();
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

        if(requestCode == TIME_PICKER_REQUEST_CODE){
            Date updateDateTime = (Date) data.getSerializableExtra(TimePickerDialogFragment.DATE_FROM_TIME_PICKER_DIALOG);
            mButtonTaskDateInDetail.setText(updateDateTime.toString());
            mTaskInDetail.setDate(updateDateTime);
            mButtonTaskTimeInDetail.setText(String.valueOf(mTaskInDetail.getDate().getTime()));
            mTaskRepository.setTask(mTaskInDetail);
        }
    }

    public interface callBacks{
        public void updateView();
    }
}