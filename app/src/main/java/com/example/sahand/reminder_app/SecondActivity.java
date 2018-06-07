package com.example.sahand.reminder_app;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
    private ToggleButton snoozeable;
    private EditText snoozeInterval;
    private boolean snoozeStatus;

    Calendar myCalendar = Calendar.getInstance();
    Date todayDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        timeText = findViewById(R.id.time);
        dateText = findViewById(R.id.date);
        messageText = findViewById(R.id.message);
        snoozeable = findViewById(R.id.snoozeable);
        snoozeInterval = findViewById(R.id.interval);

        snoozeable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    snoozeStatus = isChecked;
                } else {
                    snoozeStatus = isChecked;
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SecondActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void add(View view){
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

        public AddRemindersTask(SecondActivity secondActivity, Reminder reminder) {
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

}
