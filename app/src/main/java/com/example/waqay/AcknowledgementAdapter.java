
package com.example.waqay;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.ListView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;

        import com.google.android.material.checkbox.MaterialCheckBox;

        import java.util.ArrayList;
        import java.util.HashMap;

public class AcknowledgementAdapter extends ArrayAdapter<String> {

    Activity activity;
    ArrayList<String> a_list;
    ListView l;
HashMap<String,String> student_acknowledgement_hashmap ;
    public AcknowledgementAdapter(Activity activity, ArrayList<String> a_list,HashMap<String,String> student_acknowledgement_hashmap )
    {
        super(activity,R.layout.acknowledgement,a_list);
        this.activity=activity;
        this.a_list=a_list;
        this.student_acknowledgement_hashmap=student_acknowledgement_hashmap;



    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =  inflater.inflate(R.layout.acknowledgement, null, true);
        TextView tv_question=view.findViewById(R.id.tv_question);
        tv_question.setText(a_list.get(position));
        MaterialCheckBox ch_poor=view.findViewById(R.id.ch_poor);
        ch_poor.setTag(a_list.get(position));
        TextView tv_poor=(TextView)view.findViewById(R.id.tv_poor);
        TextView tv_good=(TextView)view.findViewById(R.id.tv_good);
        TextView tv_average=(TextView)view.findViewById(R.id.tv_average);
       ch_poor.setTag(R.id.tv_poor,tv_poor);

        MaterialCheckBox ch_good=view.findViewById(R.id.ch_good);
        ch_good.setTag(a_list.get(position));
        ch_good.setTag(R.id.tv_average,tv_average);
        MaterialCheckBox ch_best=view.findViewById(R.id.ch_best);
        ch_best.setTag(a_list.get(position));
        ch_best.setTag(R.id.tv_good,tv_good);
        if( student_acknowledgement_hashmap.containsKey(a_list.get(position)))
        {
            if(student_acknowledgement_hashmap.get(a_list.get(position)).equals("poor"))
            {
                ch_poor.setChecked(true);
            }
            if(student_acknowledgement_hashmap.get(a_list.get(position)).equals("average"))
            {
                ch_good.setChecked(true);
            }
            if(student_acknowledgement_hashmap.get(a_list.get(position)).equals("good"))
            {
                ch_best.setChecked(true);
            }
        }
        else
        {
            student_acknowledgement_hashmap.put(a_list.get(position),"");
        }
        ch_poor.setOnClickListener(v -> {
            String question = (String)ch_poor.getTag();
            TextView tvv_poor=(TextView)ch_poor.getTag(R.id.tv_poor);
            Animation anim1 = AnimationUtils.loadAnimation(activity,R.anim.translate_animation1);
//                    l_saif.startAnimation(anim);
//                    l_home.startAnimation(anim1);
//                    l_bottom.startAnimation(anim);
            tvv_poor.startAnimation(anim1);
            if(ch_poor.isChecked())
            {

                ch_good.setChecked(false);
                ch_best.setChecked(false);
                student_acknowledgement_hashmap.put(question,"poor");

              //studentAcknowledgement=studentAcknowledgement;
            }
            else
            {
                student_acknowledgement_hashmap.put(question,"");
            }

        });
        ch_good.setOnClickListener(v -> {
            String question = (String)ch_good.getTag();
            TextView tvv_average=(TextView)ch_good.getTag(R.id.tv_average);
            Animation anim1 = AnimationUtils.loadAnimation(activity,R.anim.translate_animation1);
//                    l_saif.startAnimation(anim);
//                    l_home.startAnimation(anim1);
//                    l_bottom.startAnimation(anim);
            tvv_average.startAnimation(anim1);
            if(ch_good.isChecked())
            {
                ch_poor.setChecked(false);
                ch_best.setChecked(false);
                student_acknowledgement_hashmap.put(question,"average");
            }
            else
            {
                student_acknowledgement_hashmap.put(question,"");
            }
        });
        ch_best.setOnClickListener(v -> {
            String question = (String)ch_best.getTag();
            TextView tvv_good=(TextView)ch_best.getTag(R.id.tv_good);
            Animation anim1 = AnimationUtils.loadAnimation(activity,R.anim.translate_animation1);
//                    l_saif.startAnimation(anim);
//                    l_home.startAnimation(anim1);
//                    l_bottom.startAnimation(anim);
            tvv_good.startAnimation(anim1);
            if(ch_best.isChecked())
            {
                ch_good.setChecked(false);
                ch_poor.setChecked(false);
                student_acknowledgement_hashmap.put(question,"good");
            }
            else
            {
                student_acknowledgement_hashmap.put(question,"");
            }
        });

        return  view;
    }
}
