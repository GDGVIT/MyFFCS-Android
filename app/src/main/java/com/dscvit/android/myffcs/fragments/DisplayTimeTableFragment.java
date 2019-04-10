package com.dscvit.android.myffcs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dscvit.android.myffcs.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayTimeTableFragment extends Fragment {


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
        getChildFragmentManager().beginTransaction().replace(R.id.nested_container, new CourseTabFragment()).commit();
        Button returnPreviousButton = view.findViewById(R.id.go_back_button);
        returnPreviousButton.setOnClickListener(v -> {
            FragmentTransaction transaction = requireFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
            transaction.replace(R.id.main_container, new CourseSelectionFragment());
            transaction.commit();
        });
    }
}