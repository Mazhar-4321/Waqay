package com.example.waqay;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService  extends FirebaseMessagingService
{
    private static final String CHANNEL_ID = "Bestmarts";
    private static final String NOTIFICATION_ID = "Notification_id";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        Log.d(remoteMessage.getNotification().getTitle(), "onMessageReceived: ");
        Log.d(remoteMessage.getNotification().getBody(), "onMessageReceived: ");
        Intent intent = new Intent(com.example.waqay.NotificationService.this, NewLoginActivity.class);
       Uri uri= remoteMessage.getNotification().getImageUrl();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(com.example.waqay.NotificationService.this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
       // Bitmap b_ico= BitmapFactory.
        Notification  notification = new NotificationCompat.Builder(com.example.waqay.NotificationService.this,NOTIFICATION_ID)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);



    }
    }

