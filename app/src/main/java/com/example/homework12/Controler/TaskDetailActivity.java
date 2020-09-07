
package com.example.homework12.Controler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.homework12.Models.Task;
import com.example.homework12.R;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String TASK_IN_DETAIL_ACTIVITY = "task in detail activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_task_detail_container);
        if(fragment == null){
            fragmentManager.beginTransaction()
                    .add(R.id.activity_task_detail_container, TaskDetailFragment.newInstance((Task) getIntent()
                            .getSerializableExtra(TASK_IN_DETAIL_ACTIVITY)))
                    .commit();
        }
    }

    public static Intent newIntent(Context context, Task task){
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra(TASK_IN_DETAIL_ACTIVITY, task);
        return intent;
    }

}