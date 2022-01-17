package com.example.waqay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	 StudentsSubjectsDisplay activity;
	 Context con;
    int i=-1;
    int current_expanded=-1,previous_expanded=-1;
	int notify_data_set_changed;
	 ArrayList<Boolean> parent_headers_selected;
	 HashMap<String,ArrayList<String>> child_headers;
	 HashMap<String,ArrayList<Boolean>> child_headers_selected;
	 ExpandableListView expandableListView;
	HashMap<String,ArrayList<TopicNameAndData>> hashMap1= new HashMap<>();
	HashMap<String,TopicData> hashMap= new HashMap<>();
	ArrayList<String> parent_headers= new ArrayList<>();
	ExpandableListView e;
	 ExpandableListAdapter(StudentsSubjectsDisplay activity,HashMap<String,TopicData> hashMap,ArrayList<String> parent_headers,ExpandableListView e ,Context con)
	 {
	 	 this.activity=activity;
	 	this.hashMap= hashMap;
        this.parent_headers= parent_headers;
         expandableListView=e;
         this.con=con;
	 }
	 
	 @Override
	 public void notifyDataSetChanged() {
		  super.notifyDataSetChanged();
	 }
	 
	 @Override
	 public void registerDataSetObserver(DataSetObserver observer) {
		  super.registerDataSetObserver(observer);
	 }
	 
	 @Override
	 public int getGroupCount() {
		  return hashMap.size();
	 }
	 
	 @Override
	 public int getChildrenCount(int groupPosition) {

		  return hashMap.get(parent_headers.get(groupPosition)).questions_list.size();
	 }
	 
	 @Override
	 public Object getGroup(int groupPosition) {
		  return hashMap.get(groupPosition);
	 }
	 
	 @Override
	 public Object getChild(int groupPosition, int childPosition) {
		  return hashMap.get(parent_headers.get(groupPosition)).questions_list.get(childPosition);
	 }
	 
	 @Override
	 public long getGroupId(int groupPosition) {
		  return groupPosition;
	 }
	 
	 @Override
	 public long getChildId(int groupPosition, int childPosition) {
		  return childPosition;
	 }
	 
	 @Override
	 public boolean hasStableIds() {
		  return false;
	 }
	 

	 
	 @Override
	 public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	 	 View row_view=convertView;
	 	 if(row_view==null)
		 {
			  LayoutInflater inflater = activity.getLayoutInflater();
			   row_view = inflater.inflate(R.layout.group_layout,parent,false);
			  TextView t_topic_name =row_view.findViewById(R.id.t_topic_name);
			 TextView t_level =row_view.findViewById(R.id.t_level);
			 TextView t_date =row_view.findViewById(R.id.t_date);
			  MaterialButton b_plus =row_view.findViewById(R.id.b_plus);
			  MaterialButton b_pdf=row_view.findViewById(R.id.b_pdf);
			 MaterialButton b_video=row_view.findViewById(R.id.b_video);
			  GroupHolder groupHolder = new GroupHolder();
			  groupHolder.t_topic_name=t_topic_name;
			  groupHolder.t_level=t_level;
			  groupHolder.t_date=t_date;
			 groupHolder.b_plus=b_plus;
			 groupHolder.b_pdf=b_pdf;
			 groupHolder.b_video=b_video;
			  row_view.setTag(groupHolder);

			 
		 }
	 	 GroupHolder groupHolder =(GroupHolder) row_view.getTag();
		 TextView t_topic_name =groupHolder.t_topic_name;
		 TextView t_level =groupHolder.t_level;
		 TextView t_date =groupHolder.t_date;
		 MaterialButton b_plus =groupHolder.b_plus;
		 MaterialButton b_pdf=groupHolder.b_pdf;
		 MaterialButton b_video=groupHolder.b_video;
		 //TopicNameAndData topicNameAndData = hashMap.get(parent_headers.get(groupPosition)).get();
		 // t_topic_name.setText();
           TopicData topicData=hashMap.get(parent_headers.get(groupPosition));
		 t_topic_name.setText("Topic:"+parent_headers.get(groupPosition));
		 t_level.setText("Level:"+topicData.level);
		 t_date.setText("Date:"+topicData.date);
		 b_pdf.setTag(topicData.pdf_info);
		 b_video.setTag(topicData.video_link);
		 b_pdf.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
			 	MaterialButton button=(MaterialButton)v;
			 String list=(String)
					 button.getTag();
				 activity.startPDF(list);
			 }
		 });
		 b_video.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 MaterialButton button=(MaterialButton)v;
				 String list=(String)	button.getTag();

			 	activity.startVideo(list);
			 }
		 });
		  if(isExpanded)
		  {


				groupHolder.b_pdf.setVisibility(View.VISIBLE);
				groupHolder.b_video.setVisibility(View.VISIBLE);
				groupHolder.b_plus.setIcon(AppCompatResources.getDrawable(activity.getApplicationContext(), R.drawable.minus));


		  }
		  else
		  {
			  groupHolder.b_pdf.setVisibility(View.GONE);
			  groupHolder.b_video.setVisibility(View.GONE);
			  groupHolder.b_plus.setIcon( AppCompatResources.getDrawable(activity.getApplicationContext(), R.drawable.plus));



			    }

		  return row_view;
	 }

	@Override
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
	}

	@Override
	 public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	 	View row_view=convertView;
	 	if(row_view==null)
		{
			LayoutInflater inflater = activity.getLayoutInflater();
			 row_view = inflater.inflate(R.layout.child1,null,true);
			TextView materialTextView =row_view.findViewById(R.id.parent_header);
			ChildHolder groupHolder = new ChildHolder();
			//groupHolder.materialButton=materialButton;
			groupHolder.textView=materialTextView;

			row_view.setTag(groupHolder);
		}
		 ChildHolder groupHolder =(ChildHolder) row_view.getTag();
		 TextView materialTextView =groupHolder.textView;
		TopicData topicData=hashMap.get(parent_headers.get(groupPosition));
		 materialTextView.setText("Q("+(childPosition+1)+")"+topicData.questions_list.get(childPosition));
		 //MaterialButton materialButton =groupHolder.materialButton;

		  return row_view;
	 }
	 
	 
	 @Override
	 public boolean isChildSelectable(int groupPosition, int childPosition) {
		  return true;
	 }
	 static class ChildHolder
	 {
	 	TextView textView;

	 }
	 static  class GroupHolder
	 {
	 	  TextView t_topic_name;
		 TextView t_level;
		 TextView t_date;
	 	  MaterialButton b_plus;
	 	  MaterialButton b_pdf;
		 MaterialButton b_video;

	 }
}
