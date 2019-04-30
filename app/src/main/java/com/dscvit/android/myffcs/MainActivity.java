package com.dscvit.android.myffcs;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.dscvit.android.myffcs.fragments.CourseSelectionFragment;
import com.dscvit.android.myffcs.models.CourseViewModel;
import com.dscvit.android.myffcs.utils.CourseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CourseViewModel viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        CourseRepository repository = new CourseRepository(getApplication());
        setContentView(R.layout.activity_main);
        repository.updateAllCourses();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        CircleImageView accountPic = findViewById(R.id.account_pic);
        if (currentUser != null) {

            Glide.with(MainActivity.this).load(currentUser.getPhotoUrl()).placeholder(R.drawable.ic_person).fitCenter().into(accountPic);
            accountPic.setOnClickListener(v -> new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Sign out?")
                    .setMessage("You will lose your saved timetable. However, you can resync it from the cloud if you have saved it already.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        viewModel.deleteAllCourses();
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));

                    }).setNegativeButton("No", ((dialog, which) -> dialog.dismiss())).show());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CourseSelectionFragment()).commit();
    }
}
