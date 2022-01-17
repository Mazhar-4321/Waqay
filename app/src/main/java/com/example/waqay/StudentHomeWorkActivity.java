package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class StudentHomeWorkActivity extends AppCompatActivity {
    String year,subject,phone,topic;
    String end_date,start_date,name,checked,pdf_info,remarks;
    MaterialAutoCompleteTextView a_select_task;
    TextView t_home_work_name,t_home_work_start_date,t_home_work_end_date,t_home_work_status,t_home_work_remarks;
    MaterialButton b_upload;
    LinearLayout l_remarks;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private String topic1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_work);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        storageReference= FirebaseStorage.getInstance().getReference();
        t_home_work_name=(TextView)findViewById(R.id.t_home_work_name);
        t_home_work_start_date=(TextView)findViewById(R.id.t_home_work_start_date);
        t_home_work_end_date=(TextView)findViewById(R.id.t_home_work_end_date);
        t_home_work_status=(TextView)findViewById(R.id.t_home_work_status);
        t_home_work_remarks=(TextView)findViewById(R.id.t_home_work_remarks);
        b_upload=(MaterialButton)findViewById(R.id.b_upload);
        l_remarks=(LinearLayout)findViewById(R.id.l_remarks);
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


            ((TextView)v.findViewById(R.id.t_title)).setText(" HOMEWORK");

//assign the view to the actionbar
            this.getSupportActionBar().setCustomView(v);
        }
        end_date=getIntent().getStringExtra("end_date");
        start_date=getIntent().getStringExtra("start_date");
        name=getIntent().getStringExtra("name");
        checked=getIntent().getStringExtra("checked");
        pdf_info=getIntent().getStringExtra("pdf_info");
        remarks=getIntent().getStringExtra("remarks");
        phone=getIntent().getStringExtra("phone");
        topic1=getIntent().getStringExtra("topic");
        year=getIntent().getStringExtra("year");
        subject=getIntent().getStringExtra("subject");
        t_home_work_name.setText("HomeWork Name:"+name);
        t_home_work_end_date.setText("End Date:"+end_date);
        t_home_work_remarks.setText("Remarks:"+remarks);
        t_home_work_start_date.setText("Start Date:"+start_date);
          l_remarks.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(StudentHomeWorkActivity.this);
                  LayoutInflater inflater = StudentHomeWorkActivity.this.getLayoutInflater();
                View  row_view = inflater.inflate(R.layout.remarks,null);
                  TextView t_handwriting=row_view.findViewById(R.id.t_handwriting);
                  TextView  t_neatness=row_view.findViewById(R.id.t_neatness);
                  TextView      t_sentence_framing=row_view.findViewById(R.id.t_sentence_framing);
                  TextView t_creativity=row_view.findViewById(R.id.t_creativity);
                  TextView       t_presentation=row_view.findViewById(R.id.t_presentation);
                  TextView t_highlighters=row_view.findViewById(R.id.t_highlighters);
                  t_neatness.setText(getIntent().getStringExtra("t_neatness"));
                  t_sentence_framing.setText(getIntent().getStringExtra("t_sentence_framing"));
                  t_creativity.setText(getIntent().getStringExtra("t_creativity"));
                  t_presentation.setText(getIntent().getStringExtra("t_presentation"));
                  t_highlighters.setText(getIntent().getStringExtra("t_highlighters"));
                  t_handwriting.setText(getIntent().getStringExtra("t_handwriting"));
                  builder.setView(row_view);
                 builder.setTitle("Remarks");
                 if(!remarks.equals("NA"))
                 builder.show();

              }
          });
        if(pdf_info.equals("NA"))
        {
            b_upload.setVisibility(View.VISIBLE);
            t_home_work_status.setText("Status:"+checked);
        }
        else
        {
            b_upload.setVisibility(View.GONE);
            if(checked.equals("NA"))
            t_home_work_status.setText("Status:Pending");
            else
                t_home_work_status.setText("Status:"+checked);
        }
b_upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        selectPDFFile();
    }
});
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toast.makeText(KYCNewActivity.this,item.getItemId()+"",Toast.LENGTH_LONG).show();
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
         return true;
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

            StudentHomeWork studentHomeWork= new StudentHomeWork();

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate= formatter.format(date);

            studentHomeWork.date=strDate;
            studentHomeWork.pdf_info=url.toString();
            studentHomeWork.remarks="NA";
            studentHomeWork.checked="NA";
            FirebaseDatabase.getInstance().getReference("StudentHomeWork/"+year+"/"+subject+"/"+phone+"/"+topic1+"/"+name).setValue(studentHomeWork).addOnCompleteListener
                    (task -> {
                                Toast.makeText( StudentHomeWorkActivity.this,"HomeWork Uploaded Successfully",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        finish();;
                            }

                    );

        }).addOnFailureListener((OnFailureListener) e -> {Toast.makeText(StudentHomeWorkActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();progressBar.setVisibility(View.GONE);});



    }

}