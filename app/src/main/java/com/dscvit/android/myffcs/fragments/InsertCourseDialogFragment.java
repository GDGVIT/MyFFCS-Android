package com.dscvit.android.myffcs.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.utils.AppDatabase;
import com.dscvit.android.myffcs.utils.DatabaseContainer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class InsertCourseDialogFragment extends DialogFragment {
    private ClassroomResponse insertCourse;

    public InsertCourseDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public InsertCourseDialogFragment(ClassroomResponse insertCourse) {
        this.insertCourse = insertCourse;
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override


    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AppDatabase database = DatabaseContainer.getInstance(requireContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Add this course to the time table?")
                .setPositiveButton("Yes", (dialog, which) -> new AsyncTask<Void, Void, Void>() {
                    ProgressDialog dialog = new ProgressDialog(requireContext());
                    String test = null;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog.setMessage("Saving...");
                        dialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        database.courseDao().insertCourse(insertCourse);
                        test = database.courseDao().getSavedCourses().get(0).getTitle();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            //Toast.makeText(requireContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute())
                .setNegativeButton("No", (dialog, which) -> {
                    this.dismiss();
                });
        return builder.create();
    }
}
