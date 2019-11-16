package org.ftui.mobile.service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.ftui.mobile.LoginActivity;
import org.ftui.mobile.R;
import org.ftui.mobile.model.User;
import org.ftui.mobile.utils.ApiService;

import es.dmoral.toasty.Toasty;
import org.ftui.mobile.utils.SPService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseInstance extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences(SPService.FB_TOKEN_SP, MODE_PRIVATE).edit().putString(SPService.FB_TOKEN_STRING, s).apply();
        getSharedPreferences(SPService.FB_TOKEN_SP, MODE_PRIVATE).edit().putBoolean(SPService.FB_TOKEN_IS_UPLOADED_STRING, false).apply();
    }


    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);

        if(message.getNotification() != null){
            Log.d("Notification received", "Body : " + message.getNotification().getBody());

            Map<String, String> notificationData = message.getData();
            String INTENT_ID = message.getNotification().getClickAction();

            Intent i = new Intent(INTENT_ID);
            i.putExtra("keluhan_id", notificationData.get("id"));

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0, i, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
