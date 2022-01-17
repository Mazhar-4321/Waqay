package com.example.waqay;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Notification> {
    ArrayList<Notification> notificationsList;
    Activity activity;

    ListView l;

    public ListAdapter(Activity activity, ArrayList<Notification> notificationsList)
    {
        super(activity,R.layout.notification,notificationsList);
        this.activity=activity;
        this.notificationsList=notificationsList;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =  inflater.inflate(R.layout.notification_layout, null, true);
        TextView t_language= view.findViewById(R.id.t_language);
        TextView t_topic=view.findViewById(R.id.t_topic);
        TextView t_home_work_name=view.findViewById(R.id.t_home_work_name);
        Notification notification =notificationsList.get(position);
        t_language.setText(notification.languauge);
        t_topic.setText(notification.topic);
        t_home_work_name.setText(notification.home_work_name);
        return  view;
    }
}
