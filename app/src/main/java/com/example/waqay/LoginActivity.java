package com.example.waqay;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText user_name,password;
    private Button submit,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        user_name=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.pass_word);
        submit=(Button)findViewById(R.id.submit);
        login=(Button)findViewById(R.id.Login);
        FirebaseDatabase.getInstance().getReference("Users");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(user_name.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(LoginActivity.this,ExpandableListViewActivity.class));


                                    FirebaseUserData userData= new FirebaseUserData(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID),"12345");
                                    //userData.user_bluetooth=bluetoothAdapter.getAddress().toString();
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userData).addOnCompleteListener
                                            (new OnCompleteListener<Void>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<Void> task)
                                                 {
                                                     Toast.makeText( LoginActivity.this,"User Registered Successfully",Toast.LENGTH_LONG).show();

                                                 }
                                             }

                                            );

                                } else {
                                    // If sign in fails, display a message to the user.

                                }

                                // ...
                            }
                        });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(user_name.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String s=   FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
                                    // Sign in success, update UI with the signed-in user's information
                                    // Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    ;
                                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                                    DatabaseReference reference=database.getReference("Users/"+s);
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            FirebaseUserData userData=snapshot.getValue(FirebaseUserData.class);
                                            Toast.makeText(LoginActivity.this,userData+"",Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    Toast.makeText(LoginActivity.this,s+"",Toast.LENGTH_LONG).show();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.

                                }

                                // ...
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
