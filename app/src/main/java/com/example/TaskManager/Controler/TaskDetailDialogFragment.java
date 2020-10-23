package com.example.TaskManager.Controler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.TaskManager.Models.Task;
import com.example.TaskManager.R;
import com.example.TaskManager.Repository.AppRepository;
import com.example.TaskManager.utils.DateUtils;
import com.example.TaskManager.utils.PictureUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

public class TaskDetailDialogFragment extends DialogFragment {

    public static final String TASK_IN_DETAIL = "task in detail";
    public static final String DATE_PICKER_DIALOG_FRAGMENT = "date picker dialog fragment";
    public static final int DATE_PICKER_REQUEST_CODE = 0;
    public static final int TIME_PICKER_REQUEST_CODE = 1;
    public static final String TIME_PICKER_DIALOG_FRAGMENT = "time picker dialog fragment";
    public static final int IMAGE_CAPTURE_REQUEST_CODE = 2;
    public static final String TASK_MANAGER_FILE_PROVIDER = "com.example.TaskManager.fileProvider";

    private callBacks mCallBacks;

    private AppRepository mAppRepository;

    private Task mTaskInDetail;
    private File mPhotoFile;
    private TextView mTextViewTaskTitleInDetail;
    private TextView mTextViewTaskStateInDetail;
    private TextView mTextViewTaskDescriptionInDetail;

    private Button mButtonTaskDateInDetail;
    private Button mButtonTaskTimeInDetail;

    private ImageButton mImageButtonDelete;
    private ImageButton mImageButtonShare;
    private LinearLayout mLinearLayoutMain;
    private ImageView mImageViewPicture;

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
        mAppRepository = AppRepository.getInstance(getActivity());
        mPhotoFile = mAppRepository.getPhotoFile(mTaskInDetail.getImgFileName());
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

    public void findViews(View view) {
        mTextViewTaskTitleInDetail = view.findViewById(R.id.textView_detail_task_title);
        mTextViewTaskStateInDetail = view.findViewById(R.id.textView_detail_task_state);
        mTextViewTaskDescriptionInDetail = view.findViewById(R.id.textView_detail_task_description);
        mButtonTaskDateInDetail = view.findViewById(R.id.button_detail_date);
        mButtonTaskTimeInDetail = view.findViewById(R.id.button_detail_time);
        mImageButtonDelete = view.findViewById(R.id.button_delete_in_task_detail);
        mImageButtonShare = view.findViewById(R.id.button_share_in_task_detail);
        mLinearLayoutMain = view.findViewById(R.id.main_layout_in_detail);
        mImageViewPicture = view.findViewById(R.id.imageView_in_task_detail);
    }

    public void initView() {
        if (mTaskInDetail.getState().equals("Todo")) {
            mLinearLayoutMain.setBackground(getResources().getDrawable(R.drawable.detail_background));
        } else if (mTaskInDetail.getState().equals("Doing")) {
            mLinearLayoutMain.setBackground(getResources().getDrawable(R.drawable.todo_background));
        } else if (mTaskInDetail.getState().equals("Done")) {
            mLinearLayoutMain.setBackground(getResources().getDrawable(R.drawable.done_background));
        }
        mTextViewTaskTitleInDetail.setText(mTaskInDetail.getName());
        mTextViewTaskStateInDetail.setText(mTaskInDetail.getState());
        mTextViewTaskDescriptionInDetail.setText(mTaskInDetail.getDescription());
        mButtonTaskDateInDetail.setText(DateUtils.getDate(mTaskInDetail.getDate()));
        mButtonTaskTimeInDetail.setText(DateUtils.getTime(mTaskInDetail.getDate()));
        initImageView();
    }

    private void initImageView() {
        if(mPhotoFile == null || !mPhotoFile.exists()){
            mImageViewPicture.setImageDrawable(getResources().getDrawable(R.drawable.camera_icon_png));
        } else {
            Bitmap bitmap = PictureUtils.getScaledPhoto(mPhotoFile.getPath());
            mImageViewPicture.setImageBitmap(bitmap);
        }
    }


    public void setListeners() {
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
                mAppRepository.mITaskDatabaseDao().deleteTask(mTaskInDetail);
                mCallBacks.updateView();
                dismiss();
            }
        });

        mImageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Title: " + mTaskInDetail.getName() + " , Description: " + mTaskInDetail.getDescription() + " , State: " + mTaskInDetail.getState());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        mImageViewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri photoFileURI = FileProvider.getUriForFile(
                        getActivity()
                        , TASK_MANAGER_FILE_PROVIDER,
                        mPhotoFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoFileURI);
                List<ResolveInfo> activities = getActivity().getPackageManager().
                        queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : activities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            photoFileURI,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }

        if (requestCode == DATE_PICKER_REQUEST_CODE) {
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerDialogFragment.USER_SELECTED_DATE);
            mTaskInDetail.setDate(userSelectedDate);
            mButtonTaskDateInDetail.setText(DateUtils.getDate(mTaskInDetail.getDate()));
            mAppRepository.mITaskDatabaseDao().updateTask(mTaskInDetail);

        }

        if (requestCode == TIME_PICKER_REQUEST_CODE) {
            Date updateDateTime = (Date) data.getSerializableExtra(TimePickerDialogFragment.DATE_FROM_TIME_PICKER_DIALOG);
            mTaskInDetail.setDate(updateDateTime);
            mButtonTaskTimeInDetail.setText(DateUtils.getTime(mTaskInDetail.getDate()));
            mAppRepository.mITaskDatabaseDao().updateTask(mTaskInDetail);
        }

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            initImageView();
            getActivity().revokeUriPermission(FileProvider.getUriForFile(getActivity()
                    , TASK_MANAGER_FILE_PROVIDER
                    , mPhotoFile)
                    , Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public interface callBacks {
        public void updateView();
    }
}