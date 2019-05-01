package com.dscvit.android.myffcs.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.adapters.CourseTabRecyclerviewAdapter;
import com.dscvit.android.myffcs.adapters.CustomSpinnerAdapter;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.models.CourseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dscvit.android.myffcs.utils.Utils.getSlotDays;
import static com.dscvit.android.myffcs.utils.Utils.getSlotsFromCourse;
import static com.dscvit.android.myffcs.utils.Utils.getTimingFromCourseAndDay;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseTabFragment extends Fragment {

    private final String TAG = "CourseTabFragment";
    private List<String> displayCourses = new ArrayList<>();
    private List<ClassroomResponse> savedCourses = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>(), timings = new ArrayList<>(), venues = new ArrayList<>();
    private CustomSpinnerAdapter customSpinnerAdapter;
    private Spinner coursesSpinner;

    public CourseTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CourseViewModel viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        viewModel.getSavedCourses().observe(this, responseList -> {
            if (responseList.isEmpty()) {
                displayCourses.add("Select course");
            } else {
                savedCourses = responseList;
                displayCourses.clear();
                displayCourses.add("Select course");
                for (ClassroomResponse item : responseList) {
                    displayCourses.add(item.getCode() + ": " + item.getTitle() + " (" + item.getType() + ")");
                }
            }
            coursesSpinner.setSelection(displayCourses.indexOf("Select course"));
            customSpinnerAdapter.notifyDataSetChanged();
        });

        coursesSpinner = view.findViewById(R.id.course_tab_select_spinner);
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
                    String spinnerText = ((TextView) view.findViewById(R.id.spinner_item_text)).getText().toString();
                    String courseCode = spinnerText.split(":")[0];
                    if (Objects.equals(spinnerText, "Select course")) {
                        days.clear();
                        timings.clear();
                        venues.clear();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    days.clear();
                    timings.clear();
                    venues.clear();
                    String courseType = spinnerText.split("\\(")[1].split("\\)")[0];

                    //List<String> tempList = new ArrayList<>();
                    for (ClassroomResponse item : savedCourses) {
                        if (Objects.equals(item.getCode(), courseCode) && Objects.equals(item.getType(), courseType)) {
                            for (String slot : getSlotsFromCourse(item)) {
                                for (String day : getSlotDays(slot)) {
                                    if (!days.contains(day)) {
                                        Log.d(TAG, "onItemSelected: adding " + day);
                                        days.add(day);
                                        timings.add(getTimingFromCourseAndDay(item, day));
                                        venues.add(item.getVenue());
                                    }
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    days.clear();
                    timings.clear();
                    venues.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
