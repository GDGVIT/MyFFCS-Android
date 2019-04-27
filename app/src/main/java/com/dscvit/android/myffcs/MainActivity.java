package com.dscvit.android.myffcs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dscvit.android.myffcs.fragments.CourseSelectionFragment;
import com.dscvit.android.myffcs.utils.CourseRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CourseRepository(getApplication()).updateAllCourses();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CourseSelectionFragment()).commit();

    }
}
