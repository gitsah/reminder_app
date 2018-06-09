package com.ad340.group3.reminder_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Reminder.class}, version = 2)
public abstract class ReminderDatabase extends RoomDatabase {
    public abstract ReminderDao reminderDao();
}