package com.example.waqay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    ArrayList<SubjectInfo> subjectInfoArrayList;
    Context activity;
    public GridAdapter( Context activity,  ArrayList<SubjectInfo> subjectInfoArrayList) {
//        super(activity, R.layout.grid_subjects, subjectInfoArrayList);
        this.subjectInfoArrayList=subjectInfoArrayList;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return subjectInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectInfoArrayList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.grid_subjects,null);
        TextView t_subject_name=view.findViewById(R.id.t_subject_name);
        TextView t_running_unit=view.findViewById(R.id.t_running_unit);
        TextView t_percentage_completed=view.findViewById(R.id.t_percentage_completed);
        t_subject_name.setText("Subject Name :"+subjectInfoArrayList.get(position).subject_name);
        t_running_unit.setText("Running Unit :"+subjectInfoArrayList.get(position).running_unit);
        t_percentage_completed.setText("Percentage Completed :"+subjectInfoArrayList.get(position).completed+"%");
        view.setTag(subjectInfoArrayList.get(position).subject_name);
return  view;
    }
    //    Activity activity;
//    Context context;
//    ArrayList<SubjectInfo> subjectInfoArrayList;
//    GridAdapter(Activity activity,ArrayList<SubjectInfo> subjectInfoArrayList)
//    {
//        this.activity=activity;
//        this.subjectInfoArrayList=subjectInfoArrayList;
//    }
//    @Override
//    public int getCount() {
//        return subjectInfoArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View view =  inflater.inflate(R.layout.grid_subjects, null, true);
//        TextView t_subject_name=view.findViewById(R.id.t_subject_name);
//        TextView t_running_unit=view.findViewById(R.id.t_running_unit);
//        TextView t_percentage_completed=view.findViewById(R.id.t_percentage_completed);
//        t_subject_name.setText("Subject Name :"+subjectInfoArrayList.get(position).subject_name);
//        t_running_unit.setText("Running Unit :"+subjectInfoArrayList.get(position).running_unit);
//        t_percentage_completed.setText("Percentage Completed :"+subjectInfoArrayList.get(position).completed+"%");
//return  view;
//    }
}
