package com.dscvit.android.myffcs.fragments;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.adapters.CourseTabRecyclerviewAdapter;
import com.dscvit.android.myffcs.adapters.CustomSpinnerAdapter;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.utils.AppDatabase;
import com.dscvit.android.myffcs.utils.DatabaseContainer;
import com.dscvit.android.myffcs.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseTabFragment extends Fragment {

    private final String TAG = "CourseTabFragment";
    private AppDatabase database;
    private List<String> displayCourses = new ArrayList<>();
    private List<ClassroomResponse> courses = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>(), timings = new ArrayList<>(), venues = new ArrayList<>();
    private CustomSpinnerAdapter customSpinnerAdapter;

    public CourseTabFragment() {
        // Required empty public constructor
    }

    private static List<String> getSlotDays(String slot) {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(Utils.getSlotToDay().get(slot)).split("\\|")));
    }

    private static List<String> getSlotsFromCourse(ClassroomResponse course) {
        return new ArrayList<>(Arrays.asList(course.getSlot().split("\\+")));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = DatabaseContainer.getInstance(requireContext());
        FetchSavedCoursesTask task = new FetchSavedCoursesTask();
        try {
            courses = task.execute().get();
        } catch (Exception e) {
            Log.e(TAG, "onViewCreated: ", e);
        }
        displayCourses.add("Select course");

        Spinner coursesSpinner = view.findViewById(R.id.course_tab_select_spinner);
        customSpinnerAdapter = new CustomSpinnerAdapter(requireContext(), displayCourses);
        coursesSpinner.setAdapter(customSpinnerAdapter);

        RecyclerView coursesRecyclerView = view.findViewById(R.id.course_list_recycler_view);
        CourseTabRecyclerviewAdapter adapter = new CourseTabRecyclerviewAdapter(days, timings, venues);
        coursesRecyclerView.setAdapter(adapter);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        coursesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    days.clear();
                    timings.clear();
                    venues.clear();
                    String courseCode = ((TextView) view.findViewById(R.id.spinner_item_text)).getText().toString().split(":")[0];
                    if (Objects.equals(courseCode, "Select course")) {
                        days.clear();
                        timings.clear();
                        venues.clear();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    for (ClassroomResponse item : courses) {
                        if (Objects.equals(item.getCode(), courseCode)) {
                            for (String slot : getSlotsFromCourse(item)) {
                                for (String day : getSlotDays(slot)) {
                                    days.add(day);
                                    timings.add("10:00-11:00");
                                    venues.add(item.getVenue());
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private class FetchSavedCoursesTask extends AsyncTask<Void, Void, List<ClassroomResponse>> {

        @Override
        protected List<ClassroomResponse> doInBackground(Void... voids) {
            days.clear();
            timings.clear();
            venues.clear();
            List<ClassroomResponse> temp = database.courseDao().getSavedCourses();
            for (ClassroomResponse item : temp) {
                displayCourses.add(item.getCode() + ": " + item.getTitle());
            }
//            for (ClassroomResponse item : temp) {
//                for (String slot : getSlotsFromCourse(item)) {
//                    Log.d(TAG, "doInBackground: " + slot);
//                    for (String slotDay : getSlotDays(slot)) {
//                        days.add(slotDay);
//                        venues.add(item.getVenue());
//                    }
//                }
//            }
//            for (int i=0; i<days.size(); i++) {
//                timings.add("10:00-11:00");
//            }
            return temp;
        }

        @Override
        protected void onPostExecute(List<ClassroomResponse> responseList) {
            super.onPostExecute(responseList);
            customSpinnerAdapter.notifyDataSetChanged();
        }
    }
}
