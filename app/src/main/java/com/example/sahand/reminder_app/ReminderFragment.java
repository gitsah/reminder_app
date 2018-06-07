package com.example.sahand.reminder_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReminderFragment extends Fragment {

    private ListView mListView;

    private List<Reminder> reminders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        mListView = view.findViewById(R.id.reminder_list_view);

        return view;
    }

    class ReminderAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Reminder> mDataSource;

        public ReminderAdapter(Context context, ArrayList<Reminder> reminders) {
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

            return convertView;
        }
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
