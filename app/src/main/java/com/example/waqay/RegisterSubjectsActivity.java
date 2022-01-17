package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class RegisterSubjectsActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ArrayList<String> parent_headers = new ArrayList<>();
    ArrayList<Boolean> parent_headers_selected = new ArrayList<>();
    HashMap<String,ArrayList<String>> child_headers= new HashMap<>();
    HashMap<String,ArrayList<Boolean>> child_headers_selected= new HashMap<>();
    MaterialButton b_register_name_and_mobile;
    TextInputLayout textInputLayout,textInputLayout1,textInputLayout2;
    MaterialAutoCompleteTextView a_select_year_and_sem;
    TextInputEditText e_name,e_phone_number;
    LinearLayout l_subjects_top;
    ArrayList<SubjectsData> subjectsList= new ArrayList<>();
    HashMap<String,SubjectsData> hashMap = new HashMap<>();ArrayList<String> subjects_to_send;
    CheckBox checkBox;
    ChipGroup chipGroup;
    Status statusPapa=Status.getInstance();
    SubjectsAdapter subjectsAdapter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_subjects_waseem);

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
            ((TextView)v.findViewById(R.id.t_title)).setText("REGISTER SUBJECTS");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }
        FirebaseDatabase.getInstance().getReference("Years");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Years/");
        b_register_name_and_mobile=(MaterialButton)findViewById(R.id.b_register_name_and_mobile);
        textInputLayout=(TextInputLayout)findViewById(R.id.textInputLayout3);
        textInputLayout1=(TextInputLayout)findViewById(R.id.textInputLayout);
        textInputLayout2=(TextInputLayout)findViewById(R.id.textInputLayout1);
         checkBox=(CheckBox)findViewById(R.id.checkBox);
         chipGroup=(ChipGroup)findViewById(R.id.ch_chip_group);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren())
                {
                    System.out.println(postSnapshot.getKey());
                    SubjectsData post = postSnapshot.getValue(SubjectsData.class);
                    try {
                        subjectsList.add(post);
                        hashMap.put(postSnapshot.getKey(),post);
                        for(int i=0;i<post.SUBJECT.size();i++)
                        {
                            statusPapa.subjects_selected.put(post.SUBJECT.get(i),false);
                            statusPapa.subjects_to_semester.put(post.SUBJECT.get(i),postSnapshot.getKey());
                        }
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
        e_phone_number=(TextInputEditText)findViewById(R.id.e_phone_number);
        e_phone_number.setText( getIntent().getStringExtra("phone_number"));
        e_name=(TextInputEditText)findViewById(R.id.e_name);
        String[] select_customer_type= new String[]{"1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterSubjectsActivity.this,R.layout.drop_down_item,select_customer_type);
        a_select_year_and_sem.setAdapter(arrayAdapter);
        ListView l =(ListView)findViewById(R.id.l_subjects);
        l_subjects_top=(LinearLayout)findViewById(R.id.l_subjects_top);
        a_select_year_and_sem.setOnItemClickListener((parent, view, position, id) -> {
      try {
           subjects_to_send= hashMap.get(a_select_year_and_sem.getText().toString()).SUBJECT;
           int count=0;
          for(int i=0;i<subjects_to_send.size();i++)
          {
              if(!Status.getInstance().subjects_selected.containsKey(subjects_to_send.get(i)))
              Status.getInstance().subjects_selected.put(subjects_to_send.get(i),false);
              else
              {
                  if(Status.getInstance().subjects_selected.get(subjects_to_send.get(i)))
                  {
                      count++;
                  }
              }
          }
          if(count==subjects_to_send.size())
          {checkBox.setChecked(true);
          checkBox.setContentDescription("1");
          }
          else
          {checkBox.setChecked(false);checkBox.setContentDescription("0");}
          subjectsAdapter = new SubjectsAdapter(RegisterSubjectsActivity.this,subjects_to_send,checkBox,chipGroup);
          l.setAdapter(subjectsAdapter);

      }
         catch (Exception e)
         {
             e.printStackTrace();
         }
      checkBox.setOnClickListener(v -> {
          CheckBox c=(CheckBox)v;
          Status statusObject=Status.getInstance();
          if(Integer.parseInt(v.getContentDescription().toString())==0)
          {
             c.setChecked(true);
             c.setContentDescription("1");
              for(int i=0;i<subjects_to_send.size();i++)
              {
                  //status.set(i,false);
                  statusObject.subjects_selected.put(subjects_to_send.get(i),true);
              }
          }
          else
          {
              c.setChecked(false);
              c.setContentDescription("0");
              for(int i=0;i<subjects_to_send.size();i++)
              {

                  statusObject.subjects_selected.put(subjects_to_send.get(i),false);
              }
          }
          chipGroup.removeAllViews();
          Set<String> keys=   statusObject.subjects_selected.keySet();
          Iterator it=keys.iterator();
          while(it.hasNext())
          {
              String obj=(String)it.next();
              if( statusObject.subjects_selected.get(obj))
              {
                  Chip chip= new Chip(RegisterSubjectsActivity.this);
                  chip.setText(obj);
                  chip.setChipBackgroundColorResource(R.color.purple_500);

                  chip.setTextColor(getResources().getColor(R.color.white));
                  chip.setTextAppearance(R.style.AppTheme_GenderChip);
                  chipGroup.addView(chip);

              }
          }
          subjectsAdapter.change();

      });

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toast.makeText(KYCNewActivity.this,item.getItemId()+"",Toast.LENGTH_LONG).show();
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;
    }
    public void StoreDate(View view)
    {
        if(Status.getInstance().subjects_selected.size()==0)
        {
            Toast.makeText(RegisterSubjectsActivity.this,"Register Atleast One Subject",Toast.LENGTH_SHORT).show();
        }
        else
        {
          HashMap<String,Boolean> subjects_selected=  Status.getInstance().subjects_selected;
         Set<String> set= subjects_selected.keySet();
        Iterator it= set.iterator();
        HashMap<String,ArrayList<String>> sem= new HashMap<>();
        ArrayList<String> subjects= new ArrayList<>();
        String previous_sem=null;
        String subject_string="";
            FirebaseMessaging.getInstance().subscribeToTopic(e_phone_number.getText().toString()).addOnCompleteListener(task -> {});
        while(it.hasNext())
        {
            String subject=(String)it.next();
//            SubjectInfo subjectInfo= new SubjectInfo();
//            subjectInfo.subject_name=subject;
//            subjectInfo.completed="NA";
//            subjectInfo.running_unit="NA";
//            FirebaseDatabase.getInstance().getReference("SubjectInfo").child(subject).setValue(subjectInfo).addOnCompleteListener
//                    (task -> {
//                        //Toast.makeText( RegisterSubjectsActivity.this,"Subjects Registered Successfully",Toast.LENGTH_LONG).show();
//
//                    }
//
//                    );


          String current_sem=Status.getInstance().subjects_to_semester.get(subject);

          if(subjects_selected.get(subject))
          {

              if(previous_sem==null)
              {
                  previous_sem=current_sem;
                  FirebaseMessaging.getInstance().subscribeToTopic(previous_sem+"").addOnCompleteListener(task -> {});

              }
              if(!previous_sem.equals(current_sem))
              {
                  sem.put(previous_sem,subjects);
                  subjects= new ArrayList<>();
                  subjects.add(subject);
                  FirebaseMessaging.getInstance().subscribeToTopic(subject+"").addOnCompleteListener(task -> {});

                  subject_string=subject_string+subject+",";
                  previous_sem=current_sem;
                  FirebaseMessaging.getInstance().subscribeToTopic(previous_sem+"").addOnCompleteListener(task -> {});


              }
              else
              {
                  subjects.add(subject);subject_string=subject_string+subject+",";
                  FirebaseMessaging.getInstance().subscribeToTopic(subject+"").addOnCompleteListener(task -> {});
                  FirebaseMessaging.getInstance().subscribeToTopic(previous_sem+"").addOnCompleteListener(task -> {});

              }
          }

            //Toast.makeText(RegisterSubjectsActivity.this,current_sem,Toast.LENGTH_SHORT).show();
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(subject+"").addOnCompleteListener(task -> {
//                Toast.makeText(RegisterSubjectsActivity.this,"Success",Toast.LENGTH_SHORT).show();
//            });
          //  FirebaseMessaging.getInstance().subscribeToTopic(semester+"").addOnCompleteListener(task -> Toast.makeText(RegisterSubjectsActivity.this,"Success",Toast.LENGTH_SHORT).show());
        }
        sem.put(previous_sem,subjects);
            sem=sem;
            StudentsData studentsData= new StudentsData();
            studentsData.name=e_name.getText().toString();
            studentsData.subjects=sem;
            subject_string=subject_string;
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(RegisterSubjectsActivity.this);

            builder.setTitle("Confirm Action");
            builder.setMessage("Are You Sure You Want To Proceed With The following subjects:"+subject_string);


            builder.setPositiveButton("OK", (dialog, which) -> {
                e_name.setVisibility(View.GONE);
               View view1 = getWindow().getCurrentFocus();
               if(view1!=null)
               {
                   InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
               }
                e_phone_number.setVisibility(View.GONE);
                b_register_name_and_mobile.setVisibility(View.GONE);
                textInputLayout.setVisibility(View.VISIBLE);
                textInputLayout1.setVisibility(View.GONE);
                textInputLayout2.setVisibility(View.GONE);
                l_subjects_top.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                FirebaseDatabase.getInstance().getReference("StudentsData").child(e_phone_number.getText().toString()).setValue(studentsData).addOnCompleteListener
                        (new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task)
                             {
                                 Toast.makeText( RegisterSubjectsActivity.this,"Subjects Registered Successfully",Toast.LENGTH_LONG).show();
                                 finish();

                             }
                         }

                        );
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {


            });
            builder.show();



            Toast.makeText(RegisterSubjectsActivity.this,"hi",Toast.LENGTH_SHORT).show();
        }
    }

    public void storeNameAndMobile(View view)
    {
        if(e_phone_number.getText().toString().length()!=10)
        {
            Toast.makeText(RegisterSubjectsActivity.this,"Mobile Number Must Be Of 10 digits",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(e_name.getText().toString().length()<5)
            {
                Toast.makeText(RegisterSubjectsActivity.this,"Name Must Be Of 5 characters",Toast.LENGTH_SHORT).show();
            }
            else
            {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(RegisterSubjectsActivity.this);

                builder.setTitle("Confirm Action");
                builder.setMessage("Are You Sure You Want To Proceed With The Name And Mobile Number?");


                builder.setPositiveButton("OK", (dialog, which) -> {
                  // e_name.setVisibility(View.GONE);
                    View view1 = getCurrentFocus();
                    if(view1!=null)
                    {
                        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view1.getWindowToken(),0);
                    }
                    e_name.setVisibility(View.GONE);

                    e_phone_number.setVisibility(View.GONE);
                   b_register_name_and_mobile.setVisibility(View.GONE);
                   textInputLayout.setVisibility(View.VISIBLE);
                   textInputLayout1.setVisibility(View.GONE);
                   textInputLayout2.setVisibility(View.GONE);
                   l_subjects_top.setVisibility(View.VISIBLE);
                   checkBox.setVisibility(View.VISIBLE);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {


                });
                builder.show();
            }
        }
    }

}
/*l.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(RegisterSubjectsActivity.this,"clicked",Toast.LENGTH_SHORT).show();
            ConstraintLayout constraintLayout=(ConstraintLayout)view;
          CheckBox c=(CheckBox)  constraintLayout.getChildAt(2);
           // c.setChecked(!c.isChecked());

            if( c.isChecked())
         {
             c.setChecked(false);
             checkBox.setChecked(false);
           Status.getInstance().subjects_selected.put(  subjects_to_send.get(position),false);
         }
         else
         {
             Status.getInstance().subjects_selected.put(  subjects_to_send.get(position),true);
             int count=0;
             for(int i=0;i<subjects_to_send.size();i++)
             {
                 if(!Status.getInstance().subjects_selected.containsKey(subjects_to_send.get(i)))
                     Status.getInstance().subjects_selected.put(subjects_to_send.get(i),false);
                 else
                 {
                     if(Status.getInstance().subjects_selected.get(subjects_to_send.get(i)))
                     {
                         count++;
                     }
                 }
             }
             if(count==subjects_to_send.size())
                 checkBox.setChecked(true);
             c.setChecked(true);
         }
         // subjectsAdapter.change();
        });*/