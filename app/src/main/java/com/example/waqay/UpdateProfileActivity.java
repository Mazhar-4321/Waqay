package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {
    MaterialAutoCompleteTextView a_select_student;
    HashMap<String,FirebaseUserData>  hashMap= new HashMap<>();
    ArrayList<String> mobile_list = new ArrayList<>();
    HashMap<String,String>  hashMap_1= new HashMap<>();
    MaterialButton b_change_access,b_remove_access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        a_select_student=findViewById(R.id.a_select_student);
        b_change_access=findViewById(R.id.b_change_access);
        b_remove_access=findViewById(R.id.b_remove_access);
        b_change_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=a_select_student.getText().toString();
                String parent_key = hashMap_1.get(key);
                FirebaseUserData firebaseUserData = hashMap.get(parent_key);
                firebaseUserData.isActive = "1";
                FirebaseDatabase.getInstance().getReference("Users/"+parent_key).setValue(firebaseUserData).addOnCompleteListener(task -> {
                    Toast.makeText(UpdateProfileActivity.this,"Success",Toast.LENGTH_SHORT).show();});
            }
        });
        b_remove_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=a_select_student.getText().toString();
                String parent_key = hashMap_1.get(key);
                FirebaseUserData firebaseUserData = hashMap.get(parent_key);
                firebaseUserData.isActive = "0";
                FirebaseDatabase.getInstance().getReference("Users/"+parent_key).setValue(firebaseUserData).addOnCompleteListener(task -> {
                    Toast.makeText(UpdateProfileActivity.this,"Success",Toast.LENGTH_SHORT).show();});
            }
        });
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot post_snap_shot : snapshot.getChildren())
            {
                String key = post_snap_shot.getKey();
                FirebaseUserData firebaseUserData = post_snap_shot.getValue(FirebaseUserData.class);
                hashMap.put(key,firebaseUserData);
                mobile_list.add(firebaseUserData.mail_id);
                hashMap_1.put(firebaseUserData.mail_id,key);


            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdateProfileActivity.this,R.layout.drop_down_item,mobile_list);
        a_select_student.setAdapter(arrayAdapter);
        a_select_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdateProfileActivity.this,R.layout.drop_down_item,mobile_list);
                a_select_student.setAdapter(arrayAdapter);
            }
        });

    }
}