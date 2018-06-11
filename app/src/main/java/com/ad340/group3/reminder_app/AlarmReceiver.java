package com.ad340.group3.reminder_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

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
        Calendar calender = Calendar.getInstance();
        // add 30 seconds to the calendar object
        int[] times = SecondActivity.getReminderTime(reminder);
        calender.setTimeInMillis(System.currentTimeMillis());
        calender.set(Calendar.HOUR_OF_DAY, times[3]);
        calender.set(Calendar.MINUTE, times[4]);
        calender.set(Calendar.YEAR, times[2]);
        calender.set(Calendar.MONTH, times[0]);
        calender.set(Calendar.DAY_OF_MONTH, times[1]);
//        calender.add(Calendar.SECOND, 30);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), sender);
        System.out.println(calender);

        Log.d("Carbon","Alrm SET !!");
    }

//    public static void cancelAlarm(AlarmManager am){
//        if (am != null){
//
//        }
//    }
}
