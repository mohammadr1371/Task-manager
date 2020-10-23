package com.example.TaskManager.Controler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.TaskManager.R;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.single_fragment_layout);


        if(fragment == null){
            fragmentManager.beginTransaction()
                    .add(R.id.single_fragment_layout, StarterFragment.newInstance())
                    .commit();
        }
    }
}