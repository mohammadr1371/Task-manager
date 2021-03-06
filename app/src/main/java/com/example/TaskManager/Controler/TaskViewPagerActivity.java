package com.example.TaskManager.Controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.TaskManager.Models.Task;
import com.example.TaskManager.R;
import com.example.TaskManager.Repository.AppRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class TaskViewPagerActivity extends AppCompatActivity implements AddDialogFragment.callBacks,TaskDetailDialogFragment.callBacks {

    public static final String USER_NAME_FOR_ADD_ACTIVITY = "user name for add activity";
    public static final String ADD_DIALOG_FRAGMENT = "add dialog fragment";
    private ViewPager2 mTaskPager;

    private TabLayout mTabLayout;

    ArrayList<ArrayList<Task>> mTaskListForAdapter;

    private ArrayList<Task> mTaskList;

    private AppRepository mAppRepository;

    private TextView mTextViewUsername;

    private FloatingActionButton mFloatingActionButton;

    private String mUserName;

    private int mCurrentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view_pager);
        mUserName = getIntent().getStringExtra(StarterFragment.USERNAME_IN_STARTER);
        mAppRepository = AppRepository.getInstance(this);
        mTaskList = getTaskListOFUser(mUserName);
        findViews();
        initView();
        initViewPager();
        setListener();
    }

    private void setActionbarSubtitle() {

        if(mUserName.equalsIgnoreCase("admin")){
            this.getSupportActionBar().setSubtitle(mAppRepository.mITaskDatabaseDao().getTaskList().size()+" Tasks");
        } else {
            this.getSupportActionBar().setSubtitle(getTaskListOFUser(mUserName).size()+" Tasks");
        }
    }

    private void initView() {
        mTextViewUsername.setText(mUserName);
    }

    public void findViews() {
        mTaskPager = findViewById(R.id.task_view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mFloatingActionButton = findViewById(R.id.floatingActionButton);
        mTextViewUsername = findViewById(R.id.textView_username_task_view_pager);
    }

    public void initViewPager() {
        mTaskListForAdapter = getArrayListForViewpager(getTaskListOFUser(mUserName));
        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(this, mTaskListForAdapter);
        mTaskPager.setAdapter(taskPagerAdapter);
        mTaskPager.setCurrentItem(mCurrentPosition);
        setActionbarSubtitle();
        new TabLayoutMediator(mTabLayout, mTaskPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText("To DO");
                        } else if (position == 1) {
                            tab.setText("Doing");
                        } else if (position == 2) {
                            tab.setText("Done");
                        }
                    }
                }
        ).attach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewPager();
    }

    public void setListener() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialogFragment addDialogFragment = AddDialogFragment.newInstance(mUserName);
                addDialogFragment.show(getSupportFragmentManager(), ADD_DIALOG_FRAGMENT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                finish();
                break;
            case R.id.item_delete_all:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setMessage("Do you want to delete all of tasks?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAppRepository.mITaskDatabaseDao().deleteAllTasks();
                                initViewPager();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void updateView() {
        initViewPager();
    }

    class TaskPagerAdapter extends FragmentStateAdapter {

        private ArrayList<ArrayList<Task>> mTaskArrayList;

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<ArrayList<Task>> taskArrayList) {
            super(fragmentActivity);
            mTaskArrayList = taskArrayList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = TaskListFragment.newInstance(mTaskArrayList.get(position), mUserName);
            mCurrentPosition = position;
            return fragment;
        }

        @Override
        public int getItemCount() {
            return mTaskArrayList.size();
        }
    }

    private ArrayList<Task> getTaskListOFUser (String userName){
        ArrayList<Task> allTasks = (ArrayList<Task>) mAppRepository.mITaskDatabaseDao().getTaskList();
        ArrayList<Task> outputTaskList = new ArrayList<>();
        if(userName.equalsIgnoreCase("admin")){
            outputTaskList = allTasks;
        } else {
            for (Task task : allTasks) {
                if (task.getUsername().equals(userName)) {
                    outputTaskList.add(task);
                }
            }
        }
        return outputTaskList;
    }

    private ArrayList<ArrayList<Task>> getArrayListForViewpager(ArrayList<Task> tasks){
        ArrayList<ArrayList<Task>> arrayListOfArrayList = new ArrayList<>();
        ArrayList<Task> todoTaskList = new ArrayList<>();
        ArrayList<Task> doingTaskList = new ArrayList<>();
        ArrayList<Task> doneTaskList = new ArrayList<>();
        for (Task task: tasks) {
            if(task.getState().equals("Todo")) {
                todoTaskList.add(task);
            } else if(task.getState().equals("Doing")){
                doingTaskList.add(task);
            } else if(task.getState().equals("Done")){
                doneTaskList.add(task);
            }
        }

        arrayListOfArrayList.add(todoTaskList);
        arrayListOfArrayList.add(doingTaskList);
        arrayListOfArrayList.add(doneTaskList);

        return arrayListOfArrayList;
    }
}
