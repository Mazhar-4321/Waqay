package com.example.waqay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseLoginActivity extends AppCompatActivity {
//  private TextInputEditText e_phone_number;
//     FirebaseAuth o_firebaseauth;
//     ProgressBar p_loader;
//     LinearLayout l_loader;
//  private MaterialButton b_login,b_sign_up,b_admin,b_parent_login;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_firebase_login);
//        o_firebaseauth = FirebaseAuth.getInstance();
//        FirebaseDatabase.getInstance().getReference("Users");
//        e_phone_number=(TextInputEditText) findViewById(R.id.e_phone_number);
//        b_login=(MaterialButton) findViewById(R.id.b_login);
//        p_loader=(ProgressBar)findViewById(R.id.progressBar);
//        b_sign_up=(MaterialButton)findViewById(R.id.b_sign_up);
//        b_admin=(MaterialButton)findViewById(R.id.b_admin);
//        b_parent_login=(MaterialButton)findViewById(R.id.b_parent_login);
//        l_loader=(LinearLayout)findViewById(R.id.linear_layout);
//        b_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(FirebaseLoginActivity.this,"Login Clicked",Toast.LENGTH_LONG).show();
//                l_loader.setVisibility(View.VISIBLE);
//                p_loader.setVisibility(View.VISIBLE);
//                m_b_login();
//            }
//        });
//        b_sign_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(FirebaseLoginActivity.this,"SignUp Clicked",Toast.LENGTH_LONG).show();
//                l_loader.setVisibility(View.VISIBLE);
//                p_loader.setVisibility(View.VISIBLE);
//                m_sign_up();
//            }
//        });
//        b_admin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(FirebaseLoginActivity.this,"Admin Clicked",Toast.LENGTH_LONG).show();
//                m_b_admin();
//            }
//        });
//        b_parent_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(FirebaseLoginActivity.this,"Parent Clicked",Toast.LENGTH_LONG).show();
//                m_b_parent_login();
//            }
//        });
//    }
//
//    private void m_b_parent_login() {
//
//    }
//
//    private void m_b_admin() {
//    }
//
//    private void m_sign_up()
//    {
//Toast.makeText(FirebaseLoginActivity.this,e_phone_number.getText().toString().trim(),Toast.LENGTH_LONG).show();
//        String password="123456";
//        o_firebaseauth.createUserWithEmailAndPassword(e_phone_number.getText().toString().trim(), password.trim())
//                .addOnCompleteListener(FirebaseLoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(FirebaseLoginActivity.this,task.toString(),Toast.LENGTH_LONG).show();
//                        if (task.isSuccessful()) {
//                            p_loader.setVisibility(View.GONE);
//                            l_loader.setVisibility(View.GONE);
//                            Toast.makeText(FirebaseLoginActivity.this,"Success",Toast.LENGTH_LONG).show();
//                            //startActivity(new Intent(LoginActivity.this,ExpandableListViewActivity.class));
//
//
//                            FirebaseUserData userData= new FirebaseUserData(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID),e_phone_number.getText().toString());
//                            //userData.user_bluetooth=bluetoothAdapter.getAddress().toString();
//                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userData).addOnCompleteListener
//                                    (new OnCompleteListener<Void>() {
//                                         @Override
//                                         public void onComplete(@NonNull Task<Void> task)
//                                         {
//                                             Toast.makeText( FirebaseLoginActivity.this,"User Registered Successfully",Toast.LENGTH_LONG).show();
//
//                                         }
//                                     }
//
//                                    );
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            p_loader.setVisibility(View.GONE);
//                            l_loader.setVisibility(View.GONE);
//                            Toast.makeText(FirebaseLoginActivity.this,"Sign Up failed",Toast.LENGTH_LONG).show();
//
//                        }
//
//                        // ...
//                    }
//                })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(FirebaseLoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void m_b_login()
//    {
//        String password="123456";
//        //o_firebaseauth = FirebaseAuth.getInstance();
//        o_firebaseauth.signInWithEmailAndPassword(e_phone_number.getText().toString().trim(), password.trim()).addOnCompleteListener(task -> {
//                   // Toast.makeText(FirebaseLoginActivity.this, "mazhar", Toast.LENGTH_LONG).show();
//                    if (task.isSuccessful()) {
//                        p_loader.setVisibility(View.GONE);
//                        l_loader.setVisibility(View.GONE);
//                        String s = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
//                        // Sign in success, update UI with the signed-in user's information
//                        // Log.d(TAG, "signInWithEmail:success");
//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        ;
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference reference = database.getReference("Users/" + s);
//                        reference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                FirebaseUserData userData = snapshot.getValue(FirebaseUserData.class);
//                                if (userData.android_id.equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))) {
//                                    Toast.makeText(FirebaseLoginActivity.this, "Authentic User", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(FirebaseLoginActivity.this, "Non Authentic User", Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//
//                        });
//                    }
//                    else
//                    {
//                        p_loader.setVisibility(View.GONE);
//                        l_loader.setVisibility(View.GONE);
//                    }
//                });
//    }
}
//                .addOnCompleteListener(FirebaseLoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(FirebaseLoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                        if (task.isSuccessful()) {
//                            String s=   FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
//                            // Sign in success, update UI with the signed-in user's information
//                            // Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            ;
//                            FirebaseDatabase database=FirebaseDatabase.getInstance();
//                            DatabaseReference reference=database.getReference("Users/"+s);
//                            reference.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    FirebaseUserData userData=snapshot.getValue(FirebaseUserData.class);
//                                    if(userData.android_id.equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)))
//                                    {
//                                        Toast.makeText(FirebaseLoginActivity.this,"Authentic User",Toast.LENGTH_LONG).show();
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText(FirebaseLoginActivity.this,"Non Authentic User",Toast.LENGTH_LONG).show();
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                            Toast.makeText(FirebaseLoginActivity.this,s+"",Toast.LENGTH_LONG).show();
//                            //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(FirebaseLoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
//                        }
//
//                        // ...
//                    }
//                })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                String s= e.getMessage();
//                Toast.makeText(FirebaseLoginActivity.this,s,Toast.LENGTH_LONG).show();
//            }
//        })
//        .addOnCanceledListener(new OnCanceledListener() {
//            @Override
//            public void onCanceled() {
//
//            }
//        })
//        ;
