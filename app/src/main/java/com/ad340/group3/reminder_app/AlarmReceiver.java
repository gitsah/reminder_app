package com.ad340.group3.reminder_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mPlayer.start();
        String message = "Such Alarm! Much test!";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setAlarm(Context context, Reminder reminder){
        int[] times = SecondActivity.getReminderTime(reminder);
        Calendar calendar = new GregorianCalendar(times[2], times[0], times[1], times[3], times[4]);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am != null) {
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}
