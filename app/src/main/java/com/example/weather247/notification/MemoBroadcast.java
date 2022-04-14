package com.example.weather247.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.weather247.ui.Home;
import com.example.weather247.R;

import java.io.IOException;
import java.net.URL;

public class MemoBroadcast extends BroadcastReceiver {
    private Bitmap image;
    @Override
    public void onReceive(Context context, Intent intent) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String weatherStatus = intent.getStringExtra("Weather status");
        String day = intent.getStringExtra("Day");
        String icon = intent.getStringExtra("Icon");

        try {
            URL url = new URL(icon);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }


        Intent repeating_Intent = new Intent(context, Home.class);

        repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.cloudy_icon)
                .setLargeIcon(image)
                .setContentTitle(day)
                .setContentText(weatherStatus)
                .setPriority(Notification.VISIBILITY_PRIVATE)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
