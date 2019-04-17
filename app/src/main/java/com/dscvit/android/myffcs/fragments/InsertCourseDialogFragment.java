package com.dscvit.android.myffcs.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.models.CourseViewModel;
import com.dscvit.android.myffcs.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class InsertCourseDialogFragment extends DialogFragment {
    private ClassroomResponse insertCourse;
    private CourseViewModel viewModel;
    private List<ClassroomResponse> savedCourses;

    public InsertCourseDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    InsertCourseDialogFragment(ClassroomResponse insertCourse) {
        this.insertCourse = insertCourse;
    }


    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        viewModel.getSavedCourses().observe(this, responseList -> savedCourses = responseList);

        builder.setMessage("Add this course to the time table?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (!Utils.canClashWith(requireActivity(), insertCourse, savedCourses)) {
                        for (ClassroomResponse item : savedCourses) {
                            if (item.getCode().equals(insertCourse.getCode()) && item.getType().equals(insertCourse.getType())) {
                                Toast.makeText(requireContext(), "You have already registered this course component", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        viewModel.insertCourse(insertCourse);
                    }
                })
                .setNegativeButton("No", (dialog, which) -> this.dismiss());
        return builder.create();
    }
}
