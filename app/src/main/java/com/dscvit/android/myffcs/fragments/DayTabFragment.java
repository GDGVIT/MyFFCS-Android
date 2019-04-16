package com.dscvit.android.myffcs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.adapters.CustomSpinnerAdapter;
import com.dscvit.android.myffcs.adapters.DayTabRecyclerviewAdapter;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.models.CourseViewModel;
import com.dscvit.android.myffcs.utils.SwipeToDeleteCallback;
import com.dscvit.android.myffcs.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayTabFragment extends Fragment {
    private List<ClassroomResponse> displayCourses = new ArrayList<>();
    private List<ClassroomResponse> selectedCourses = new ArrayList<>();
    private String selectedDay;

    public DayTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CourseViewModel viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        viewModel.getSavedCourses().observe(this, responseList -> displayCourses = responseList);


        RecyclerView recyclerView = view.findViewById(R.id.day_recycler_view);
        DayTabRecyclerviewAdapter adapter = new DayTabRecyclerviewAdapter(selectedCourses, selectedDay, requireContext(), requireActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        Spinner dayListSpinner = view.findViewById(R.id.day_tab_select_spinner);
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(requireContext(), Arrays.asList("Select a day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
        dayListSpinner.setAdapter(customSpinnerAdapter);
        dayListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    selectedCourses.clear();
                    selectedDay = ((TextView) view.findViewById(R.id.spinner_item_text)).getText().toString();
                    List<String> selectedCourseNames = new ArrayList<>();
                    if (!Objects.equals(selectedDay, "Select a day")) {
                        for (ClassroomResponse item : displayCourses) {
                            for (String slot : Utils.getSlotsFromCourse(item)) {
                                if (Utils.getSlotDays(slot).contains(selectedDay) && !selectedCourseNames.contains(item.getCode())) {
                                    selectedCourses.add(item);
                                    selectedCourseNames.add(item.getCode());
                                }
                            }
                        }
                        adapter.setSelectedDay(selectedDay);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
