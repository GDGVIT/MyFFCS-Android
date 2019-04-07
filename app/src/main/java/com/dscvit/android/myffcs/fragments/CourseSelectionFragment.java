package com.dscvit.android.myffcs.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.adapters.AutoCompleteAdapter;
import com.dscvit.android.myffcs.adapters.CustomSpinnerAdapter;
import com.dscvit.android.myffcs.models.ApiModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseSelectionFragment extends Fragment {
    private static final String TAG = "CourseSelectionFragment";

    private Retrofit retrofit;

    public CourseSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://myffcs-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiModel apiClient = retrofit.create(ApiModel.class);

        Spinner courseSpinner = view.findViewById(R.id.course_select_spinner);
        courseSpinner.setVisibility(View.GONE);
        AutoCompleteTextView courseSearch = view.findViewById(R.id.course_search_edittext);
        courseSearch.setThreshold(3);

        List<String> testList = new ArrayList<>();
        List<String> courseList = new ArrayList<>();
        testList.add("Test");
        testList.add("Test2");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(requireContext(), testList);
        AutoCompleteAdapter autocompleteAdapter = new AutoCompleteAdapter(requireActivity(), android.R.layout.simple_selectable_list_item);
        courseSpinner.setAdapter(adapter);
        courseSearch.setAdapter(autocompleteAdapter);

        courseSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                courseSpinner.setEnabled(true);
                return true;
            }
            return false;
        });
//        courseSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (courseSearch.getText().length() >= 3) {
//                    Call<List<ClassroomResponse>> searchCall = apiClient.getCoursesByCode(courseSearch.getText().toString());
//                    searchCall.enqueue(new Callback<List<ClassroomResponse>>() {
//                        @Override
//                        public void onResponse(@NonNull Call<List<ClassroomResponse>> call, @NonNull Response<List<ClassroomResponse>> response) {
//                            List<ClassroomResponse> responseList = response.body();
//                            for (ClassroomResponse item : Objects.requireNonNull(responseList)) {
//                                courseList.add(item.getCode());
//                                Log.d(TAG, "onResponse: " + item.getCode());
//                            }
//                            autocompleteAdapter.notifyDataSetChanged();
//                            courseSearch.setAdapter(autocompleteAdapter);
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Call<List<ClassroomResponse>> call, @NonNull Throwable t) {
//                            Log.e(TAG, "onFailure: ", t);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }
}
