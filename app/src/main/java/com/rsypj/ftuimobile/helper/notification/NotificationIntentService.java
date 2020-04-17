package com.rsypj.ftuimobile.helper.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.ui.activity.NotificationActivity;

public class NotificationIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                if (SharePref.getmInstance(this).getRole().equals("2")){
                    processStartNotification();
                }else if (SharePref.getmInstance(this).getRole().equals("3")){
                    processStartNotificationSurveyor();
                }
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        }catch (Exception e){

        } finally{
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?

        try {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Terdapat Posting Baru")
                    .setAutoCancel(true)
                    .setColor(getResources().getColor(R.color.lightBlue300))
                    .setContentText("Posting baru menunggu publish")
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setSmallIcon(R.drawable.logoui);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    NOTIFICATION_ID,
                    new Intent(this, NotificationActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

            final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, builder.build());
        }catch (Exception e){

        }
    }

    private void processStartNotificationSurveyor() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?

        try {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Terdapat Posting Baru")
                    .setAutoCancel(true)
                    .setColor(getResources().getColor(R.color.lightBlue300))
                    .setContentText("Posting baru menunggu verifikasi anda")
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setSmallIcon(R.drawable.logoui);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    NOTIFICATION_ID,
                    new Intent(this, NotificationActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

            final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, builder.build());
        }catch (Exception ex){

        }
    }
}
