package com.dscvit.android.myffcs;

import android.os.Bundle;

import com.dscvit.android.myffcs.fragments.CourseSelectionFragment;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CourseSelectionFragment()).commit();

    }
}
