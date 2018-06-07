package com.example.sahand.reminder_app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class Reminder implements Comparable<Reminder>{

    @PrimaryKey(autoGenerate = true)
    private int reminderId;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "snoozable")
    private boolean snoozable;
    @ColumnInfo(name = "snooze_count")
    private int snoozeCount;

    public Reminder () {}

    public boolean snooze() {
        if(!snoozable || snoozeCount < 1)
            return false;
        else {
            snoozeCount--;
            return true;
        }
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSnoozable() {
        return snoozable;
    }

    public void setSnoozable(boolean snoozable) {
        this.snoozable = snoozable;
    }

    public int getSnoozeCount() {
        return snoozeCount;
    }

    public void setSnoozeCount(int snoozeCount) {
        this.snoozeCount = snoozeCount;
    }

    @Override
    public int compareTo(@NonNull Reminder reminder) {
        String[] date1 = this.date.split("/");
        String[] time1 = this.time.split(":");
        String[] date2 = reminder.date.split("/");
        String[] time2 = reminder.time.split(":");

        Calendar calendar1 = new GregorianCalendar(Integer.parseInt(date1[2]), Integer.parseInt(date1[0]),
                Integer.parseInt(date1[1]), Integer.parseInt(time1[0]), Integer.parseInt(time1[1]));

        Calendar calendar2 = new GregorianCalendar(Integer.parseInt(date2[2]), Integer.parseInt(date2[0]),
                Integer.parseInt(date2[1]), Integer.parseInt(time2[0]), Integer.parseInt(time2[1]));

        return calendar1.compareTo(calendar2);
    }
}