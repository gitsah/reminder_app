package com.example.sahand.reminder_app;

public class Reminder {

    private int reminderId;
    private String time;
    private String date;
    private String message;
    private boolean snoozable;
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
}