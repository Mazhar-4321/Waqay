package com.example.waqay;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationClass extends Application {
    private static final String NOTIFICATION_ID = "Notification_id";
    @Override
    public void onCreate() {
        super.onCreate();


        createNotification();
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_ID,
                    "demo", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Demo");
            NotificationManager notificationManager =getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
