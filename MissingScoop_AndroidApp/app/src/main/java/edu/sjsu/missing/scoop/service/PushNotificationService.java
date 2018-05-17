package edu.sjsu.missing.scoop.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.sjsu.missing.scoop.MainActivity;
import edu.sjsu.missing.scoop.R;

public class PushNotificationService extends FirebaseMessagingService {
    public PushNotificationService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("PushNotificationService", "From: " + remoteMessage.getFrom());
        Log.d("PushNotificationService", "Notification Message Body: " + remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage);
    }


    private void sendNotification(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "001");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", remoteMessage.getNotification().getTitle());
        intent.putExtra("message", remoteMessage.getNotification().getBody());

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(remoteMessage.getNotification().getTitle());
        bigText.setBigContentTitle(remoteMessage.getNotification().getBody());

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.scoop);
        mBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        mBuilder.setContentText(remoteMessage.getNotification().getBody());
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("001", "001", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
}
