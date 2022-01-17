package com.example.waqay;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class SubjectsAdapter extends ArrayAdapter<String> {
    ArrayList<String> title;
  HashMap<String,Boolean> status;
  Status statusObject;
    RegisterSubjectsActivity activity;
ChipGroup chipGroup;
    CheckBox materialCheckBox1;

    ListView l;

    public SubjectsAdapter(RegisterSubjectsActivity activity, ArrayList<String> title,CheckBox materialCheckBox,ChipGroup chipGroup)
    {
        super(activity,R.layout.child_layout,title);
        this.activity=activity;
        this.title=title;
        this.chipGroup=chipGroup;
        statusObject=Status.getInstance();
        this.status=Status.getInstance().subjects_selected;
        materialCheckBox1=materialCheckBox;


    }

public  void change()
{
    notifyDataSetChanged();
}

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row_view;

        //child_click_listener=0;

            LayoutInflater inflater = activity.getLayoutInflater();
            row_view = inflater.inflate(R.layout.child_layout,null,true);
            TextView materialTextView =row_view.findViewById(R.id.parent_header);
           // MaterialButton materialButton =row_view.findViewById(R.id.materialButton);
            CheckBox materialCheckBox=row_view.findViewById(R.id.checkBox);
            materialCheckBox.setContentDescription(position+"");
            materialCheckBox.setTag(position);
          materialTextView.setText(title.get(position));




        if(statusObject.subjects_selected.get(title.get(position)))
        {
            materialCheckBox.setChecked(true);
            materialCheckBox.setContentDescription("1");
        }
        else
        {
            materialCheckBox.setChecked(false);
            materialCheckBox.setContentDescription("0");
        }
        materialCheckBox.setOnClickListener(v -> {
            CheckBox checkBox1 = (CheckBox) v;
            int posit=Integer.parseInt(checkBox1.getTag().toString());
            if(Integer.parseInt(checkBox1.getContentDescription().toString())==0)
            {
                checkBox1.setChecked(true);
                checkBox1.setContentDescription("1");
                Status.getInstance().subjects_selected.put(title.get(posit),true);
                int count=0;
                for(int i=0;i<title.size();i++)
                {
                    if(Status.getInstance().subjects_selected.get(title.get(i)))
                    {
                        count++;
                    }
                }
                if(count==title.size()) {
                    materialCheckBox1.setContentDescription("1");
                    materialCheckBox1.setChecked(true);
                }
            }
            else
            {
                Status.getInstance().subjects_selected.put(title.get(posit),false);
                checkBox1.setChecked(false);
                checkBox1.setContentDescription("0");
                materialCheckBox1.setContentDescription("0");
                materialCheckBox1.setChecked(false);
            }
            chipGroup.removeAllViews();
            Set<String> keys=   statusObject.subjects_selected.keySet();
            Iterator it=keys.iterator();
            while(it.hasNext())
            {
                String obj=(String)it.next();
                if( statusObject.subjects_selected.get(obj))
                {
                    Chip chip= new Chip(getContext());
                    chip.setText(obj);
                    chip.setChipBackgroundColorResource(R.color.purple_500);

                    chip.setTextColor(activity.getResources().getColor(R.color.white));
                    chip.setTextAppearance(R.style.AppTheme_GenderChip);
                    chipGroup.addView(chip);

                }
            }

        });
       /* materialCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox checkBox1 = (CheckBox) buttonView;
                //ConstraintLayout v=(ConstraintLayout) checkBox1.getRootView();
                //int p=l.getPositionForView(v);

                int position= Integer.parseInt(checkBox1.getContentDescription()+"");
                //dataClass.status[position]=isChecked;
                //dataClass=dataClass;
Status.getInstance().subjects_selected.put(title.get(position),isChecked);
if(!isChecked)
{
    materialCheckBox1.setChecked(false);
    materialCheckBox1.setContentDescription("0");
}
            }
        });*/
       /* materialCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            CheckBox c=(CheckBox)buttonView;
        int position1 =  Integer.parseInt(  c.getTag().toString());
        if(isChecked)
        {
            Status.getInstance().subjects_selected.put(title.get(position1),true);
            int count=0;
            for(int i=0;i<title.size();i++)
            {
                if(Status.getInstance().subjects_selected.get(title.get(position1)))
                {
                    count++;
                }
            }
            if(count==title.size())
            {
                materialCheckBox1.setChecked(true);
            }
        }
        else
        {
            Status.getInstance().subjects_selected.put(title.get(position1),false);
            materialCheckBox1.setChecked(false);
        }
        });*/
        return row_view;
    }
    static class ChildHolder
    {
        TextView textView;
        CheckBox materialCheckBox;
    }
}

