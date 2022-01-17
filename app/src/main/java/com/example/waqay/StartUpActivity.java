package com.example.waqay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // Intent intent= new Intent(StartUpActivity.this,NewLoginActivity.class);
                Intent intent= new Intent(StartUpActivity.this,SaqlainBroActivity.class);
                intent.putExtra("data","mazhar");
                startActivity(intent);
                finish();

            }
        },500);
    }
}