package com.dscvit.android.myffcs;

import android.content.Intent;
import android.os.Bundle;

import com.dscvit.android.myffcs.adapters.CustomPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SegmentedButtonGroup segmentedButtonGroup = findViewById(R.id.segmentedButtonGroup);
        ViewPager viewPager = findViewById(R.id.signin_viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                segmentedButtonGroup.setPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            segmentedButtonGroup.setOnClickedButtonListener(position -> {
                viewPager.setCurrentItem(position, true);
            });
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}