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
import android.widget.DatePicker;

import com.example.homework12.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.transform.Result;


public class DatePickerDialogFragment extends DialogFragment {

    public static final String CURRENT_DATE = "current_date";
    public static final String USER_SELECTED_DATE = "user selected date";

    private Date mDate;

    private DatePicker mDatePicker;

    public static DatePickerDialogFragment newInstance(Date currentDate) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_DATE, currentDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = (Date) getArguments().getSerializable(DatePickerDialogFragment.CURRENT_DATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_date_picker, null);
        findViews(view);
        initDatePicker();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Date Picker")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date outPutDate = getDateFromDatePicker();
                        Intent intent = new Intent();
                        intent.putExtra(USER_SELECTED_DATE, outPutDate);
                        Fragment fragment = getTargetFragment();
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                });
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    private Date getDateFromDatePicker() {
        int yearSelected = mDatePicker.getYear();
        int monthSelected = mDatePicker.getMonth();
        int daySelected = mDatePicker.getDayOfMonth();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(yearSelected, monthSelected, daySelected);
        Date date = gregorianCalendar.getTime();
        return date;
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, month, dayOfMonth, null);
    }

    public void findViews(View view){
        mDatePicker = view.findViewById(R.id.date_picker);
    }
}