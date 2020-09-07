package com.example.homework12.Controler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homework12.Models.Task;
import com.example.homework12.R;

import java.util.ArrayList;


public class TaskListFragment extends Fragment {

    public static final String TASK_IN_TASK_LIST_FRAGMENT = "task in task list fragment";

    private RecyclerView mTaskRecyclerView;
    private ArrayList<Task> mTaskArrayList;
    private LinearLayout mEmptyListIcon;

    public static TaskListFragment newInstance(ArrayList<Task> taskArrayList) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(TASK_IN_TASK_LIST_FRAGMENT, taskArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskArrayList = (ArrayList<Task>) getArguments().getSerializable(TASK_IN_TASK_LIST_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        setVisibility();
        intView();
        return view;
    }

    private void setVisibility() {
        if(mTaskArrayList.size() == 0){
            mEmptyListIcon.setVisibility(View.VISIBLE);
            mTaskRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyListIcon.setVisibility(View.GONE);
            mTaskRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void findViews(View view) {
        mTaskRecyclerView = view.findViewById(R.id.layout_task_list);
        mEmptyListIcon = view.findViewById(R.id.imageView_empty_list);
    }

    private void intView() {
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TaskAdapter taskAdapter = new TaskAdapter(mTaskArrayList);
        mTaskRecyclerView.setAdapter(taskAdapter);
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mRow;
        TextView mTextViewTaskTitle;
        TextView mTextViewTaskState;
        Button mButtonIcon;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mRow = itemView.findViewById(R.id.row_layout);
            mTextViewTaskTitle = itemView.findViewById(R.id.textView_task_title);
            mTextViewTaskState = itemView.findViewById(R.id.textView_task_state);
            mButtonIcon = itemView.findViewById(R.id.button_row_icon);
        }

        public void bindView(final int position){
            mTextViewTaskTitle.setText(mTaskArrayList.get(position).getName());
            mTextViewTaskState.setText(mTaskArrayList.get(position).getState());
            mButtonIcon.setText(String.valueOf(mTaskArrayList.get(position).getName().charAt(0)));
            if(mTaskArrayList.get(position).getState().equals("Todo")){
                mRow.setBackgroundColor(Color.RED);
            } else if(mTaskArrayList.get(position).getState().equals("Doing")){
                mRow.setBackgroundColor(Color.BLUE);
            } else if(mTaskArrayList.get(position).getState().equals("Done")){
                mRow.setBackgroundColor(Color.GREEN);
            }

            mRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = TaskDetailActivity.newIntent(getActivity(), mTaskArrayList.get(position));
                    startActivity(intent);
                }
            });
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{

        ArrayList<Task> mTasks;

        public TaskAdapter(ArrayList<Task> tasks){
            mTasks = tasks;
        }
        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.view_of_row, parent, false);
            TaskViewHolder taskViewHolder = new TaskViewHolder(view);
            return taskViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }


}