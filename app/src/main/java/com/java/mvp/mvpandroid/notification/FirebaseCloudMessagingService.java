package com.java.mvp.mvpandroid.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.repository.PreferencesRepository;
import com.java.mvp.mvpandroid.ui.splash.SplashActivity;

/**
 * Created by hafiq on 08/09/2016.
 */
public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    private static final String TAG = "E-halal Push";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        PreferencesRepository preferencesRepository = new PreferencesRepository(this);
        if (preferencesRepository.isEnableTokenPush())
            sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        try {
            Log.d(TAG, "PUSH Notification Message Body = " + remoteMessage.getNotification().getBody());
            Log.i(TAG, "PUSH JSON DATA = " + new Gson().toJson(remoteMessage.getData()));
            Log.i(TAG, "PUSH JSON NOTIFICATION = " + new Gson().toJson(remoteMessage.getNotification()));
            Log.i(TAG, "PUSH Collapse Key = " + remoteMessage.getCollapseKey());
            Log.i(TAG, "PUSH From = " + remoteMessage.getFrom());
            Log.i(TAG, "PUSH Message Id = " + remoteMessage.getMessageId());
            Log.i(TAG, "PUSH Message Type = " + remoteMessage.getMessageType());
            Log.i(TAG, "PUSH Message To = " + remoteMessage.getTo());
            Log.i(TAG, "PUSH Message Sent Time = " + remoteMessage.getSentTime());
            Log.i(TAG, "PUSH Message Total = " + remoteMessage.getTtl());
            Log.i(TAG, "PUSH Message Token = " + FirebaseInstanceId.getInstance().getToken());

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("message", remoteMessage.getNotification().getBody());
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MYe-Halal")
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
        catch (Exception e){
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }
}