package com.rsypj.ftuimobile.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.NotificationController;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class NotificationActivity extends SmartSipBaseActivity{

    RecyclerView notificationList;
    TextView tvNoNotification;
    TextView tvActionBar;
    ImageView ivBack;
    NotificationController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_notification;
    }

    @Override
    public void initItem() {
        notificationList = findViewById(R.id.activity_notification_list);
        tvActionBar = findViewById(R.id.activity_notification_tvNama);
        tvNoNotification = findViewById(R.id.activity_notification_tvNoNotification);
        ivBack = findViewById(R.id.activity_notification_ivBack);

        controller = new NotificationController(this);
    }

    public TextView getTvActionBar() {
        return tvActionBar;
    }

    public RecyclerView getNotificationList() {
        return notificationList;
    }

    public TextView getTvNoNotification() {
        return tvNoNotification;
    }

    public ImageView getIvBack() {
        return ivBack;
    }
}
