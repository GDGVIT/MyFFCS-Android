package com.dscvit.android.myffcs.utils;

import android.content.Context;

import com.dscvit.android.myffcs.models.ClassroomModel;
import com.dscvit.android.myffcs.models.ClassroomResponse;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ClassroomResponse.class, ClassroomModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();

    private static AppDatabase database = null;

    public static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "my-database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
