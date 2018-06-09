package com.ad340.group3.reminder_app;

import android.arch.persistence.room.Room;
import android.content.Context;

public class ReminderDatabaseSingleton {

    private static ReminderDatabase db;

    public static ReminderDatabase getDatabase(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context,
                    ReminderDatabase.class, "main-database").build();
        }
        return db;
    }
}