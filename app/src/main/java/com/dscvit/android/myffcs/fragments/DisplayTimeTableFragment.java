package com.dscvit.android.myffcs.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.models.ApiModel;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.models.CourseViewModel;
import com.dscvit.android.myffcs.models.TimeTableBody;
import com.dscvit.android.myffcs.models.UserResponse;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayTimeTableFragment extends Fragment {
    private static final String TAG = "DisplayTimeTableFragmen";
    private static CourseViewModel viewModel;
    private static FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private static List<ClassroomResponse> savedCourses;

    public DisplayTimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_time_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        viewModel.getSavedCourses().observe(this, classroomResponses -> savedCourses = classroomResponses);


        SegmentedButtonGroup segmentedButtonGroup = view.findViewById(R.id.segmented_course_tabs);

        FloatingActionsMenu floatingActionsMenu = view.findViewById(R.id.parent_fab);
        FloatingActionButton uploadFab = view.findViewById(R.id.upload_fab);
        FloatingActionButton downloadFab = view.findViewById(R.id.download_fab);
        if (currentUser == null) {
            floatingActionsMenu.setVisibility(View.GONE);
            uploadFab.setVisibility(View.GONE);
            downloadFab.setVisibility(View.GONE);
        }

        uploadFab.setOnClickListener(v -> {
            floatingActionsMenu.collapse();
            UploadAsyncTask task = new UploadAsyncTask(requireContext());
            task.execute();
        });

        downloadFab.setOnClickListener(v -> {
            floatingActionsMenu.collapse();
            DownloadAsyncTask task = new DownloadAsyncTask(requireContext());
            task.execute();
        });

        getChildFragmentManager().beginTransaction().replace(R.id.nested_container, new CourseTabFragment()).commit();

        Button returnPreviousButton = view.findViewById(R.id.go_back_button);
        returnPreviousButton.setOnClickListener(v -> {
            FragmentTransaction transaction = requireFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
            transaction.replace(R.id.main_container, new CourseSelectionFragment());
            transaction.commit();
        });

        segmentedButtonGroup.setOnClickedButtonListener(position -> {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            switch (position) {
                case 0:
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    transaction.replace(R.id.nested_container, new CourseTabFragment()).commit();
                    break;
                case 1:
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    transaction.replace(R.id.nested_container, new DayTabFragment()).commit();
                    break;
            }
        });

    }

    private static class DownloadAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;
        private WeakReference<Context> contextRef;

        DownloadAsyncTask(Context context) {
            contextRef = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(contextRef.get());
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Syncing timetable from cloud...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (currentUser != null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://myffcs-api.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiModel apiModel = retrofit.create(ApiModel.class);
                Call<UserResponse> savedUser = apiModel.getUser(currentUser.getUid());
                UserResponse currUser = new UserResponse();
                try {
                    currUser = savedUser.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (currUser != null) {
                    List<Integer> timeTable = currUser.getTimetable();
                    if (!timeTable.isEmpty()) {
                        viewModel.deleteAllCourses();
                        List<ClassroomResponse> fetchedCourses = new ArrayList<>();
                        for (Integer courseId : timeTable) {
                            Log.d(TAG, "onViewCreated: " + courseId);

                            Call<ClassroomResponse> getCourseCall = apiModel.getCourseById(courseId);
                            try {
                                ClassroomResponse response = getCourseCall.execute().body();
                                if (response != null) {
                                    response.setId(courseId);
                                }
                                fetchedCourses.add(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        viewModel.insertCourseList(fetchedCourses);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    private static class UploadAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;
        private WeakReference<Context> contextRef;

        UploadAsyncTask(Context context) {
            contextRef = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(contextRef.get());
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Syncing timetable to cloud...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://myffcs-api.herokuapp.com")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiModel apiModel = retrofit.create(ApiModel.class);


            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (savedCourses != null && currentUser != null) {
                List<Integer> timeTable = new ArrayList<>();
                Log.d(TAG, "onViewCreated: " + timeTable);
                for (ClassroomResponse item : savedCourses) {
                    timeTable.add(item.getId());
                }
                Log.d(TAG, "onViewCreated: ");
                Call<String> updateTimeTableCall = apiModel.updateTimetable(currentUser.getUid(), new TimeTableBody(timeTable));
                try {
                    updateTimeTableCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


}