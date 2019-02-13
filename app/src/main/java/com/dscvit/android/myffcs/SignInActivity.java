package com.dscvit.android.myffcs;

import android.os.Bundle;

import com.dscvit.android.myffcs.fragments.SignInFragment;
import com.dscvit.android.myffcs.fragments.SignUpFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SegmentedButtonGroup segmentedButtonGroup = findViewById(R.id.segmentedButtonGroup);
        fragmentManager.beginTransaction().replace(R.id.sign_in_container, new SignInFragment()).commit();
        segmentedButtonGroup.setOnClickedButtonListener(position -> {
            switch (position) {
                case 0:
                    fragmentManager.beginTransaction().replace(R.id.sign_in_container, new SignInFragment()).commit();
                    break;
                case 1:
                    fragmentManager.beginTransaction().replace(R.id.sign_in_container, new SignUpFragment()).commit();
                    break;
            }
        });
    }
}