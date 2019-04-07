package com.dscvit.android.myffcs.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.dscvit.android.myffcs.models.ApiModel;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private static ApiModel apiClient;
    ArrayList<String> courses;

    public AutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        courses = new ArrayList<String>();
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myffcs-api.herokuapp.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiClient = retrofit.create(ApiModel.class);


    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public String getItem(int index) {
        return courses.get(index);
    }


    @NonNull
    @Override
    public Filter getFilter() {

        Filter myFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {

                    // A class that queries a web API, parses the data and returns an ArrayList<String>
                    try {
                        courses = new FetchCourses().execute(new String[]{constraint.toString()}).get();
                    } catch (Exception e) {
//                        Log.e("myException", e.getMessage());
                    }
                    // Now assign the values and count to the FilterResults object
                    filterResults.values = courses;
                    filterResults.count = courses.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;

    }


    private static class FetchCourses extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... constraint) {
            HashSet<String> courseList = new HashSet<>();
            List<ClassroomResponse> courseResponse;

            try {
                if (Utils.containsDigit(constraint[0]) || constraint[0].length() <= 3) {
                    Call<List<ClassroomResponse>> classroomCall = apiClient.getCoursesByCode(constraint[0]);
                    courseResponse = classroomCall.execute().body();
                    for (ClassroomResponse item : Objects.requireNonNull(courseResponse)) {
                        courseList.add(item.getCode());
                    }
                } else {
                    Call<List<ClassroomResponse>> classroomCall = apiClient.getCoursesByCourseName(constraint[0]);
                    courseResponse = classroomCall.execute().body();
                    for (ClassroomResponse item : Objects.requireNonNull(courseResponse)) {
                        courseList.add(item.getTitle());
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ArrayList<>(courseList);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

        }

    }


}
