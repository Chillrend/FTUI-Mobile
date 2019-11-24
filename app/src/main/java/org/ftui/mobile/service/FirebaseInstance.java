package org.ftui.mobile.service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import org.ftui.mobile.R;

import org.ftui.mobile.model.Notification;
import org.ftui.mobile.utils.SPService;

import java.util.Map;
import java.util.UUID;

public class FirebaseInstance extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences(SPService.FB_TOKEN_SP, MODE_PRIVATE).edit().putString(SPService.FB_TOKEN_STRING, s).apply();
        getSharedPreferences(SPService.FB_TOKEN_SP, MODE_PRIVATE).edit().putBoolean(SPService.FB_TOKEN_IS_UPLOADED_STRING, false).apply();
    }


    public static void putNotifToRealmDb(Notification notification){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        notification.setId(UUID.randomUUID().toString());
        notification.setUpdated_at(System.currentTimeMillis() / 1000L);
        realm.insertOrUpdate(notification);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);

        SPService spService = new SPService(getApplicationContext());

        if(message.getData().size() > 0){
            Log.d("From FCM", "Data: " + message.getData());
            String keluhan_id = message.getData().get("ID");
            String not_title = message.getData().get("notification_title");
            String not_desc = message.getData().get("notification_body");

            Notification notification = new Notification(not_title, not_desc, keluhan_id);

            if(spService.isRealmInitialized()){
                putNotifToRealmDb(notification);
            }else{
                Realm.init(getApplicationContext());
                spService.setRealmIsInit(true);
                putNotifToRealmDb(notification);
            }

            Intent i = new Intent();
            i.setAction("ACTION_SEND_SNACKBAR");
            i.putExtra("data", keluhan_id);
            sendBroadcast(i);
        }
    }
}
