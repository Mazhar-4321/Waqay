package com.example.waqay;

//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.*;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaqlainBroActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saqlain_bro);
        MyDatabase database = new MyDatabase(SaqlainBroActivity.this);
        database.insert(1,"mazhar","1257641267");
   database.selectRow();
   database.deleteRow();




////        Intent in = getIntent();
////       String text=  in.getStringExtra("data");
////        EditText et= (EditText)findViewById(R.id.e_text);//
////        et.setText(text);
////
////
////
////
////
////
////        Button bt = (Button)findViewById(R.id.b_text);//
////        bt.setOnClickListener(new View.OnClickListener()
////        {
////            @Override
////            public void onClick(View v)
////            {
////
//////                       String text = et.getText().toString();
//////                       Intent in = new Intent(SaqlainBroActivity.this,HandeyBro.class);
//////                       in.putExtra("data",text);
//////                       startActivity(in);
//////                String text = et.getText().toString();
//////                Intent in = new Intent(Intent.ACTION_CALL);
//////                in.setData(Uri.parse(text));
//////                startActivity(in);
////                /******* MEthod1 ************/
////                SmsManager sm = SmsManager.getDefault();
////                String number = et.getText().toString();
////                String msg = et.getText().toString();
////                sm.sendTextMessage(number, null, msg, null, null);
////                /******** Method 2 **********/
////
////
////
////
////
////
////                NotificationCompat.Builder  builder = new NotificationCompat.Builder(SaqlainBroActivity.this).
////                        setSmallIcon(R.drawable.ic_launcher_background)
////                        .setContentTitle("New Message")
////                        .setContentText("HEllo Boys")
////                        .setAutoCancel(true);
////
////
////
////
////
////                Intent in = new Intent(SaqlainBroActivity.this,NotificationActivity.class);
////                PendingIntent p = PendingIntent.getActivity(SaqlainBroActivity.this,0,in,0);
////                android.app.Notification note= new android.app.Notification();
////                note = builder.getNotification();
////                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////                notificationManager.notify(0,note);
////
////
////            }
//        }
//        );
    }
}