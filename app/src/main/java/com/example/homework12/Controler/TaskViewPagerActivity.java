package com.example.homework12.Controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.homework12.Models.Task;
import com.example.homework12.R;
import com.example.homework12.Repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class TaskViewPagerActivity extends AppCompatActivity {

    public static final String USER_NAME_FOR_ADD_ACTIVITY = "user name for add activity";
    private ViewPager2 mTaskPager;

    private TabLayout mTabLayout;

    private ArrayList<ArrayList<Task>> mTaskForAdapter;

    private TaskRepository mTaskRepository;

    private TextView mTextViewUsername;

    private FloatingActionButton mFloatingActionButton;

    private String mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view_pager);
        mUserName = getIntent().getStringExtra(StarterFragment.USERNAME);
        findViews();
        initView();
        mTaskRepository = TaskRepository.getInstance();
        initViewPager();
        setListener();
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
        mTaskForAdapter = mTaskRepository.getArrayListsOfLists(mUserName);
        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(this, mTaskForAdapter);
        mTaskPager.setAdapter(taskPagerAdapter);
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
                Intent intent = new Intent(TaskViewPagerActivity.this, AddActivity.class);
                intent.putExtra(USER_NAME_FOR_ADD_ACTIVITY, mUserName);
                startActivity(intent);
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
            default:
                return super.onOptionsItemSelected(item);
        }
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
            Fragment fragment = TaskListFragment.newInstance(mTaskArrayList.get(position));

            return fragment;
        }

        @Override
        public int getItemCount() {
            return mTaskArrayList.size();
        }
    }
}
