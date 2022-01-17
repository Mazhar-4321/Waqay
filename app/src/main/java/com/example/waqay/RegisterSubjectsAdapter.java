package com.example.waqay;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterSubjectsAdapter extends BaseExpandableListAdapter {
    Activity activity;
    final ArrayList<String> parent_headers;
    int notify_data_set_changed,child_click_listener;
    ArrayList<Boolean> parent_headers_selected;
    HashMap<String,ArrayList<String>> child_headers;
    HashMap<String,ArrayList<Boolean>> child_headers_selected;
    ExpandableListView expandableListView;
   static ArrayList<CheckBox> materialCheckBoxArrayList= new ArrayList<>();
   static HashMap<Integer,Integer> g= new HashMap<>();
    int count=0,hoshiyar=0;

    RegisterSubjectsAdapter(Activity activity, final ArrayList<String> parent_headers, HashMap<String,ArrayList<String>> child_headers, ExpandableListView expandableListView, final ArrayList<Boolean> parent_headers_selected, final HashMap<String,ArrayList<Boolean>> child_headers_selected)
    {
        this.activity=activity;
        this.parent_headers=parent_headers;
        this.child_headers=child_headers;
        this.parent_headers_selected=parent_headers_selected;
        this.child_headers_selected=child_headers_selected;
        this.expandableListView=expandableListView;
/*expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
    ConstraintLayout c=(ConstraintLayout)v;
   int count= c.getChildCount();

    MaterialCheckBox materialCheckBox=(MaterialCheckBox) c.getChildAt(count-1);
    ArrayList<Boolean> al = child_headers_selected.get(parent_headers.get(groupPosition));
    materialCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
        if(isChecked)
        {
            for (int i = 0; i < al.size(); i++) {
                al.set(i, true);
            }
        }
        else
        {

            for (int i = 0; i < al.size(); i++) {
                al.set(i, false);
            }
        }
        parent_headers_selected.set(groupPosition, parent_headers_selected.get(groupPosition)?false:true);
        child_headers_selected.put(parent_headers.get(groupPosition), al);
        notifyDataSetChanged();
    });


//        if( materialCheckBox.isChecked()) { materialCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//
//            MaterialCheckBox materialCheckBox1 = (MaterialCheckBox) buttonView;
//            int position = Integer.parseInt((String) materialCheckBox1.getTag());
//            int gp=groupPosition;
//           // ArrayList<Boolean> al = child_headers_selected.get(parent_headers.get(position));
//            if (isChecked&&child_click_listener==0) {
//
//                for (int i = 0; i < al.size(); i++) {
//                    al.set(i, true);
//                }
//
//            }
//            else {
//
//                for (int i = 0; i < al.size(); i++) {
//                    al.set(i, false);
//                }
//            }
//            parent_headers_selected.set(position, isChecked);
//
//            //notify_data_set_changed=0;
//            if(child_click_listener==0) {
//
//                child_headers_selected.put(parent_headers.get(position), al);
//                notifyDataSetChanged();
//            }
//        });
//        }
    return false;
});*/


    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
       // child_click_listener=0;

    }

    public   void change()
    {
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return parent_headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_headers.get(parent_headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parent_headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_headers.get(parent_headers.get(groupPosition)).get(childPosition);
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
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View row_view = convertView;
        if (row_view == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row_view = inflater.inflate(R.layout.parent_layout, parent, false);
            TextView materialTextView = row_view.findViewById(R.id.parent_header);
            MaterialButton materialButton = row_view.findViewById(R.id.materialButton);
            CheckBox materialCheckBox = row_view.findViewById(R.id.checkBox);
            materialCheckBoxArrayList.add(materialCheckBox);
            GroupHolder groupHolder = new GroupHolder();
            groupHolder.materialButton = materialButton;
            groupHolder.textView = materialTextView;
            groupHolder.materialCheckBox = materialCheckBox;
            row_view.setTag(groupHolder);
            row_view.setTag(R.id.TAG_ONLINE_ID, materialCheckBox);

        }
        GroupHolder groupHolder = (GroupHolder) row_view.getTag();
        TextView materialTextView = groupHolder.textView;
        materialTextView.setText(parent_headers.get(groupPosition));
        MaterialButton materialButton = groupHolder.materialButton;
        CheckBox materialCheckBox = groupHolder.materialCheckBox;
        //materialCheckBoxArrayList.set(groupPosition,materialCheckBox);
        materialCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            ArrayList<Boolean> al = child_headers_selected.get(parent_headers.get(groupPosition));

            if (isChecked) {
                for (int i = 0; i < al.size(); i++) {
                    al.set(i, true);
                }
            } else {
                for (int i = 0; i < al.size(); i++) {
                    al.set(i, false);
                }
            }

                  child_headers_selected.put(parent_headers.get(groupPosition), al);
                  parent_headers_selected.set(groupPosition, isChecked);

                  notifyDataSetChanged();





        });
        materialCheckBox.setTag(groupPosition + "");

            if (parent_headers_selected.get(groupPosition)) {
                materialCheckBox.setChecked(true);
            } else {
                materialCheckBox.setChecked(false);
            }


		 /* LayoutInflater inflater = activity.getLayoutInflater();
		  View view = inflater.inflate(R.layout.parent_layout,null,true);
		  MaterialTextView materialTextView =view.findViewById(R.id.parent_header);
		  final MaterialButton materialButton =view.findViewById(R.id.materialButton);
		  materialButton.setContentDescription("c-"+groupPosition);

		  materialButton.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View v) {

				 MaterialButton materialButton1=(MaterialButton)v;
				 String tokens=materialButton1.getContentDescription()+"";
				 String[] array=tokens.split("-");
				 String c_e=array[0];
				 int g_position=Integer.parseInt(array[1]);
				 if(c_e.equals("c"))
				 {
				 	 materialButton1.setIcon(AppCompatResources.getDrawable(activity.getApplicationContext(),R.drawable.minus));
				 	 c_e="e";
				 	 materialButton1.setContentDescription(c_e+"-"+g_position);
				 	 expandableListView.expandGroup(g_position);
				 }
				 else
				 {
					  materialButton1.setIcon(AppCompatResources.getDrawable(activity.getApplicationContext(),R.drawable.plus));
					  c_e="c";
					  materialButton1.setContentDescription(c_e+"-"+g_position);
					  expandableListView.collapseGroup(g_position);
				 }

			   }
		  });
		  materialTextView.setText(parent_headers.get(groupPosition));*/
        if(isExpanded)
        {
            //groupHolder.materialButton.setIcon(AppCompatResources.getDrawable(R.drawable.));
            //groupHolder.textView.setText("hi");
            groupHolder.materialCheckBox.setVisibility(View.VISIBLE);
            groupHolder.materialButton.setIcon( AppCompatResources.getDrawable(activity.getApplicationContext(), R.drawable.minus));
        }
        else
        {
            //groupHolder.textView.setText("Bye");
            groupHolder.materialCheckBox.setVisibility(View.GONE);
            groupHolder.materialButton.setIcon( AppCompatResources.getDrawable(activity.getApplicationContext(), R.drawable.plus));
        }
hoshiyar=0;
        return row_view;
    }



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View row_view=convertView;

        //child_click_listener=0;
        if(row_view==null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            row_view = inflater.inflate(R.layout.child_layout,null,true);
            TextView materialTextView =row_view.findViewById(R.id.parent_header);
            MaterialButton materialButton =row_view.findViewById(R.id.materialButton);
            CheckBox materialCheckBox=row_view.findViewById(R.id.checkBox);
            Tag t= new Tag();
            t.c=childPosition;t.p=groupPosition;
            materialCheckBox.setTag(t);
            ChildHolder groupHolder = new ChildHolder();
            //groupHolder.materialButton=materialButton;
            groupHolder.textView=materialTextView;
            groupHolder.materialCheckBox=materialCheckBox;
            row_view.setTag(groupHolder);
        }
        ChildHolder groupHolder =(ChildHolder) row_view.getTag();
        TextView materialTextView =groupHolder.textView;
        materialTextView.setText(child_headers.get( parent_headers.get(groupPosition)).get(childPosition));
        //MaterialButton materialButton =groupHolder.materialButton;
        CheckBox materialCheckBox=groupHolder.materialCheckBox;
        materialCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            CheckBox checkBox=(CheckBox)buttonView;
            Tag t=(Tag)checkBox.getTag();
           // MaterialCheckBox materialCheckBox= v.findViewById(R.id.checkBox);

            child_headers_selected.get(parent_headers.get(t.p)).set(t.c,materialCheckBox.isChecked()?false:true);
            ArrayList<Boolean> checkList =child_headers_selected.get(parent_headers.get(t.p));
            int count=0,count1=0;
            for(int i=0;i<checkList.size();i++)
            {
                if(checkList.get(i))
                    count++;
                else
                    count1++;
            }

            if(count==checkList.size())
            {
                parent_headers_selected.set(t.p,true);
               // RegisterSubjectsAdapter.materialCheckBoxArrayList.get(groupPosition).setChecked(true);

            }
            else {

                parent_headers_selected.set(t.p, false);
               // RegisterSubjectsAdapter.materialCheckBoxArrayList.get(groupPosition).setChecked(false);

            }

            notifyDataSetChanged();

        });
        if(child_headers_selected.get(parent_headers.get(groupPosition)).get(childPosition))
        {
            materialCheckBox.setChecked(true);
        }
        else
        {
            materialCheckBox.setChecked(false);
        }
        return row_view;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    static class ChildHolder
    {
        TextView textView;
        CheckBox materialCheckBox;
    }
    static  class GroupHolder
    {
        TextView textView;
        MaterialButton materialButton;
        CheckBox materialCheckBox;
    }
}

