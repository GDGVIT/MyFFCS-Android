package com.dscvit.android.myffcs.utils;

import com.dscvit.android.myffcs.models.ClassroomResponse;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ClassroomResponse.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
