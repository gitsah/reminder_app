package com.ad340.group3.reminder_app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReminderFragment extends Fragment {

    private List<Reminder> reminders;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Date currentDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView mListView = (ListView) inflater.inflate(
                R.layout.fragment_reminder, container, false);

        if(reminders != null)
            Collections.sort(reminders);

        ReminderAdapter adapter = new ReminderAdapter(mListView.getContext(), reminders);
        mListView.setAdapter(adapter);

        return mListView;
    }

    class ReminderAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<Reminder> mDataSource;
        private Calendar calender;

        ReminderAdapter(Context context, List<Reminder> reminders) {
            mContext = context;
            mDataSource = reminders;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            alarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get view for row item
            if (convertView == null) convertView = mInflater.inflate(R.layout.list_item_reminder, parent, false);

            Reminder reminder = reminders.get(position);
            if (reminder != null) {
                TextView time = convertView.findViewById(R.id.reminder_list_time);
                TextView date = convertView.findViewById(R.id.reminder_list_date);
                TextView title = convertView.findViewById(R.id.reminder_list_title);
                ImageView thumbnail = convertView.findViewById(R.id.reminder_list_thumbnail);
                Button delete = convertView.findViewById(R.id.delete_button);

                if(thumbnail != null) {
                    thumbnail.setImageResource(R.drawable.simple_calendar_icon);
                }
                if (time != null) {
                    time.setText(reminder.getTime());
                }
                if(date != null){
                    date.setText(reminder.getDate());
                }
                if(title != null){
                    title.setText(reminder.getMessage());
                }
                if(delete != null){
                    delete.setOnClickListener(v -> {
                        Activity activity = getActivity();
                        if(activity instanceof MainActivity){
                            MainActivity mainActivity = (MainActivity) activity;
                            new MainActivity.DeleteReminderTask(mainActivity, reminder.getReminderId()).execute();
                        }
                    });
                }

//                long timeChange = getTimeSpan(reminder);
//                int[] times = getReminderTime(reminder);
//                AlarmReceiver.setAlarm(mContext, reminder);

//                Intent intent = new Intent(mContext, ReminderFragment.class);
//                alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
//
//                alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), alarmIntent);


            }
            return convertView;
        }
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }


}
