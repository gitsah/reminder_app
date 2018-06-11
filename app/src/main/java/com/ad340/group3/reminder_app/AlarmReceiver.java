package com.ad340.group3.reminder_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Such Alarm! Much test!";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//        Intent intent2 = new Intent(context, MainActivity.class);
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent2);
    }

    public static void setAlarm(Context context, Reminder reminder){
        // get a Calendar object with current time
        //Calendar calender = Calendar.getInstance();
        // add 30 seconds to the calendar object
        int[] times = SecondActivity.getReminderTime(reminder);
        Calendar calendar = new GregorianCalendar(times[2], times[0], times[1], times[3], times[4]);
//        calender.set(Calendar.HOUR_OF_DAY, times[3]);
//        calender.set(Calendar.MINUTE, times[4]);
//        calender.set(Calendar.YEAR, times[2]);
//        calender.set(Calendar.MONTH, times[0]);
//        calender.set(Calendar.DAY_OF_MONTH, times[1]);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        System.out.println("system time: " + System.currentTimeMillis() + " our time: " + calendar.getTimeInMillis());
        if (am != null) {
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
        System.out.println(calendar);

        Log.d("Carbon","Alrm SET !!");
    }

//    public static void cancelAlarm(AlarmManager am){
//        if (am != null){
//
//        }
//    }
}
