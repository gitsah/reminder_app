package com.ad340.group3.reminder_app;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends Activity {

    private EditText timeText;
    private EditText dateText;
    private EditText messageText;
    private EditText snoozeInterval;
    private boolean snoozeStatus;
    private Date currentDate;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        timeText = findViewById(R.id.time_field);
        dateText = findViewById(R.id.date_field);
        messageText = findViewById(R.id.message_field);
        ToggleButton snoozeable = findViewById(R.id.snoozeable);
        snoozeInterval = findViewById(R.id.limit_field);

        snoozeable.setOnCheckedChangeListener((buttonView, isChecked) -> snoozeStatus = isChecked);

        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        dateText.setOnClickListener(v -> new DatePickerDialog(SecondActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    public void add(View view){
        if(timeText.getText().toString().length() == 0 || dateText.getText().toString().length() == 0
                || messageText.getText().toString().length() == 0 || snoozeInterval.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "One or more fields is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(timeText.getText().toString().split(":").length != 2){
            Toast.makeText(getApplicationContext(), "Time isn't formatted correctly, use 24hr like: 3:04 or 16:02", Toast.LENGTH_SHORT).show();
            return;
        }

        Reminder newReminder = new Reminder();

        newReminder.setTime(timeText.getText().toString());
        newReminder.setDate(dateText.getText().toString());
        newReminder.setMessage(messageText.getText().toString());
        newReminder.setSnoozable(snoozeStatus);
        newReminder.setSnoozeCount(Integer.parseInt(snoozeInterval.getText().toString()));
        new AddRemindersTask(this, newReminder).execute();
    }

    private static class AddRemindersTask extends AsyncTask<Void, Void, Reminder> {

        private WeakReference<SecondActivity> weakActivity;
        private Reminder reminder;

        AddRemindersTask(SecondActivity secondActivity, Reminder reminder) {
            weakActivity = new WeakReference<>(secondActivity);
            this.reminder = reminder;
        }

        @Override
        protected Reminder doInBackground(Void... voids) {
            SecondActivity secondActivity = weakActivity.get();
            if(secondActivity == null) {
                return null;
            }

            ReminderDatabase db = ReminderDatabaseSingleton.getDatabase(secondActivity);

            db.reminderDao().insert(reminder);
            AlarmReceiver.setAlarm(secondActivity, reminder);

            return reminder;
        }

        @Override
        protected void onPostExecute(Reminder reminder) {
            if(reminder == null) {
                Toast.makeText(weakActivity.get(), "Reminder not added", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(weakActivity.get(), "Reminder added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(sdf.format(myCalendar.getTime()));
    }

//    public int getTimeSpan(Reminder reminder){
//        String myFormat = "MM/dd/yyyy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        String timeFormat = "HH:mm";
//        SimpleDateFormat stf = new SimpleDateFormat(timeFormat);
//
//        currentDate = new Date();
//        String currentDate1 = sdf.format(currentDate);
//        String currentTime1 = stf.format(currentDate);
//
//        String[] date1 = currentDate1.split("/");
//        String[] time1 = currentTime1.split(":");
//        String[] date2 = reminder.getDate().split("/");
//        String[] time2 = reminder.getTime().split(":");
//
//        int tMonth = Integer.parseInt(date1[0]);
//        int tDay = Integer.parseInt(date1[1]);
//        int tYear = Integer.parseInt(date1[2]);
//        int tHour = Integer.parseInt(date1[0]);
//        int tMinute = Integer.parseInt(date1[1]);
//
//        int aMonth = Integer.parseInt(date2[0]);
//        int aDay = Integer.parseInt(date2[1]);
//        int aYear = Integer.parseInt(date2[2]);
//        int aHour = Integer.parseInt(date2[0]);
//        int aMinute = Integer.parseInt(date2[1]);
//
//        int cMonth = Math.abs(aMonth - tMonth);
//        int cDay = Math.abs(aDay - tDay);
//        int cYear = Math.abs(aYear - tYear);
//        int cHour = Math.abs(aHour - tHour);
//        int cMinute = Math.abs(aMinute - tMinute);
//
//        if(cMonth == 0 && cYear == 0){
//            int total = cMinute*60*1000 + cHour*3600*1000 + cDay*24*3600*1000;
//            return total;
//        }
//        int total = 9999999;
//
//        return total;
//    }

    public static int[] getReminderTime(Reminder reminder){
        String[] date2 = reminder.getDate().split("/");
        String[] time2 = reminder.getTime().split(":");
        int aMonth = Integer.parseInt(date2[0]);
        int aDay = Integer.parseInt(date2[1]);
        int aYear = Integer.parseInt(date2[2]);
        int hour = Integer.parseInt(time2[0]);
        int minute = Integer.parseInt(time2[1]);
        int[] times = new int[]{aMonth, aDay, aYear, hour, minute};
        return times;
    }

}
