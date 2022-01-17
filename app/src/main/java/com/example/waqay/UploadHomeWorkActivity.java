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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.andexert.library.RippleView;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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

public class UploadHomeWorkActivity extends AppCompatActivity {

    MaterialAutoCompleteTextView a_select_year_and_sem,a_select_subject,a_select_unit,a_select_topic,a_see_questions;
    TextInputEditText e_topic,e_video_link,e_questions,e_end_date;
    MaterialButton b_plus,b_upload,b_plus_plus;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<String> questions_list = new ArrayList<>();
    HashMap<String,SubjectsData> hashMap = new HashMap<>();
    ProgressBar progressBar;
   // RippleView rippleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_home_work);
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;
       // rippleView=(RippleView)findViewById(R.id.more);
        FirebaseDatabase.getInstance().getReference("Years");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Years/");
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
            ((TextView)v.findViewById(R.id.t_title)).setText("UPLOAD HOMEWORK");

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
        a_select_year_and_sem=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_year_and_sem);
        String[] select_year_and_sem= new String[]{"1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UploadHomeWorkActivity.this,R.layout.drop_down_item,select_year_and_sem);
        a_select_year_and_sem.setAdapter(arrayAdapter);

        a_select_subject=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_subject);
        a_select_unit=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_unit);
        String[] select_unit= new String[]{"1","2","3","4","5"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(UploadHomeWorkActivity.this,R.layout.drop_down_item,select_unit);
        a_select_unit.setAdapter(arrayAdapter2);
        a_select_topic=(MaterialAutoCompleteTextView)findViewById(R.id.a_select_topic);

        String[] select_level= new String[]{"Easy","Medium","Hard"};
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(UploadHomeWorkActivity.this,R.layout.drop_down_item,select_level);
        a_select_topic.setAdapter(arrayAdapter3);
        a_see_questions=(MaterialAutoCompleteTextView)findViewById(R.id.a_see_questions);
        /* AutoCompleteTextView Liseteners  */
        a_select_year_and_sem.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<String> subjects= new ArrayList<>();

            if(hashMap.containsKey(a_select_year_and_sem.getText().toString())) {
                subjects = hashMap.get(a_select_year_and_sem.getText().toString()).SUBJECT;
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(UploadHomeWorkActivity.this, R.layout.drop_down_item, subjects);
                a_select_subject.setAdapter(arrayAdapter1);
            }

        });
        a_select_subject.setOnItemClickListener((parent, view, position, id) -> {

        });
        a_select_unit.setOnItemClickListener((parent, view, position, id) -> {
            DatabaseReference referenceTopics = database.getReference("TopicData/"+a_select_year_and_sem.getText().toString()+"/"+a_select_subject.getText().toString()+"/"+a_select_unit.getText().toString());
            referenceTopics.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> t= new ArrayList<>();
                    for (DataSnapshot postSnapshot: snapshot.getChildren())
                    {
                        System.out.println(postSnapshot.getKey());
                        t.add(postSnapshot.getKey());
//
                    }
                    //String[] select_level= new String[]{"Easy","Medium","Hard"};
                    ArrayAdapter<String> arrayAdapter33 = new ArrayAdapter<>(UploadHomeWorkActivity.this,R.layout.drop_down_item,t);
                    a_select_topic.setAdapter(arrayAdapter33);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        });
        a_select_topic.setOnItemClickListener((parent, view, position, id) -> {

        });
        a_see_questions.setOnItemClickListener((parent, view, position, id) -> {

        });
        /* AutoCompleteTextView Liseteners  */
        e_topic=(TextInputEditText)findViewById(R.id.e_topic);
        e_end_date=(TextInputEditText)findViewById(R.id.e_end_date);
        e_video_link=(TextInputEditText)findViewById(R.id.e_video_link);
        e_questions=(TextInputEditText)findViewById(R.id.e_questions);
        b_plus=(MaterialButton)findViewById(R.id.b_plus);
        b_plus_plus=(MaterialButton)findViewById(R.id.b_plus_plus);
        b_upload=(MaterialButton)findViewById(R.id.b_upload);
        b_plus.setOnClickListener(v -> {
            MaterialDatePicker.Builder builder= MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Select Start Date");
            MaterialDatePicker materialDatePicker= builder.build();
            materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
           // Date d = new Date();

            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");

            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener()
            {
                @Override
                public void onPositiveButtonClick(Object selection) {

                    e_questions.setText(materialDatePicker.getHeaderText());
                    materialDatePicker.dismiss();
                }
            });

                  // e_questions.setText("");

        });
        b_plus_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder builder= MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select Start Date");
                MaterialDatePicker materialDatePicker= builder.build();
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        e_end_date.setText(materialDatePicker.getHeaderText());
                        materialDatePicker.dismiss();
                    }
                });
            }
        });

            // e_questions.setText("");


        b_upload.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            HomeWork homeWork= new HomeWork();
            homeWork.name=e_topic.getText().toString();
            homeWork.start_date=e_questions.getText().toString();
            homeWork.end_date=e_end_date.getText().toString();
            homeWork.topic=a_select_topic.getText().toString();
            FirebaseDatabase.getInstance().getReference("HomeWork/"+a_select_subject.getText().toString()+"/"+a_select_topic.getText().toString()).setValue(homeWork)
                    .addOnCompleteListener
                    (task -> {
                                Toast.makeText( UploadHomeWorkActivity.this,"HomeWork Uploaded Successfully",Toast.LENGTH_LONG).show();
                                   progressBar.setVisibility(View.GONE);
                            }

                    ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( UploadHomeWorkActivity.this,"HomeWork Uploaded Failed",Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast.makeText( UploadHomeWorkActivity.this,"HomeWork Uploaded Failed",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }


        return super.onOptionsItemSelected(item) ;
    }





}