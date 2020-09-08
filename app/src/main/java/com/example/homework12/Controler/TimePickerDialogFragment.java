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
import android.widget.TimePicker;

import com.example.homework12.R;

import java.sql.Time;
import java.util.Date;


public class TimePickerDialogFragment extends DialogFragment {

    public static final String DATE_IN_TIME_PICKER_DIALOG = "date in time picker dialog";
    public static final String DATE_FROM_TIME_PICKER_DIALOG = "date from time picker dialog";

    private Date mDate;
    private long mTime;

    private TimePicker mTimePicker;

    public static TimePickerDialogFragment newInstance(Date currentDate) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATE_IN_TIME_PICKER_DIALOG, currentDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = (Date) getArguments().getSerializable(TimePickerDialogFragment.DATE_IN_TIME_PICKER_DIALOG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_time_picker_dialog, null);
        findViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTime = mTimePicker.getDrawingTime();
                        mDate.setTime(mTime);
                        Intent intent = new Intent();
                        intent.putExtra(DATE_FROM_TIME_PICKER_DIALOG, mDate);
                        getTargetFragment().onActivityResult(TaskDetailDialogFragment.TIME_PICKER_REQUEST_CODE, Activity.RESULT_OK, intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void findViews(View view){
        mTimePicker = view.findViewById(R.id.time_picker_view_in_dialog_fragment);
    }
}