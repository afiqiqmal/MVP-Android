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

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.java.mvp.mvpandroid.BuildConfig;
import com.java.mvp.mvpandroid.R;
import com.java.mvp.mvpandroid.ui.MainActivity;
import com.java.mvp.mvpandroid.utils.ErrorUtils;

/**
 * @author : hafiq on 08/09/2016.
 */
public class FirebaseNotificationService extends FirebaseMessagingService {

    private static final String TAG = "E-halal Push";

    ErrorUtils errorUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        errorUtils = new ErrorUtils(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        try {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "PUSH TITLE = " + remoteMessage.getData().get("title"));
                Log.i(TAG, "PUSH BODY = " + remoteMessage.getData().get("body"));
            }

            String body = null;
            Intent intent = null;
            try {
                body = remoteMessage.getData().get("body");
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("message", body);
            }
            catch (Exception e){
                errorUtils.recordError(e);
            }

            if (intent !=null) {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }
            else{
                errorUtils.recordError(new Throwable("Intent is null for notification"));
            }
        }
        catch (Exception e){
            errorUtils.recordError(e);
        }
    }
}