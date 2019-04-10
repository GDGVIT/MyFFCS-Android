package com.dscvit.android.myffcs.utils;

import android.content.Context;

import androidx.room.Room;

public class DatabaseContainer {
    private static AppDatabase database = null;

    public static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "my-database").build();
        }
        return database;
    }
}
