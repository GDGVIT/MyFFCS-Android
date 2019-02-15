package com.dscvit.android.myffcs;

import android.content.Intent;
import android.os.Bundle;

import com.dscvit.android.myffcs.fragments.SignInFragment;
import com.dscvit.android.myffcs.fragments.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
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
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}