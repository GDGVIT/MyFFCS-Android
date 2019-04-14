package com.dscvit.android.myffcs.utils;

import com.dscvit.android.myffcs.models.ClassroomModel;
import com.dscvit.android.myffcs.models.ClassroomResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM ClassroomResponse ORDER BY code ASC")
    LiveData<List<ClassroomResponse>> getSavedCourses();

    @Query("SELECT * FROM ClassroomModel")
    LiveData<List<ClassroomModel>> getAllCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCacheCourse(ClassroomModel classroomModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(ClassroomResponse response);

    @Update
    void updateCourse(ClassroomResponse response);

    @Delete
    void delete(ClassroomResponse response);
}
