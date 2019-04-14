package com.dscvit.android.myffcs.models;

import android.app.Application;

import com.dscvit.android.myffcs.utils.CourseRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository repository;
    private LiveData<List<ClassroomResponse>> savedCourses;
    private LiveData<List<ClassroomModel>> allCourses;

    public CourseViewModel(Application application) {
        super(application);
        repository = new CourseRepository(application);
        savedCourses = repository.getSavedCourses();
        allCourses = repository.getAllCourses();
    }

    public LiveData<List<ClassroomResponse>> getSavedCourses() {
        return savedCourses;
    }

    public LiveData<List<ClassroomModel>> getAllCourses() {
        return allCourses;
    }

    public void insertCourse(ClassroomResponse classroomResponse) {
        repository.insertCourse(classroomResponse);
    }
}
