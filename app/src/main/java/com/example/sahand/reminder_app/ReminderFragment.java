package com.example.sahand.reminder_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class ReminderFragment extends Fragment {

    private List<Reminder> reminders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView mListView = (ListView) inflater.inflate(
                R.layout.fragment_reminder, container, false);

        if(reminders != null)
            Collections.sort(reminders);

        ReminderAdapter adapter = new ReminderAdapter(mListView.getContext(), this, reminders);
        mListView.setAdapter(adapter);

        return mListView;
    }

    class ReminderAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<Reminder> mDataSource;

        public ReminderAdapter(Context context, ReminderFragment reminderFragment, List<Reminder> reminders) {
            mContext = context;
            mDataSource = reminders;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                        new MainActivity.DeleteReminderTask((MainActivity) getActivity(), reminder.getReminderId()).execute();
                    });
                }
            }
            return convertView;
        }
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
