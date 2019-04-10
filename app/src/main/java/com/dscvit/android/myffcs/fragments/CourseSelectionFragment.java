package com.dscvit.android.myffcs.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.adapters.CustomSpinnerAdapter;
import com.dscvit.android.myffcs.models.ApiModel;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.utils.AppDatabase;
import com.dscvit.android.myffcs.utils.DatabaseContainer;
import com.dscvit.android.myffcs.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseSelectionFragment extends Fragment {
    private static final String TAG = "CourseSelectionFragment";
    private static List<ClassroomResponse> allCourses;
    private static HashSet<String> allCourseNames = new HashSet<>();
    private static List<String> allCourseCodes = new ArrayList<>();
    private ApiModel apiClient;
    private ApiModel apiClient1;
    private AppDatabase database;
    private List<ClassroomResponse> courseList = new ArrayList<>();


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
        database = DatabaseContainer.getInstance(requireContext());
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(requireContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myffcs-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://myffcs-api.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        apiClient = retrofit.create(ApiModel.class);
        apiClient1 = retrofit1.create(ApiModel.class);
        CacheCoursesTask task = new CacheCoursesTask();
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button viewTimeTable = view.findViewById(R.id.go_back_button);
        Spinner facultySpinner = view.findViewById(R.id.course_tab_select_spinner);
        ImageView facultyImage = view.findViewById(R.id.faculty_image);
        AutoCompleteTextView courseSearch = view.findViewById(R.id.course_search_edittext);
        courseSearch.setThreshold(1);

        facultySpinner.setVisibility(View.GONE);
        facultyImage.setVisibility(View.GONE);

        List<String> facultyList = new ArrayList<>();
        facultyList.add("Select faculty");

        CustomSpinnerAdapter facultyAdapter = new CustomSpinnerAdapter(requireContext(), facultyList);
        ArrayAdapter<String> autocompleteAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_selectable_list_item, allCourseCodes);

        facultySpinner.setAdapter(facultyAdapter);
        courseSearch.setAdapter(autocompleteAdapter);

        courseSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ProgressDialog dialog = new ProgressDialog(requireContext());
                dialog.setMessage("Please wait...");
                dialog.show();
                String course = courseSearch.getText().toString();
                if (Utils.containsDigit(course)) {
                    Call<List<ClassroomResponse>> courseListCall = apiClient.searchCourses(course, null, null, null);
                    courseListCall.enqueue(new Callback<List<ClassroomResponse>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<ClassroomResponse>> call, @NonNull Response<List<ClassroomResponse>> response) {
                            facultyList.clear();

                            for (ClassroomResponse item : Objects.requireNonNull(response.body())) {
                                facultyList.add((item.getSlot().equals("NIL") ? "N/A" : item.getSlot()) + ": " + item.getFaculty() + " (" + item.getType() + ")");
                            }
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Collections.sort(facultyList);
                            facultyList.add(0, "Select faculty");
                            facultyAdapter.notifyDataSetChanged();
                            facultySpinner.setVisibility(View.VISIBLE);
                            facultyImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<ClassroomResponse>> call, @NonNull Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                        }
                    });
                } else {
                    Call<List<ClassroomResponse>> courseListCall = apiClient.searchCourses(null, null, course, null);
                    courseListCall.enqueue(new Callback<List<ClassroomResponse>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<ClassroomResponse>> call, @NonNull Response<List<ClassroomResponse>> response) {
                            facultyList.clear();
                            for (ClassroomResponse item : Objects.requireNonNull(response.body())) {
                                facultyList.add((item.getSlot().equals("NIL") ? "N/A" : item.getSlot()) + ": " + item.getFaculty() + " (" + item.getType() + ")");
                            }
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Collections.sort(facultyList);
                            facultyList.add(0, "Select faculty");
                            facultyAdapter.notifyDataSetChanged();
                            facultySpinner.setVisibility(View.VISIBLE);
                            facultyImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<ClassroomResponse>> call, @NonNull Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                        }
                    });
                }
                return true;
            }
            return false;
        });
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    TextView selectedText = view.findViewById(R.id.spinner_item_text);
                    String facultyText = selectedText.getText().toString();
                    String slotText;
                    if (!facultyText.equals("Select faculty")) {
                        ProgressDialog dialog = new ProgressDialog(requireContext());
                        dialog.setMessage("Please wait...");
                        dialog.show();
                        slotText = facultyText.split(": ")[0];
                        facultyText = facultyText.split(": ")[1].split("\\Q (\\E")[0];

                        Log.d(TAG, "onItemSelected: " + facultyText + slotText);
                        Call<List<ClassroomResponse>> finalCourseCall = null;
                        finalCourseCall = apiClient.searchCourses(null, facultyText, null, slotText);
                        Log.d(TAG, facultyText);
                        Log.d(TAG, slotText.replaceAll("\\+", "%2B"));
                        Log.d(TAG, "onItemSelected: " + finalCourseCall.request());
                        finalCourseCall.enqueue(new Callback<List<ClassroomResponse>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<ClassroomResponse>> call, @NonNull Response<List<ClassroomResponse>> response) {
                                try {
                                    courseList.addAll(Objects.requireNonNull(response.body()));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: ", e);
                                }
                                Log.d(TAG, "onResponse: " + response.body());
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                InsertCourseDialogFragment fragment = new InsertCourseDialogFragment(response.body().get(0));
                                fragment.show(requireFragmentManager(), "insertcourse");
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<ClassroomResponse>> call, @NonNull Throwable t) {
                                Log.e(TAG, "onFailure: ", t);
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewTimeTable.setOnClickListener(v -> {
            FragmentTransaction transaction = requireFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            transaction.replace(R.id.main_container, new DisplayTimeTableFragment());
            transaction.commit();
        });

    }

    private static class CacheCoursesTask extends AsyncTask<Void, Void, List<ClassroomResponse>> {

        @Override
        protected List<ClassroomResponse> doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://gist.githubusercontent.com/ATechnoHazard/72ea29b354302ee13007cb1e69cd9716/raw/016e4f728056fedf4d652667fe475202f425f718/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiModel tempClient = retrofit.create(ApiModel.class);
            Call<List<ClassroomResponse>> allCoursesCall = tempClient.getAllCourses();
            try {
                allCourses = allCoursesCall.execute().body();
                for (ClassroomResponse item : Objects.requireNonNull(allCourses)) {
                    allCourseNames.add(item.getTitle());
                    allCourseCodes.add(item.getCode());
                }
                allCourseCodes.addAll(allCourseNames);
                allCourseCodes = new ArrayList<>(new HashSet<>(allCourseCodes));
                Collections.sort(allCourseCodes);
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
            }
            return allCourses;
        }
    }


}
