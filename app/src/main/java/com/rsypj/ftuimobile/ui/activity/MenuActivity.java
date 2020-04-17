package com.rsypj.ftuimobile.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.helper.LocationHelper;
import com.rsypj.ftuimobile.ui.activity.controller.MenuController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhadotid on 08/04/2018.
 */

public class MenuActivity extends SmartSipBaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    ImageView ivNotification;
    ImageView ivCall;
    ImageView ivLogout;
    ImageView ivChart;
    ImageView ivUser;
    TextView menuName;
    BottomNavigationView bottomNavigationView;
    MenuController controller;
    int PERMISSIONS_REQUEST_CODE = 1;

    @Override
    public int getLayoutID() {
        return R.layout.activity_menu;
    }

    @Override
    public void initItem() {
        ivLogout = findViewById(R.id.activity_menu_logout);
        ivNotification = findViewById(R.id.activity_menu_notification);
        menuName = findViewById(R.id.activity_menu_textForm);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        ivCall = findViewById(R.id.activity_menu_call);
        ivChart = findViewById(R.id.activity_menu_graph);
        ivUser = findViewById(R.id.activity_menu_verifuser);
        //bottomNavigationView.getMenu().clear();

//        for (int i = 0; i < 4; i++){
//            requestApp();
//        }
        requestApp();

        if (mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        controller = new MenuController(this);
    }

    public ImageView getIvUser() {
        return ivUser;
    }

    public ImageView getIvChart() {
        return ivChart;
    }

    public ImageView getIvCall() {
        return ivCall;
    }

    public ImageView getIvLogout() {
        return ivLogout;
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(MenuActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MenuActivity.this, permission))
                return false;
        }
        return true;
    }

    private void requestApp(){
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("android.permission.ACCESS_FINE_LOCATION");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("android.permission.CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add("android.permission.RECORD_AUDIO");
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("android.permission.CALL_PHONE");

        ActivityCompat.requestPermissions(this,
                permissionsList.toArray(new String[permissionsList.size()]),
                PERMISSIONS_REQUEST_CODE);
    }

    public ImageView getIvNotification() {
        return ivNotification;
    }

    public TextView getMenuName() {
        return menuName;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //showToast("Permission denied!");
                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null){
                //controller.onSetLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                LocationHelper.latitude = mLastLocation.getLatitude();
                LocationHelper.longtitude = mLastLocation.getLongitude();
            }
        }catch (SecurityException se){

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
