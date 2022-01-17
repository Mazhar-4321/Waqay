package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class StudentsSubjectsDisplay extends AppCompatActivity {
ArrayList<String> parent_headers= new ArrayList<>();
ArrayList<TopicNameAndData> list= new ArrayList<>();
HashMap<String,ArrayList<TopicNameAndData>> hashMap= new HashMap<>();
HashMap<String,TopicData> h_topic_data= new HashMap<>();
ArrayList<String> group_headers= new ArrayList<>();
ArrayList<HomeWork> homeWorkArrayList = new ArrayList<>();
HashMap<String,ArrayList<HomeWork>> h_home_work= new HashMap<>();
    HashMap<String,StudentHomeWork> h_home_work1= new HashMap<>();
StudentHomeWork studentHomeWork= new StudentHomeWork();
HashMap<String,QuestionPaper> questionPaperHashMap = new HashMap<>();
MaterialAutoCompleteTextView a_select_mode;
ExpandableListView expandableListView;
    String year,subject,phone;
private CharSequence[] topics;
    private CharSequence[] questions;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
studentHomeWork.checked="NA";
studentHomeWork.pdf_info="NA";
studentHomeWork.remarks="NA";
        setContentView(R.layout.activity_students_subjects_display);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setCustomView(R.layout.toolbar);
            View v=  getSupportActionBar().getCustomView();
            TextView t_title= v.findViewById(R.id.t_title);
            t_title.setText("Pasha");
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            LayoutInflater inflator = LayoutInflater.from(this);
            View v1 = inflator.inflate(R.layout.toolbar, null);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
            ((TextView)v.findViewById(R.id.t_title)).setText("CHOOSE TOPIC");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }

        String s = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference reference_mazhar = database1.getReference("Users/" + s);
        reference_mazhar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUserData userData = snapshot.getValue(FirebaseUserData.class);
                if(userData.isActive.equals("0")) {
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });





























        FirebaseDatabase database = FirebaseDatabase.getInstance();
        expandableListView=(ExpandableListView)findViewById(R.id.expandable_list_view) ;
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });
        a_select_mode=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_mode);
        String[] select_customer_type = new String[]{"1","2","3","4","5"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(StudentsSubjectsDisplay.this, R.layout.drop_down_item, select_customer_type);
        a_select_mode.setAdapter(arrayAdapter);
        a_select_mode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<TopicNameAndData> arrayList=    hashMap.get(a_select_mode.getText().toString());
                h_topic_data= new HashMap<>();
                group_headers= new ArrayList<>();
                if(arrayList!=null) {


                    for (int i = 0; i < arrayList.size(); i++) {
                        TopicNameAndData topicNameAndData = arrayList.get(i);
                        h_topic_data.put(topicNameAndData.topic_name, topicNameAndData.topicData);
                        group_headers.add(topicNameAndData.topic_name);
                    }
                    ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(StudentsSubjectsDisplay.this, h_topic_data, group_headers, expandableListView, getApplicationContext());
                    expandableListView.setAdapter(expandableListAdapter);
                }
                else
                {
                    Toast.makeText(StudentsSubjectsDisplay.this,"Data Not Yet Uploaded",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // String s = FirebaseDatabase.getInstance().getReference("StudentsData").child(phone_number).getKey();
        year=getIntent().getStringExtra("year");
         subject=getIntent().getStringExtra("subject");
         phone=getIntent().getStringExtra("phone");
        DatabaseReference reference = database.getReference("TopicData/"+year+"/"+subject);
        DatabaseReference reference1 = database.getReference("HomeWork/"+subject);
        DatabaseReference reference2 = database.getReference("StudentHomeWork/"+year+"/"+subject);
        DatabaseReference reference3 = database.getReference("QuestionPapers/"+year+"/"+subject);
        //DatabaseReference reference1 = database.getReference("SubjectInfo");
        try {


            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   // for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        // String key =postSnapshot .getKey();
                                      questionPaperHashMap.put(snapshot.getKey(),snapshot.getValue(QuestionPaper.class));

                    //}


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(StudentsSubjectsDisplay.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }

            });
        }
        catch (Exception e)
        {
            Toast.makeText(StudentsSubjectsDisplay.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        try {


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String key1 = postSnapshot.getKey();
                        parent_headers.add(key1);
                        list = new ArrayList<>();
                        for (DataSnapshot d : postSnapshot.getChildren()) {
                            TopicData topicData = d.getValue(TopicData.class);
                            TopicNameAndData topicNameAndData = new TopicNameAndData();
                            topicNameAndData.topic_name = d.getKey();
                            topicNameAndData.topicData = topicData;
                            list.add(topicNameAndData);

                        }
                        hashMap.put(key1, list);


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                     Toast.makeText(StudentsSubjectsDisplay.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }

            });
        }
        catch (Exception e)
        {
            Toast.makeText(StudentsSubjectsDisplay.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        try {


            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    homeWorkArrayList= new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String key1 = postSnapshot.getKey();
                        HomeWork homeWork= postSnapshot.getValue(HomeWork.class);
                        homeWorkArrayList.add(homeWork);
                        //parent_headers.add(key1);
                        //list = new ArrayList<>();
//                        for (DataSnapshot d : postSnapshot.getChildren()) {
//                            TopicData topicData = d.getValue(TopicData.class);
//                            TopicNameAndData topicNameAndData = new TopicNameAndData();
//                            topicNameAndData.topic_name = d.getKey();
//                            topicNameAndData.topicData = topicData;
//                            list.add(topicNameAndData);
//
//                        }
                        h_home_work.put(key1, homeWorkArrayList);


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(StudentsSubjectsDisplay.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                }

            });
        }
        catch (Exception e)
        {
            Toast.makeText(StudentsSubjectsDisplay.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        try {


            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    homeWorkArrayList= new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String key=postSnapshot.getKey();
                        if(key.equals(phone)) {
                            for (DataSnapshot snapshot1 : postSnapshot.getChildren()) {
                                //String key1=snapshot1.getKey();
                                for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                                    StudentHomeWork studentHomeWork1 = snapshot2.getValue(StudentHomeWork.class);
                                    h_home_work1.put(snapshot1.getKey(), studentHomeWork1);
                                }
                            }
                        }
//                        StudentHomeWork studentHomeWork1=postSnapshot.getValue(StudentHomeWork.class);
//                        if(studentHomeWork1.checked!=null)
//                        {
//                            studentHomeWork=studentHomeWork1;
//                        }
                          }



                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }






            });
        }
        catch (Exception e)
        {
            Toast.makeText(StudentsSubjectsDisplay.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public  void startPDF(String s)
    {
        //MaterialButton button=(MaterialButton)v;
        String list=s;
       // Toast.makeText(activity,"Wait",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(context,PDFViewerActivity.class);
        intent.putExtra("list",list);
        startActivity(intent);
    }
    public void startVideo(String s)
    {
        Intent intent= new Intent(context,YoutTubeActivity.class);
       intent.putExtra("video",s);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_work,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toast.makeText(KYCNewActivity.this,item.getItemId()+"",Toast.LENGTH_LONG).show();

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if(item.getItemId()==R.id.m_ac)
        {
            Intent intent = new Intent(StudentsSubjectsDisplay.this,AcknowledgementActivity.class);
            intent.putExtra("year",year);
            intent.putExtra("subject",subject);
            intent.putExtra("mobile",phone);
            intent.putExtra("unit",a_select_mode.getText().toString());

            startActivity(intent);

        }
        if(item.getItemId()==R.id.m_home_work)

        {
           Set<String> keys= h_topic_data.keySet();
          Iterator it= keys.iterator();
          topics= new CharSequence[keys.size()];
          int i=0;
           while(it.hasNext())
           {
               String topic=(String)it.next();
               topics[i]=topic;
               i++;
           }
            if(a_select_mode.getText().toString().length()!=0&&topics.length!=0)
            {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(StudentsSubjectsDisplay.this);

                builder.setTitle("Select Topic To Proceed HomeWork");
                builder.setSingleChoiceItems(topics, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(StudentsSubjectsDisplay.this, topics[which], Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentsSubjectsDisplay.this,StudentHomeWorkActivity.class);
                        //year,subject,phone;
                    ArrayList <HomeWork> a=   h_home_work.get(topics[which]);
                    if(a!=null) {
                        HomeWork homeWork = new HomeWork();
                        for (int i = 0; i < a.size(); i++) {
                            if (a.get(i).topic.equals(topics[which])) {
                                homeWork = a.get(i);
                            }
                        }

                        StudentHomeWork st;
                        st = h_home_work1.get(topics[which]);
                        intent.putExtra("end_date", homeWork.end_date);
                        intent.putExtra("start_date", homeWork.start_date);
                        intent.putExtra("name", homeWork.name);
                        if (st == null) {
                            intent.putExtra("checked", studentHomeWork.checked);
                            intent.putExtra("pdf_info", studentHomeWork.pdf_info);
                            intent.putExtra("remarks", studentHomeWork.remarks);
                        } else {
                            intent.putExtra("checked", st.checked);
                            intent.putExtra("pdf_info", st.pdf_info);
                            intent.putExtra("remarks", st.remarks);
                            if (st.remarks_list != null) {
                                intent.putExtra("t_handwriting", st.remarks_list.get(0));
                                intent.putExtra("t_neatness", st.remarks_list.get(1));
                                intent.putExtra("t_sentence_framing", st.remarks_list.get(2));
                                intent.putExtra("t_creativity", st.remarks_list.get(3));
                                intent.putExtra("t_presentation", st.remarks_list.get(4));
                                intent.putExtra("t_highlighters", st.remarks_list.get(5));
                            }
                        }
                        intent.putExtra("phone", phone);
                        intent.putExtra("topic", topics[which]);
                        intent.putExtra("year", year);
                        intent.putExtra("subject", subject);

                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(StudentsSubjectsDisplay.this,"Home Work Not Yet Available",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    }
                });
                builder.show();
            }
            else
            {
                Toast.makeText(StudentsSubjectsDisplay.this,"Please Select Unit",Toast.LENGTH_SHORT).show();
            }

        }
        if(item.getItemId()==R.id.m_question_paper) {

            try {


                QuestionPaper questionPaper = questionPaperHashMap.get(subject);
                ArrayList<String> questionsList = questionPaper.pdf_info;
                questions = new CharSequence[questionsList.size()];
                String pattern = "Paper";
                for (int i = 0; i < questions.length; i++) {
                    questions[i] = pattern + (i + 1);
                }

                if (true || a_select_mode.getText().toString().length() != 0 && questions.length != 0) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(StudentsSubjectsDisplay.this);

                    builder.setTitle("Select One Of the Question Papers To Proceed");
                    builder.setSingleChoiceItems(questions, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(StudentsSubjectsDisplay.this, DisplayQuestionPaperActivity.class);
                            intent.putExtra("list", questionsList.get(which));
                            startActivity(intent);

                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(StudentsSubjectsDisplay.this, "Please Select Unit", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(StudentsSubjectsDisplay.this,"Question Papers are not Uploaded Yet",Toast.LENGTH_LONG).show();
            }

            }
            if (item.getItemId() == R.id.m_strategy) {
                //item.setTitle("");
                SubMenu subMenu = item.getSubMenu();
                subMenu.clearHeader();
            }
            if (item.getItemId() == R.id.m_unit_wise) {
                Toast.makeText(StudentsSubjectsDisplay.this, "GEt", Toast.LENGTH_SHORT).show();
            }
            if (item.getItemId() == R.id.m_topic_wise) {

            }


            //item.setTitle("Strategy");
            return true;


    }


}