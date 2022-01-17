package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

public class AcknowledgementActivity extends AppCompatActivity {
ListView lv_acknowledgement;
MaterialButton btn_show_questions,btn_submit;
    Acknowledgement acknowledgement;
    AcknowledgementAdapter acknowledgementAdapter;
    StudentAcknowledgementFirebase studentAcknowledgementFirebase= new StudentAcknowledgementFirebase();
    HashMap<String,String> student_hashmap= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
            ((TextView)v.findViewById(R.id.t_title)).setText("Important Questions");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }
        setContentView(R.layout.activity_acknowledgement);
        lv_acknowledgement=(ListView)findViewById(R.id.l_acknowledgement);
        TextView textView=(TextView)findViewById(R.id.tv_unit);

        btn_show_questions=(MaterialButton)findViewById(R.id.btn_show_questions);
        btn_submit=(MaterialButton)findViewById(R.id.btn_submit);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String unit=getIntent().getStringExtra("unit");
        String subject=getIntent().getStringExtra("subject");
        String mobile=getIntent().getStringExtra("mobile");
        textView.setText(subject+"-"+"UNIT"+unit);
        DatabaseReference reference = database.getReference("Questions/"+subject+"/"+unit);
        DatabaseReference reference1 = database.getReference("StudentAcknowledgement/"+mobile+"/"+subject+"/"+unit);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                acknowledgement=snapshot.getValue(Acknowledgement.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentAcknowledgementFirebase = snapshot.getValue(StudentAcknowledgementFirebase.class);
               if(studentAcknowledgementFirebase!=null) student_hashmap=studentAcknowledgementFirebase.student_acknowledgement_hashmap;
//                if(snapshot!=null) {
//                    studentAcknowledgementFirebase = snapshot.getValue(StudentAcknowledgementFirebase.class);
//                    if (studentAcknowledgementFirebase == null) {
//                        HashMap<String, String> hashMap = new HashMap<>();
//                        StudentAcknowledgement.getInstance().student_acknowledgement_hashmap = hashMap;
//                    } else
//                        StudentAcknowledgement.getInstance().student_acknowledgement_hashmap = studentAcknowledgementFirebase.student_acknowledgement_hashmap;
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        btn_submit.setOnClickListener(v -> {
            StudentAcknowledgementFirebase studentAcknowledgementFirebase = new StudentAcknowledgementFirebase();
            studentAcknowledgementFirebase.student_acknowledgement_hashmap=acknowledgementAdapter.student_acknowledgement_hashmap;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference= firebaseDatabase.getReference("StudentAcknowledgement/"+mobile+"/"+subject+"/"+unit);
            databaseReference.setValue(studentAcknowledgementFirebase)
                    .addOnCompleteListener
                            (task -> {
                                        Toast.makeText( AcknowledgementActivity.this,"Acknowledgement Uploaded Successfully",Toast.LENGTH_LONG).show();
                                        finish();
                                       // progressBar.setVisibility(View.GONE);
                                    }

                            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AcknowledgementActivity.this,"Acknowledgement Uploading Failed",Toast.LENGTH_LONG).show();

                    //progressBar.setVisibility(View.GONE);
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast.makeText( AcknowledgementActivity.this,"Acknowledgement Uploading Failed",Toast.LENGTH_LONG).show();
                   // progressBar.setVisibility(View.GONE);
                }
            });
        });

        btn_show_questions.setOnClickListener(v -> {
            if(acknowledgement!=null&& acknowledgement.questions!=null&&acknowledgement.questions.size()>0) {
                acknowledgementAdapter = new AcknowledgementAdapter(this, acknowledgement.questions, student_hashmap);

                lv_acknowledgement.setAdapter(acknowledgementAdapter);
                btn_show_questions.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(AcknowledgementActivity.this,"Imp Questions Not Yet Uploaded...",Toast.LENGTH_LONG).show();
            }


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

}