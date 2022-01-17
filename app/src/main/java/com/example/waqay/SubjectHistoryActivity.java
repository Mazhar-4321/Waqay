package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
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

public class SubjectHistoryActivity extends AppCompatActivity {
MaterialAutoCompleteTextView a_select_year_and_sem;
TextInputLayout textInputLayout;
int home_work_dialog;
GridView g_grid_view;
    String phone_number;
    HashMap<String, ArrayList<String>> subjects= new HashMap<>();
    StudentsData studentsData = new StudentsData();
    HashMap<String,SubjectInfo> subjectMap= new HashMap<>();
    ArrayList<SubjectInfo> subjectInfoArrayList=new ArrayList<>();
    HashMap<String,String> home_works_done = new HashMap<>();
    HashMap<String,HashMap<String,HomeWork>> pending_home_works_map= new HashMap<>();
    HashMap<String,HashMap<String,HashMap<String,HashMap<String,HashMap<String,StudentHomeWork>>>>> student_home_works= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_history);
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
































       textInputLayout=(TextInputLayout)findViewById(R.id.textInputLayout3);
        getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        );
//        textInputLayout.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//        textInputLayout.set
//        ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(textInputLayout.getWindowToken(),0);
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


            ((TextView)v.findViewById(R.id.t_title)).setText("SELECT SUBJECT");


            this.getSupportActionBar().setCustomView(v);
        }
         phone_number = getIntent().getStringExtra("number");
        a_select_year_and_sem = (MaterialAutoCompleteTextView) findViewById(R.id.a_select_year_and_sem);
//        ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(a_select_year_and_sem.getWindowToken(),0);
        String[] select_customer_type = new String[]{"1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2"};



//        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SubjectHistoryActivity.this, R.layout.drop_down_item, select_customer_type);
        a_select_year_and_sem.setAdapter(arrayAdapter);
        try{
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.running_unit = "";
        subjectInfo.completed = "";
        subjectInfo.subject_name = "";
        subjectInfoArrayList.add(subjectInfo);
    }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();;
        }
        g_grid_view=(GridView)findViewById(R.id.g_grid_view);


        a_select_year_and_sem.setOnItemClickListener((parent, view, position, id) -> {
String year_sem=a_select_year_and_sem.getText().toString();
ArrayList<Notification> notifications = new ArrayList<>();
Set<String> keyset = pending_home_works_map.keySet();//c++,DS,.....
            ArrayList<String> subjects_choosen =new ArrayList<>();
            for(int i=0;i<select_customer_type.length;i++)
            {
                ArrayList<String> al = new ArrayList<>();
               al= studentsData.subjects.get(select_customer_type[i]);
               try {
                   if(al!=null)
                   subjects_choosen.addAll(al);
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
            }

Iterator it= keyset.iterator();
while(it.hasNext())
{
    String obj=(String)it.next();
    if(subjects_choosen.contains(obj)) {
        HashMap<String, HomeWork> check_home_work = pending_home_works_map.get(obj);
        Set<String> topic_keys = check_home_work.keySet();
        Iterator it_topic = topic_keys.iterator();
        while (it_topic.hasNext()) {
            String topic = (String) it_topic.next();
            if(!home_works_done.containsKey(topic))
            {
                HomeWork homeWork = check_home_work.get(topic);
                Notification notification = new Notification();
                notification.home_work_name=homeWork.name;
                notification.topic=homeWork.topic;
                notification.languauge=obj;
                notifications.add(notification);
            }

        }
    }

}
HashMap<String,HomeWork> check_home_work= pending_home_works_map.get(year_sem);
ArrayList<HomeWork> homeWorkList = new ArrayList<>();

            if(studentsData.subjects.containsKey( a_select_year_and_sem.getText().toString())) {
                subjectInfoArrayList= new ArrayList<>();

             ArrayList<String> subject_choosen=   studentsData.subjects.get(a_select_year_and_sem.getText().toString());
                for(int i=0;i<subject_choosen.size();i++)
                {
                    subjectInfoArrayList.add(subjectMap.get(subject_choosen.get(i)));
                   // homeWorkList.add(check_home_work.get(subjectMap.get(subject_choosen.get(i))));

                }
                if(notifications.size()==0 || home_work_dialog!=0) {
                    g_grid_view.setVisibility(View.VISIBLE);
                    GridAdapter gridAdapter1 = new GridAdapter(SubjectHistoryActivity.this, subjectInfoArrayList);
                    g_grid_view.setAdapter(gridAdapter1);
                }
                else
                {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(SubjectHistoryActivity.this);
                    LayoutInflater inflater = SubjectHistoryActivity.this.getLayoutInflater();
                    View  row_view = inflater.inflate(R.layout.notification,null);
                    builder.setView(row_view);
                    ListView listView =(ListView)row_view.findViewById(R.id.list_view);
                    ArrayList<Notification> arrayList = new ArrayList<>();

                    ListAdapter listAdapter = new ListAdapter(SubjectHistoryActivity.this,notifications);
                    listView.setAdapter(listAdapter);
                    builder.setTitle("Pending HomeWorks");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", (dialog, which) -> {
                                                   home_work_dialog=1;a_select_year_and_sem.setText("");
                        }



                    );
                    builder.setNegativeButton("Cancel",(dialog, which) -> {home_work_dialog=1;a_select_year_and_sem.setText("");});
                    builder.show();

                }

            }
            else
            {
                subjectInfoArrayList= new ArrayList<>();
                GridAdapter gridAdapter1 = new GridAdapter(SubjectHistoryActivity.this,subjectInfoArrayList );
                g_grid_view.setAdapter(gridAdapter1);

                Toast.makeText(SubjectHistoryActivity.this,"Not Registered For The Sem",Toast.LENGTH_SHORT).show();
            }

        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("StudentsData/"+phone_number);
        DatabaseReference reference1 = database.getReference("SubjectInfo");
        DatabaseReference reference2 = database.getReference("HomeWork/");
        DatabaseReference reference3 = database.getReference("StudentHomeWork/");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                 String key=postSnapshot.getKey();
                 subjectMap.put(key,postSnapshot.getValue(SubjectInfo.class));



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentsData post = snapshot.getValue(StudentsData.class);
               studentsData=post;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pending_home_works_map= new HashMap<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String key=dataSnapshot.getKey();
                    HashMap<String,HomeWork> hmap= new HashMap<>();
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        String key1=dataSnapshot1.getKey();
                        HomeWork homeWork=dataSnapshot1.getValue(HomeWork.class);

                        hmap.put(key1,homeWork);
                    }
                    pending_home_works_map.put(key,hmap);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
         student_home_works = new HashMap<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
               String year_sem=dataSnapshot.getKey();//2-1
               for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
               {
                   String subject=dataSnapshot1.getKey();//c++
                   for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                       String id = dataSnapshot2.getKey();//6302014432
                       if (id.equals(phone_number))
                       {
                           for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                               String topic = dataSnapshot3.getKey();//Operators
                               home_works_done.put(topic,topic);
//                               for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
//                                   String home_work = dataSnapshot4.getKey();
//                                   StudentHomeWork studentHomeWork = dataSnapshot4.getValue(StudentHomeWork.class);
//                               }
                           }
                       }
                   }
               }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        g_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subject=(String)view.getTag();
                String sem=a_select_year_and_sem.getText().toString();
                Toast.makeText(SubjectHistoryActivity.this, a_select_year_and_sem.getText().toString()+"/"+subject+"",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(SubjectHistoryActivity.this,StudentsSubjectsDisplay.class);
                intent.putExtra("year",sem);
                intent.putExtra("subject",subject);
                intent.putExtra("phone",phone_number);
                startActivity(intent);

            }
        });
    }

    public void upload(View view) {
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}