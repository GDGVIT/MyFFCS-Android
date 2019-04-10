package com.dscvit.android.myffcs.utils;

import com.dscvit.android.myffcs.models.ClassroomResponse;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM ClassroomResponse")
    List<ClassroomResponse> getSavedCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(ClassroomResponse response);

    @Update
    void updateCourse(ClassroomResponse response);

    @Delete
    void delete(ClassroomResponse response);
}
