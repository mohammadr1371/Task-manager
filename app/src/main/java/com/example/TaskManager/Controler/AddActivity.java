package com.example.TaskManager.Controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.TaskManager.R;

public class AddActivity extends AppCompatActivity {
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mUsername = getIntent().getStringExtra(TaskViewPagerActivity.USER_NAME_FOR_ADD_ACTIVITY);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_add_container);
        if(fragment == null){
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_add_container, AddDialogFragment.newInstance(mUsername))
                    .commit();
        }
    }
}