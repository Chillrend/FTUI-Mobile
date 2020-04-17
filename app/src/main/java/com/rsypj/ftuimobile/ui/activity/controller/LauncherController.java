package com.rsypj.ftuimobile.ui.activity.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.LauncherRevisiAdapter;
import com.rsypj.ftuimobile.connection.LauncherRequest;
import com.rsypj.ftuimobile.connection.listener.LauncherCallBack;
import com.rsypj.ftuimobile.model.LauncherModel;
import com.rsypj.ftuimobile.ui.activity.LauncherActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherController {

    LauncherActivity activity;
    //LauncherAdapter adapter;
    LauncherRevisiAdapter adapter;
    int PERMISSIONS_REQUEST_CODE = 1;
    ArrayList<LauncherModel> launcherData = new ArrayList<>();

    public LauncherController(LauncherActivity activity) {
        this.activity = activity;

        setAdapter();
        onRequestLauncher();
        setRefreshLayout();
        customActionBar();
        requestApp();
    }

    private void customActionBar(){
        activity.hideActionBar();
        activity.getTvTitle().setText(R.string.app_name);
    }

    private void setRefreshLayout(){
        activity.getSwipeRefreshLayout().setOnRefreshListener(activity);
        activity.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        activity.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                onRequestLauncher();
            }
        });
    }

    private void setAdapter(){
        adapter = new LauncherRevisiAdapter(activity, launcherData);
        activity.getListLauncher().setLayoutManager(new GridLayoutManager(activity, 3));
        activity.getListLauncher().setItemAnimator(new DefaultItemAnimator());
        activity.getListLauncher().setAdapter(adapter);
    }

    private boolean addPermissions(List<String> permissionsList, String permission){
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
            permissionsList.add(permission);
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return false;
        }
        return true;
    }

    private void requestApp(){
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        if (!addPermissions(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("android.permission.ACCESS_FINE_LOCATION");
        if (!addPermissions(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        if (!addPermissions(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("android.permission.CAMERA");
        if (!addPermissions(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add("android.permission.RECORD_AUDIO");

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

        }else{
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), PERMISSIONS_REQUEST_CODE);
        }
    }

//    private void setAdapter(){
//        adapter = new LauncherAdapter(activity, launcherData);
//
//        activity.getListLauncher().setItemAnimator(new DefaultItemAnimator());
//        activity.getListLauncher().setLayoutManager(new LinearLayoutManager(activity));
//        activity.getListLauncher().setAdapter(adapter);
//    }

    public void onRequestLauncher(){
        new LauncherRequest(activity).onRequestDataLauncher(new LauncherCallBack() {
            @Override
            public void onDataSetResult(ArrayList<LauncherModel> response) {
                launcherData.clear();
                launcherData.addAll(response);
                adapter.notifyDataSetChanged();
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onDataIsEmpty() {
                activity.showToast("Data Not Found!");
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onErrorRequest(String errorMessage) {
                activity.showToast(errorMessage);
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }
}
