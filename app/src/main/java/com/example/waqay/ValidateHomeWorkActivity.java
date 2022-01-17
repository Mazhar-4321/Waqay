package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ValidateHomeWorkActivity extends AppCompatActivity {

    MaterialAutoCompleteTextView a_select_year_and_sem,a_select_subject,a_select_unit,a_select_level,a_see_questions,a_select_student,a_select_home_work_topic;
    TextInputEditText e_topic,e_video_link,e_questions;
    MaterialButton b_plus,b_upload;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<String> questions_list = new ArrayList<>();
    HashMap<String,SubjectsData> hashMap = new HashMap<>();
    HashMap<String,HashMap<String,HashMap<String,TopicData>>> nested_hash_map= new HashMap<>();
    HashMap<String,HashMap<String,HashMap<String,HashMap<String,HashMap<String,StudentHomeWork>> >>>h_till_year_sem= new HashMap<>();
    HashMap<String,HashMap<String,HashMap<String,StudentHomeWork>>>     s_h= new HashMap<>();
    HashMap<String,HashMap<String,StudentHomeWork>>     t_h= new HashMap<>();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_home_work);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        FirebaseDatabase.getInstance().getReference("Years");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Years/");
        DatabaseReference reference1 = database.getReference("StudentHomeWork/");
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
            ((TextView)v.findViewById(R.id.t_title)).setText("VALIDATE HOMEWORK");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Uploads");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren())
                {
                    System.out.println(postSnapshot.getKey());
                    SubjectsData post = postSnapshot.getValue(SubjectsData.class);
                    try {
                        //subjectsList.add(post);
                        hashMap.put(postSnapshot.getKey(),post);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
//
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                h_till_year_sem= new HashMap<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren())
                {
                    String year_sem_key=postSnapshot.getKey();
                    HashMap<String,HashMap<String,HashMap<String,HashMap<String,StudentHomeWork>> >>h_till_subject= new HashMap<>();
                    for(DataSnapshot snapshot1:postSnapshot.getChildren())
                    {
                        String sub_key=snapshot1.getKey();

                        HashMap<String,HashMap<String,HashMap<String,StudentHomeWork>> >h_till_student_id= new HashMap<>();
                        for(DataSnapshot snapshot2 :snapshot1.getChildren())
                        {
                            String student_id_key=snapshot2.getKey();

                            HashMap<String,HashMap<String,StudentHomeWork>> h_till_topic= new HashMap<>();
                            for(DataSnapshot snapshot3 :snapshot2.getChildren())
                            {

                                String topic_key=snapshot3.getKey();
                                HashMap<String,StudentHomeWork> h_till_home_work=new HashMap<>();
                                for(DataSnapshot snapshot4 : snapshot3.getChildren())
                                {
                                    String home_work_key=snapshot4.getKey();
                                    StudentHomeWork studentHomeWork=snapshot4.getValue(StudentHomeWork.class);
                                    h_till_home_work.put(home_work_key,studentHomeWork);
                                }
                                h_till_topic.put(topic_key,h_till_home_work);
                            }
                           h_till_student_id.put(student_id_key,h_till_topic);
                        }
                        h_till_subject.put(sub_key,h_till_student_id);
                    }
//                    System.out.println(postSnapshot.getKey());
//                    SubjectsData post = postSnapshot.getValue(SubjectsData.class);
//                    try {
//                        //subjectsList.add(post);
//                        hashMap.put(postSnapshot.getKey(),post);
//
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                 h_till_year_sem(ye)
                    h_till_year_sem.put(year_sem_key,h_till_subject);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        a_select_year_and_sem=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_year_and_sem);

                a_select_home_work_topic=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_home_work_topic);
        a_select_student=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_student);
        String[] select_year_and_sem= new String[]{"1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ValidateHomeWorkActivity.this,R.layout.drop_down_item,select_year_and_sem);
        a_select_year_and_sem.setAdapter(arrayAdapter);

        a_select_subject=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_subject);
        a_select_unit=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_unit);
        String[] select_unit= new String[]{"1","2","3","4","5"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(ValidateHomeWorkActivity.this,R.layout.drop_down_item,select_unit);
        a_select_unit.setAdapter(arrayAdapter2);
        a_select_level=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_level);

        String[] select_level= new String[]{"Easy","Medium","Hard"};
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(ValidateHomeWorkActivity.this,R.layout.drop_down_item,select_level);
        a_select_level.setAdapter(arrayAdapter3);
        a_see_questions=(MaterialAutoCompleteTextView)findViewById(R.id.a_see_questions);
        /* AutoCompleteTextView Liseteners  */
        a_select_year_and_sem.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<String> subjects= new ArrayList<>();

            if(hashMap.containsKey(a_select_year_and_sem.getText().toString())) {
                subjects = hashMap.get(a_select_year_and_sem.getText().toString()).SUBJECT;
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(ValidateHomeWorkActivity.this, R.layout.drop_down_item, subjects);
                a_select_subject.setAdapter(arrayAdapter1);
            }

        });
        a_select_subject.setOnItemClickListener((parent, view, position, id) -> {
 String subject=a_select_subject.getText().toString();
 String year_sem=a_select_year_and_sem.getText().toString();
 HashMap<String,HashMap<String,HashMap<String,HashMap<String,StudentHomeWork>>>> f_h= h_till_year_sem.get(year_sem);
                s_h     =f_h.get(subject);
                                                                      Set<String> keys=      s_h.keySet();
                                                                      Iterator it=keys.iterator();
                                                                      ArrayList<String> arrayList= new ArrayList<>();
                                                                      while(it.hasNext())
                                                                      {
                                                                         arrayList.add( it.next().toString());
                                                                      }

            ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(ValidateHomeWorkActivity.this,R.layout.drop_down_item,arrayList);
            a_select_student.setAdapter(arrayAdapter4);

        });
        a_select_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String student_id=a_select_student.getText().toString();
                     t_h=s_h.get(student_id);
                Set<String> keys=      t_h.keySet();
                Iterator it=keys.iterator();
                ArrayList<String> arrayList= new ArrayList<>();
                while(it.hasNext())
                {
                    String obj=it.next().toString();
                    //if(t_h.get(obj).)
                    HashMap<String,StudentHomeWork>     fr_h = t_h.get(obj);
                    Set<String> keyss= fr_h.keySet();
                    Iterator itt= keyss.iterator();
                    String original_key="";
                    while(itt.hasNext())
                    {
                        original_key=itt.next().toString();
                    }
                    StudentHomeWork studentHomeWork= fr_h.get(original_key);
                    if(studentHomeWork.checked.equals("NA")) {
                        arrayList.add(obj);

                    }
                }

                ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(ValidateHomeWorkActivity.this,R.layout.drop_down_item,arrayList);
                a_select_home_work_topic.setAdapter(arrayAdapter4);


            }
        });
        a_select_home_work_topic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String home_work_topic= a_select_home_work_topic.getText().toString();
                HashMap<String,StudentHomeWork>     fr_h = t_h.get(home_work_topic);
                                         Set<String> keys= fr_h.keySet();
                                         Iterator it= keys.iterator();
                                         String original_key="";
                                         while(it.hasNext())
                                         {
                                             original_key=it.next().toString();
                                         }
                              StudentHomeWork studentHomeWork= fr_h.get(original_key);
                                         Intent intent= new Intent(ValidateHomeWorkActivity.this,GiveRatingActivity.class);
                                         intent.putExtra("year_sem",a_select_year_and_sem.getText().toString());
                                         intent.putExtra("subject",a_select_subject.getText().toString());
                                         intent.putExtra("student_id",a_select_student.getText().toString());
                                         intent.putExtra("topic",a_select_home_work_topic.getText().toString());
                                         intent.putExtra("home_work_name",original_key);
                                         intent.putExtra("checked",studentHomeWork.checked);
                                         intent.putExtra("date",studentHomeWork.date);
                                         intent.putExtra("pdf_info",studentHomeWork.pdf_info);
                                         intent.putExtra("remarks",studentHomeWork.remarks);
                                         a_select_year_and_sem.setText("");
                a_select_student.setText("");
                a_select_subject.setText("");
                a_select_home_work_topic.setText("");
                                         startActivity(intent);


            }
        });
        a_select_unit.setOnItemClickListener((parent, view, position, id) -> {

        });
        a_select_level.setOnItemClickListener((parent, view, position, id) -> {

        });
        a_see_questions.setOnItemClickListener((parent, view, position, id) -> {

        });
        /* AutoCompleteTextView Liseteners  */
        e_topic=(TextInputEditText)findViewById(R.id.e_topic);
        e_video_link=(TextInputEditText)findViewById(R.id.e_video_link);
        e_questions=(TextInputEditText)findViewById(R.id.e_questions);
        b_plus=(MaterialButton)findViewById(R.id.b_plus);
        b_upload=(MaterialButton)findViewById(R.id.b_upload);
        b_plus.setOnClickListener(v -> {
            if(e_questions.getText().toString().length()==0)
            {
                Toast.makeText(ValidateHomeWorkActivity.this,"Please Enter A Question",Toast.LENGTH_SHORT).show();
            }
            else
            {
                questions_list.add(e_questions.getText().toString());
                ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(ValidateHomeWorkActivity.this,R.layout.drop_down_item,questions_list);
                a_see_questions.setAdapter(arrayAdapter4);
                e_questions.setText("");
            }
        });
        b_upload.setOnClickListener(v -> {
            selectPDFFile();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        switch(item.getItemId())
        {

            case R.id.m_validate_homework:
                Intent intent = new Intent(ValidateHomeWorkActivity.this,ValidateHomeWorkActivity.class);
                startActivity(intent);
                break;
            case R.id.m_upload_homework :
                Intent intent1 = new Intent(ValidateHomeWorkActivity.this,UploadHomeWorkActivity.class);
                startActivity(intent1);
                break;




        }
        return super.onOptionsItemSelected(item) ;
    }
    private void selectPDFFile()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uploadFile(data.getData());
        }
    }

    private void uploadFile(Uri data) {
        String time=System.currentTimeMillis()+"";
        StorageReference reference = storageReference.child("uploads/" + time+ ".pdf");
        reference.putFile(data).addOnSuccessListener(taskSnapshot -> {
            // Get a URL to the uploaded content
            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
            while (!downloadUrl.isComplete()) ;
            Uri url = downloadUrl.getResult();

            TopicData topicData= new TopicData();
            topicData.level=a_select_level.getText().toString();
            topicData.questions_list=questions_list;
            topicData.video_link=e_video_link.getText().toString();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate= formatter.format(date);

            topicData.date=strDate;
            topicData.pdf_info=url.toString();
            FirebaseDatabase.getInstance().getReference("TopicData/"+a_select_year_and_sem.getText().toString()+"/"+a_select_subject.getText().toString()+"/"+a_select_unit.getText().toString()+"/"+e_topic.getText().toString()).setValue(topicData).addOnCompleteListener
                    (task -> {
                                Toast.makeText( ValidateHomeWorkActivity.this,"Topics data Registered Successfully",Toast.LENGTH_LONG).show();

                            }

                    );

        }).addOnFailureListener((OnFailureListener) e -> Toast.makeText(ValidateHomeWorkActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show());



    }


}