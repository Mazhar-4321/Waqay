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
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {
MaterialAutoCompleteTextView a_select_year_and_sem,a_select_subject,a_select_unit,a_select_level,a_see_questions;
TextInputEditText e_topic,e_video_link,e_questions;
MaterialButton b_plus,b_upload,b_attendance,btn_acknowledgement,btn_notes,btn_important_questions;
StorageReference storageReference;
DatabaseReference databaseReference,ref1;
ArrayList<String> questions_list = new ArrayList<>();
HashMap<String,SubjectsData> hashMap = new HashMap<>();
ArrayList<String> mobile_list= new ArrayList<>();
HashMap<String,StudentsData> h_students_data= new HashMap<>();
TextInputLayout textInputLayout,textInputLayout1333,textInputLayoutT;
ProgressBar progressBar;
LinearLayout ll_main;
int attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        textInputLayout=(TextInputLayout)findViewById(R.id.textInputLayout);
        textInputLayout1333=(TextInputLayout)findViewById(R.id.textInputLayout1333);
        textInputLayoutT=(TextInputLayout)findViewById(R.id.textInputLayoutT);
        FirebaseDatabase.getInstance().getReference("Years");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Years/");
        DatabaseReference reference1;
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
            ((TextView)v.findViewById(R.id.t_title)).setText("UPLOAD NOTES");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Uploads");
reference1=database.getReference("StudentsData/");
        reference1.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren())
                {
                   String key=postSnapshot.getKey();
                   h_students_data.put(key,postSnapshot.getValue(StudentsData.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

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
        a_select_year_and_sem=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_year_and_sem);
        String[] select_year_and_sem= new String[]{"1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AdminActivity.this,R.layout.drop_down_item,select_year_and_sem);
        a_select_year_and_sem.setAdapter(arrayAdapter);

        a_select_subject=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_subject);
        a_select_unit=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_unit);
        String[] select_unit= new String[]{"1","2","3","4","5"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(AdminActivity.this,R.layout.drop_down_item,select_unit);
        a_select_unit.setAdapter(arrayAdapter2);
        a_select_level=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_level);

        String[] select_level= new String[]{"Easy","Medium","Hard"};
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(AdminActivity.this,R.layout.drop_down_item,select_level);
        a_select_level.setAdapter(arrayAdapter3);
        a_see_questions=(MaterialAutoCompleteTextView)findViewById(R.id.a_see_questions);
        /* AutoCompleteTextView Liseteners  */
        a_select_year_and_sem.setOnItemClickListener((parent, view, position, id) -> {
          ArrayList<String> subjects= new ArrayList<>();

          if(hashMap.containsKey(a_select_year_and_sem.getText().toString())) {
              subjects = hashMap.get(a_select_year_and_sem.getText().toString()).SUBJECT;
              ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(AdminActivity.this, R.layout.drop_down_item, subjects);
              a_select_subject.setAdapter(arrayAdapter1);
          }

        });
        a_select_subject.setOnItemClickListener((parent, view, position, id) -> {

        });
        a_select_unit.setOnItemClickListener((parent, view, position, id) -> {
         if(b_upload.getVisibility()!=View.VISIBLE)
         {
             ref1=FirebaseDatabase.getInstance().getReference("Questions/"+a_select_subject.getText().toString()+"/"+a_select_unit.getText().toString());
             ref1.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                   Acknowledgement acknowledgement=snapshot.getValue(Acknowledgement.class);
                   if(acknowledgement!=null)
                   mobile_list=acknowledgement.questions;
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });
         }
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
        b_attendance=(MaterialButton)findViewById(R.id.b_attendance);
        btn_notes=(MaterialButton)findViewById(R.id.btn_notes);
        btn_important_questions=(MaterialButton)findViewById(R.id.btn_questions);
        ll_main=(LinearLayout)findViewById(R.id.ll_main);
        btn_notes.setOnClickListener(v -> {
            ll_main.setVisibility(View.VISIBLE);
            btn_acknowledgement.setVisibility(View.GONE);
            btn_notes.setVisibility(View.GONE);
            btn_important_questions.setVisibility(View.GONE);
        });

        btn_acknowledgement=(MaterialButton)findViewById(R.id.btn_acknowledgement);
        btn_acknowledgement.setOnClickListener(v -> {
            if(e_questions.getText().toString().length()==0)
            {
                Toast.makeText(AdminActivity.this,"Please Enter A Question",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Acknowledgement acknowledgement = new Acknowledgement();
                mobile_list.addAll(questions_list);
                acknowledgement.questions=mobile_list;
//                questions_list.add(e_questions.getText().toString());
//                ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(AdminActivity.this,R.layout.drop_down_item,questions_list);
//                a_see_questions.setAdapter(arrayAdapter4);
//                e_questions.setText("");
//
//                ///////////
//                TopicData topicData= new TopicData();
//                topicData.level=a_select_level.getText().toString();
//                topicData.questions_list=questions_list;
//                topicData.video_link=e_video_link.getText().toString();
//                Date date = new Date();
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                String strDate= formatter.format(date);
//
//                topicData.date=strDate;
//                //topicData.pdf_info=url.toString();
                FirebaseDatabase.getInstance().getReference("Questions/"+a_select_subject.getText().toString()+"/"+a_select_unit.getText().toString()).setValue(acknowledgement).addOnCompleteListener
                        (task -> {
                                    Toast.makeText( AdminActivity.this,"Topics data Registered Successfully",Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                        );


















                ////////////
            }
        });
        b_attendance.setOnClickListener(v -> {
  attendance();
        });
        b_upload=(MaterialButton)findViewById(R.id.b_upload);
        b_plus.setOnClickListener(v -> {
   if(e_questions.getText().toString().length()==0)
   {
       Toast.makeText(AdminActivity.this,"Please Enter A Question",Toast.LENGTH_SHORT).show();
   }
   else
   {
       questions_list.add(e_questions.getText().toString());
       ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(AdminActivity.this,R.layout.drop_down_item,questions_list);
       a_see_questions.setAdapter(arrayAdapter4);
       e_questions.setText("");
   }
        });
        b_upload.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
         selectPDFFile();
        });
        btn_important_questions.setOnClickListener(v -> {
            ll_main.setVisibility(View.VISIBLE);

            btn_notes.setVisibility(View.GONE);
            btn_important_questions.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            textInputLayout1333.setVisibility(View.GONE);
            a_select_level.setVisibility(View.GONE);
            e_video_link.setVisibility(View.GONE);
            b_attendance.setVisibility(View.GONE);
            b_upload.setVisibility(View.GONE);
            textInputLayoutT.setVisibility(View.GONE);

        });
    }

    private void attendance()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        switch(item.getItemId())
        {

            case R.id.m_validate_homework:
                 Intent intent = new Intent(AdminActivity.this,ValidateHomeWorkActivity.class);
                 startActivity(intent);
                break;
            case R.id.m_upload_homework :
                Intent intent1 = new Intent(AdminActivity.this,UploadHomeWorkActivity.class);
                startActivity(intent1);
                break;
            case R.id.m_upload_question_paper :
                Intent intent2 = new Intent(AdminActivity.this,UploadQuestionsActivity.class);
                startActivity(intent2);
                break;

            case R.id.m_questions :
                Intent intent3 = new Intent(AdminActivity.this,UploadAcknowledgement.class);
                startActivity(intent3);
                break;
            case R.id.m_unit_wise :
                Intent intent33 = new Intent(AdminActivity.this,UnitWiseStrategyActivity.class);
                startActivity(intent33);
                break;
            case R.id.m_upload_profile :
                Intent intent334 = new Intent(AdminActivity.this,UpdateProfileActivity.class);
                startActivity(intent334);
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
                                Toast.makeText( AdminActivity.this,"Topics data Registered Successfully",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                            }

                    );

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                 Toast.makeText(AdminActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
            }
        });




    }


}