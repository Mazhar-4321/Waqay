package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
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

public class UploadQuestionsActivity extends AppCompatActivity {
    MaterialAutoCompleteTextView a_select_year_and_sem, a_select_subject, a_select_unit, a_select_topic, a_see_questions;
    TextInputEditText e_topic, e_video_link, e_questions, e_end_date;
    MaterialButton b_plus, b_upload, b_plus_plus;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<String> questions_list = new ArrayList<>();
    HashMap<String, SubjectsData> hashMap = new HashMap<>();
    HashMap<String,HashMap<String,QuestionPaper>> existing_question_papers_map = new HashMap<>();
    HashMap<String,QuestionPaper> questionPaperHashMap = new HashMap<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_questions);
        try {


            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            FirebaseDatabase.getInstance().getReference("Years");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Years/");
            DatabaseReference reference1 = database.getReference("QuestionPapers/");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        System.out.println(postSnapshot.getKey());
                        SubjectsData post = postSnapshot.getValue(SubjectsData.class);
                        try {
                            //subjectsList.add(post);
                            hashMap.put(postSnapshot.getKey(), post);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setCustomView(R.layout.toolbar);
                View v = getSupportActionBar().getCustomView();
                TextView t_title = v.findViewById(R.id.t_title);
                t_title.setText("Pasha");
                this.getSupportActionBar().setDisplayShowCustomEnabled(true);
                this.getSupportActionBar().setDisplayShowTitleEnabled(false);
                LayoutInflater inflator = LayoutInflater.from(this);
                View v1 = inflator.inflate(R.layout.toolbar, null);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
                ((TextView) v.findViewById(R.id.t_title)).setText("UPLOAD QUESTION PAPER");

//assign the view to the actionbar
                this.getSupportActionBar().setCustomView(v);
            }
            storageReference = FirebaseStorage.getInstance().getReference();
            databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {


                        String key = snapshot.getKey();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            String key1 = postSnapshot.getKey();
                            for (DataSnapshot snapshot1 : postSnapshot.getChildren()) {
                                QuestionPaper questionPaper = snapshot1.getValue(QuestionPaper.class);
                                String key2 = snapshot1.getKey();
                                questionPaperHashMap.put(key2, questionPaper);
                            }

                        }
                    }
                    catch (Exception e)
                    {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            a_select_year_and_sem = (MaterialAutoCompleteTextView) findViewById(R.id.a_select_year_and_sem);
            String[] select_year_and_sem = new String[]{"1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UploadQuestionsActivity.this, R.layout.drop_down_item, select_year_and_sem);
            a_select_year_and_sem.setAdapter(arrayAdapter);
            a_select_subject = (MaterialAutoCompleteTextView) findViewById(R.id.a_select_subject);
            a_select_year_and_sem.setOnItemClickListener((parent, view, position, id) -> {
                ArrayList<String> subjects = new ArrayList<>();

                if (hashMap.containsKey(a_select_year_and_sem.getText().toString())) {
                    subjects = hashMap.get(a_select_year_and_sem.getText().toString()).SUBJECT;
                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(UploadQuestionsActivity.this, R.layout.drop_down_item, subjects);
                    a_select_subject.setAdapter(arrayAdapter1);
                }

            });
            b_upload = (MaterialButton) findViewById(R.id.b_upload);
            b_upload.setOnClickListener(v -> {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                selectPDFFile();
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

        private void selectPDFFile () {
        try {


            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);
        }
        catch(Exception e)
        {

        }
        }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        try {


            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                uploadFile(data.getData());
            }
        }
        catch(Exception e)
        {

        }
        }

        private void uploadFile (Uri data) {
            try {


                String time = System.currentTimeMillis() + "";
                StorageReference reference = storageReference.child("uploads/" + time + ".pdf");
                reference.putFile(data).addOnSuccessListener(taskSnapshot -> {
                    // Get a URL to the uploaded content
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                    while (!downloadUrl.isComplete()) ;
                    Uri url = downloadUrl.getResult();

//            TopicData topicData= new TopicData();
//            topicData.level=a_select_level.getText().toString();
//            topicData.questions_list=questions_list;
//            topicData.video_link=e_video_link.getText().toString();
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            String strDate= formatter.format(date);
//
//            topicData.date=strDate;
//            topicData.pdf_info=url.toString();
                    QuestionPaper questionPaper = questionPaperHashMap.get(a_select_subject.getText().toString()) != null ? questionPaperHashMap.get(a_select_subject.getText().toString()) : new QuestionPaper();
                    ArrayList<String> question = new ArrayList<>();
                    question.add(url.toString());
                    if (questionPaper.pdf_info != null) {
                        questionPaper.pdf_info.add(url.toString());
                    } else {
                        questionPaper.pdf_info = question;
                    }
                    //questionPaper.pdf_info!=null?questionPaper.pdf_info.add(url.toString()):question;
                    FirebaseDatabase.getInstance().getReference("QuestionPapers/" + a_select_year_and_sem.getText().toString() + "/" + a_select_subject.getText().toString()).setValue(questionPaper).addOnCompleteListener
                            (task -> {
                                        Toast.makeText(UploadQuestionsActivity.this, "Question Paper Uploaded Successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                            );

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadQuestionsActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            } catch (Exception e) {

            }
        }
    }
