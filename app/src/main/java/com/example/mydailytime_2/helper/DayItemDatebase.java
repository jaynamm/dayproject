package com.example.mydailytime_2.helper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//,exportSchema = false
@Database(entities = {DayItemVO.class, MemoVO.class}, version = 1,exportSchema = false)
public abstract class DayItemDatebase extends RoomDatabase {

    public abstract DayItemDAO dayItemDAO();
    public abstract MemoDAO MemoDAO();

    private static DayItemDatebase INSTANCE;

    private static final Object sLock = new Object();

    public static DayItemDatebase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DayItemDatebase.class, "DAILY_PROJECT_DB")
                        .build();
            }

            return INSTANCE;
        }
    }
}
