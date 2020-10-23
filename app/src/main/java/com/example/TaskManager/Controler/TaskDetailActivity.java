
package com.example.TaskManager.Controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.TaskManager.Models.Task;
import com.example.TaskManager.R;

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
                    .add(R.id.activity_task_detail_container, TaskDetailDialogFragment.newInstance((Task) getIntent()
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