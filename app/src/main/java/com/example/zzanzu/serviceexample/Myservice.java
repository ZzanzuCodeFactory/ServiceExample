package com.example.zzanzu.serviceexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class Myservice extends Service {
    NotificationManager notificationManager;
    ServiceThread thread;
    Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(Myservice.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(Myservice.this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("content title")
                        .setContentText("content text")
                        .setSmallIcon(R.drawable.example)
                        .setTicker("ticker!")
                        .setContentIntent(pendingIntent)
                        .build();

                notification.defaults = Notification.DEFAULT_SOUND;
                notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                notification.flags = Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(777, notification);

                Toast.makeText(Myservice.this, "toast", Toast.LENGTH_LONG).show();
            }
        }
    }
}
