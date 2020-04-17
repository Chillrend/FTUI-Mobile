package com.rsypj.ftuimobile.ui.activity.controller;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rsypj.ftuimobile.adapter.NotificationAdapter;
import com.rsypj.ftuimobile.connection.NotificationRequest;
import com.rsypj.ftuimobile.connection.listener.TimelineCallBack;
import com.rsypj.ftuimobile.model.TimelineModel;
import com.rsypj.ftuimobile.ui.activity.NotificationActivity;

import java.util.ArrayList;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class NotificationController {

    NotificationActivity activity;
    NotificationAdapter adapter;
    ArrayList<TimelineModel> data = new ArrayList<>();

    public NotificationController(NotificationActivity activity) {
        this.activity = activity;

        customActionbar();
        isAvailableData();
        setAdapter();
        requestNotifUser();
    }

    private void setAdapter(){
        activity.getTvActionBar().setText("Notification");
        adapter = new NotificationAdapter(activity, data);

        activity.getNotificationList().setItemAnimator(new DefaultItemAnimator());
        activity.getNotificationList().setLayoutManager(new LinearLayoutManager(activity));
        activity.getNotificationList().setAdapter(adapter);
    }

    private void requestNotifUser(){
        new NotificationRequest(activity).requestNotificationUserPost(new TimelineCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<TimelineModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDataIsEmpty() {
                activity.getTvNoNotification().setVisibility(View.VISIBLE);
            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
            }
        });
    }

    private void isAvailableData(){
        if (data.size() > 0){
            activity.getTvNoNotification().setVisibility(View.GONE);
        }
    }

    private void customActionbar(){
        activity.hideActionBar();

        activity.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
