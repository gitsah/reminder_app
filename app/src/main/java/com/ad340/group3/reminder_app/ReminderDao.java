package com.ad340.group3.reminder_app;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM Reminder")
    List<Reminder> getAll();

    @Query("DELETE FROM Reminder WHERE reminderId = :reminderId")
    void deleteReminder(int reminderId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reminder reminder);

//    @Query("SELECT * FROM Reminder WHERE reminderId LIKE :id LIMIT 1")
//    Reminder findById(int id);
}
