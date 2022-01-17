package com.example.waqay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class HandeyBro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handey_bro);
        // Intent in =getIntent();
         // String text= in.getStringExtra("data");
        EditText et =(EditText)findViewById(R.id.e_text1);
        et.setText("ajgdkjassgdj");
    }
}