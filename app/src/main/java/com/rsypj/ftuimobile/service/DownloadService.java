package com.rsypj.ftuimobile.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.rsypj.ftuimobile.helper.Helper;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class DownloadService extends Service {

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "Success download", Toast.LENGTH_SHORT).show();

                sendingDataToUI();

                context.unregisterReceiver(this);
                onDestroy();
            } else if (intent.getAction().equalsIgnoreCase(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Toast.makeText(context, "Download cancelled", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void sendingDataToUI() {
        Intent intent = new Intent(Helper.DOWNLOAD_KEY);
        intent.putExtra("SuccessCode", 200);
        sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(broadcastReceiver, intentFilter);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
