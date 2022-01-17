package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewLoginActivity extends AppCompatActivity {

    TextInputEditText e_phone_number;
    MaterialButton b_login,b_sign_up,b_admin;
    FirebaseAuth o_firebaseauth;
    ProgressBar p_loader;
    LinearLayout l_loader;
    AdminsList adminsList= new AdminsList();
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login_waseem);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference1 = database.getReference("AdminsList");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    adminsList =snapshot.getValue(AdminsList.class);








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(NewLoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);

        e_phone_number=(TextInputEditText)findViewById(R.id.e_phone_number);
        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", NewLoginActivity.MODE_PRIVATE);
        String Astatus = prfs.getString("mobile", "");
        if(Astatus.length()>0)
        {
            e_phone_number.setText(Astatus);
            e_phone_number.setEnabled(false);
        }


        b_login=(MaterialButton)findViewById(R.id.b_login);
        b_sign_up=(MaterialButton)findViewById(R.id.b_sign_up);
        b_admin=(MaterialButton)findViewById(R.id.b_admin);
        Drawable showDrawable = AppCompatResources.getDrawable(NewLoginActivity.this, R.drawable.admin);
        b_admin.setIcon(showDrawable);
        b_login.setTransformationMethod(null);
        b_sign_up.setTransformationMethod(null);
        b_admin.setTransformationMethod(null);
        p_loader=(ProgressBar)findViewById(R.id.progressBar);
        l_loader=(LinearLayout)findViewById(R.id.linear_layout);
        ProgressBar spinner = new android.widget.ProgressBar(
                NewLoginActivity.this,
                null,
                android.R.attr.progressBarStyle);

        spinner.getIndeterminateDrawable().setColorFilter(0x000000, android.graphics.PorterDuff.Mode.MULTIPLY);
        o_firebaseauth = FirebaseAuth.getInstance();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setCustomView(R.layout.toolbar);
            View v=  getSupportActionBar().getCustomView();
            //TextView t_title= v.findViewById(R.id.t_title);
            //t_title.setText("Pasha");
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            LayoutInflater inflator = LayoutInflater.from(this);
            View v1 = inflator.inflate(R.layout.toolbar, null);


            ((TextView)v1.findViewById(R.id.t_title)).setText("LOGIN");


            this.getSupportActionBar().setCustomView(v1);
        }

        try {



            b_login.setOnClickListener(v -> {
                if (e_phone_number.getText().toString().length() == 10) {
                    String password = "123456";
                    p_loader.setVisibility(View.VISIBLE);
                    l_loader.setVisibility(View.VISIBLE);
                    //o_firebaseauth = FirebaseAuth.getInstance();
                    o_firebaseauth.signInWithEmailAndPassword(e_phone_number.getText().toString().trim() + "@gmail.com", password.trim()).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            String s = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database1.getReference("Users/" + s);
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    FirebaseUserData userData = snapshot.getValue(FirebaseUserData.class);
                                    if ((userData.android_id.equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))||!(userData.parent_android_id.equals("Not Necessary")))&&userData.isActive.equals("1"))
                                    {

                                        Intent intent = new Intent(NewLoginActivity.this, SubjectHistoryActivity.class);

                                        intent.putExtra("number", e_phone_number.getText().toString());
                                        startActivity(intent);
                                        SharedPreferences prfs1 = getSharedPreferences("AUTHENTICATION_FILE_NAME", NewLoginActivity.MODE_PRIVATE);
                                        String Astatus1 = prfs1.getString("mobile", "");
                                        if(Astatus1.length()==0) {
                                            SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", NewLoginActivity.this.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("mobile", e_phone_number.getText().toString());

                                            editor.apply();
                                        }
                                        p_loader.setVisibility(View.GONE);
                                        l_loader.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(NewLoginActivity.this,Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)+"" , Toast.LENGTH_LONG).show();
                                        Toast.makeText(NewLoginActivity.this, "Non Authentic User", Toast.LENGTH_LONG).show();
                                        p_loader.setVisibility(View.GONE);
                                        l_loader.setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });

                        } else {
                            p_loader.setVisibility(View.GONE);
                            l_loader.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(NewLoginActivity.this, "Not a Valid Mobile Number", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e)
        {
            Toast.makeText(NewLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        b_sign_up.setOnClickListener(v ->{

                p_loader.setVisibility(View.VISIBLE);
                l_loader.setVisibility(View.VISIBLE);
                o_firebaseauth.createUserWithEmailAndPassword(e_phone_number.getText().toString().trim()+"@gmail.com","123456")
                .addOnCompleteListener(NewLoginActivity.this, task -> {
                    //Toast.makeText(NewLoginActivity.this,task.toString(),Toast.LENGTH_LONG).show();
                    if (task.isSuccessful()) {
                        p_loader.setVisibility(View.GONE);
                        l_loader.setVisibility(View.GONE);

                        FirebaseUserData userData= new FirebaseUserData(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID),e_phone_number.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userData).addOnCompleteListener
                                (new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task)
                                     {


                                     }
                                 }

                                );

                        Intent intent = new Intent(NewLoginActivity.this,RegisterSubjectsActivity.class);
                        intent.putExtra("phone_number",e_phone_number.getText().toString().trim());
                        startActivity(intent);
                        SharedPreferences sharedPreferences
                                = getSharedPreferences("MySharedPref",
                                MODE_PRIVATE);


                        SharedPreferences.Editor myEdit
                                = sharedPreferences.edit();


                        myEdit.putString(
                                "name",
                                e_phone_number.getText().toString());



                        myEdit.commit();


                    } else {

                        p_loader.setVisibility(View.GONE);
                        l_loader.setVisibility(View.GONE);
                        Toast.makeText(NewLoginActivity.this,"Sign Up failed",Toast.LENGTH_LONG).show();

                    }

                    // ...
                })
                .addOnFailureListener(e ->
                        {
                            Toast.makeText(NewLoginActivity.this, "The Mobile Number is Already Registered", Toast.LENGTH_LONG).show();
                            p_loader.setVisibility(View.GONE);
                            l_loader.setVisibility(View.GONE);
                        }

                );
        });
        b_admin.setOnClickListener(v -> {
            int count=0;
            for(int i=0;i<adminsList.admins.size();i++)
            {
                if(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).equals(adminsList.admins.get(i)))
                {
                    count++;break;
                }
            }
            if(count==1) {
                Intent intent = new Intent(NewLoginActivity.this, AdminActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(NewLoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
            }
        });
        b_admin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    b_admin.setBackgroundColor(Color.parseColor("#2EAFE8"));

                    Drawable showDrawable = AppCompatResources.getDrawable(NewLoginActivity.this, R.drawable.admin);
                    b_admin.setIcon(showDrawable);
                }
                else
                {
                    if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        b_admin.setBackgroundColor(Color.parseColor("#cc9d3e"));
                    }
                }
                return  false;
            }
        });
        b_sign_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    b_sign_up.setBackgroundColor(Color.parseColor("#2EAFE8"));
                    b_sign_up.setTextColor(Color.parseColor("#ffffff"));

                }
                else
                {
                    if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        b_sign_up.setBackgroundColor(Color.parseColor("#cc9d3e"));
                    }
                }
                return  false;
            }
        });
        b_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    b_login.setBackgroundColor(Color.parseColor("#2EAFE8"));
                    b_login.setTextColor(Color.parseColor("#ffffff"));

                }
                else
                {
                    if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        b_login.setBackgroundColor(Color.parseColor("#cc9d3e"));
                    }
                }
                return  false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NewLoginActivity.this);

            builder.setTitle("Alert");
            builder.setMessage("Are You Sure You Want To Exit?");


            builder.setPositiveButton("OK", (dialog, which) -> {
                finish();


            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {


            });
  builder.show();


             // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}