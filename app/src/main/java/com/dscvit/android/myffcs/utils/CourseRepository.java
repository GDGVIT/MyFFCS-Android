package com.dscvit.android.myffcs.utils;

import android.app.Application;
import android.util.Log;

import com.dscvit.android.myffcs.models.ApiModel;
import com.dscvit.android.myffcs.models.ClassroomModel;
import com.dscvit.android.myffcs.models.ClassroomResponse;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseRepository {

    private static final String TAG = "CourseRepository";
    private Executor executor = Executors.newSingleThreadExecutor();
    private CourseDao courseDao;
    private LiveData<List<ClassroomResponse>> savedCourses;
    private LiveData<List<ClassroomModel>> allCourses;

    public CourseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        courseDao = database.courseDao();
        savedCourses = courseDao.getSavedCourses();
        allCourses = courseDao.getAllCourses();
    }

    public LiveData<List<ClassroomResponse>> getSavedCourses() {
        return savedCourses;
    }

    public void insertCourse(ClassroomResponse classroomResponse) {
        executor.execute(() -> courseDao.insertCourse(classroomResponse));
    }

    public void deleteCourse(ClassroomResponse classroomResponse) {
        executor.execute(() -> courseDao.delete(classroomResponse));
    }

    public LiveData<List<ClassroomModel>> getAllCourses() {
        updateAllCourses();
        return allCourses;
    }

    public void updateAllCourses() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://myffcs-api.herokuapp.com/")
                .build();
        ApiModel apiModel = retrofit.create(ApiModel.class);

        Call<List<ClassroomModel>> getAllCoursesCall = apiModel.getAllCourses();
        getAllCoursesCall.enqueue(new Callback<List<ClassroomModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ClassroomModel>> call, @NonNull Response<List<ClassroomModel>> response) {
                executor.execute(() -> {
                    for (ClassroomModel item : Objects.requireNonNull(response.body())) {
                        courseDao.insertCacheCourse(item);
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<ClassroomModel>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
