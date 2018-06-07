package com.example.sahand.reminder_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Reminder> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetRemindersTask(this).execute();
    }

    private void populateReminders() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ReminderFragment reminderFragment = new ReminderFragment();
        reminderFragment.setReminders(reminders);
        ft.replace(R.id.frag_holder, reminderFragment);
        ft.commit();
    }

    private static class GetRemindersTask extends AsyncTask<Void, Void, List<Reminder>> {

        private WeakReference<MainActivity> weakActivity;

        GetRemindersTask(MainActivity mainActivity) {
            weakActivity = new WeakReference<>(mainActivity);
        }

        @Override
        protected List<Reminder> doInBackground(Void... voids) {
            MainActivity mainActivity = weakActivity.get();
            if (mainActivity == null) {
                return null;
            }

            ReminderDatabase db = ReminderDatabaseSingleton.getDatabase(mainActivity);

            List<Reminder> reminders = db.reminderDao().getAll();

            if (reminders == null) {
                return null;
            } else
                return reminders;
        }

        @Override
        protected void onPostExecute(List<Reminder> reminders) {
            MainActivity mainActivity = weakActivity.get();
            if (reminders == null || mainActivity == null) {
                return;
            }
            mainActivity.reminders = reminders;
            mainActivity.populateReminders();
        }
    }


    public static class DeleteReminderTask extends AsyncTask<Void, Void, List<Reminder>> {

        private WeakReference<MainActivity> weakActivity;
        private int reminderId;

        DeleteReminderTask(MainActivity weakActivity, int reminderId) {
            this.weakActivity = new WeakReference<>(weakActivity);
            this.reminderId = reminderId;
        }

        @Override
        protected List<Reminder> doInBackground(Void... voids) {
            MainActivity mainActivity = weakActivity.get();
            if (mainActivity == null) {
                return null;
            }

            ReminderDatabase db = ReminderDatabaseSingleton.getDatabase(mainActivity);

            db.reminderDao().deleteReminder(reminderId);

            List<Reminder> reminders = db.reminderDao().getAll();

            if (reminders == null) {
                return null;
            } else
                return reminders;
        }

        @Override
        protected void onPostExecute(List<Reminder> reminders) {
            MainActivity mainActivity = weakActivity.get();
            if (mainActivity == null || reminders == null) {
                return;
            }
            mainActivity.reminders = reminders;
            mainActivity.populateReminders();
        }
    }

    public void addReminder(View view){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}

