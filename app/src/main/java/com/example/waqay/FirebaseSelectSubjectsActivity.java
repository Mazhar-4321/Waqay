package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseSelectSubjectsActivity extends AppCompatActivity {
MaterialAutoCompleteTextView a_select_year,a_select_sem;
MaterialButton b_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().getReference("Year");
        setContentView(R.layout.activity_firebase_select_subjects);
        a_select_year=findViewById(R.id.a_select_year);
        a_select_sem=findViewById(R.id.a_select_sem);
        b_search=(MaterialButton)findViewById(R.id.b_search);
        String[] select_year= new String[]{"1","2","3","4"};
        String[] select_sem= new String[]{"1","2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FirebaseSelectSubjectsActivity.this,R.layout.drop_down_item,select_year);
        a_select_year.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(FirebaseSelectSubjectsActivity.this,R.layout.drop_down_item,select_sem);
        a_select_sem.setAdapter(arrayAdapter1);
        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Year/" );
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FirebaseUserData userData = snapshot.getValue(FirebaseUserData.class);
                        if (userData.android_id.equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))) {
                            Toast.makeText(FirebaseSelectSubjectsActivity.this, "Authentic User", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FirebaseSelectSubjectsActivity.this, "Non Authentic User", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });
    }
}