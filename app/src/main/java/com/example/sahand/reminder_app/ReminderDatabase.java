package com.example.sahand.reminder_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ReminderDatabase.class}, version = 1)
public abstract class ReminderDatabase extends RoomDatabase {
    public abstract ReminderDao reminderDao();
}
