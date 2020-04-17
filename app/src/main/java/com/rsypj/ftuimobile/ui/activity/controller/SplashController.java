package com.rsypj.ftuimobile.ui.activity.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;

import com.rsypj.ftuimobile.ui.activity.LoginActivity;
import com.rsypj.ftuimobile.ui.activity.SplashActivity;

/**
 * Created by dhadotid on 06/04/2018.
 */

public class SplashController {

    SplashActivity activity;

    public SplashController(SplashActivity activity) {
        this.activity = activity;

        hideActionBar();
        onIntent();
    }

    private void onIntent(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        }, 2000);
    }

    private void hideActionBar(){
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }


//    private void getLocationName(){
//        LocationManager locationManager = (LocationManager)activity.getSystemService(activity.LOCATION_SERVICE);
//        String provider = locationManager.getBestProvider(new Criteria(), true);
//
//        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
//        List<String> providerList = locationManager.getAllProviders();
//        if (location != null && providerList != null && providerList.size() > 0){
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
//
//            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
//            try{
//                List<Address> listAddress = geocoder.getFromLocation(latitude, longitude, 1);
//                if(listAddress != null && listAddress.size() > 0){
//                    String locationName = listAddress.get(0).getSubLocality();
//                    String locationStreet = listAddress.get(0).getSubThoroughfare();
//                    String locationAdmin = listAddress.get(0).getAddressLine(0);
//
//                    activity.getTvAddress().setText(locationStreet + ", " + locationName + "\n" + locationAdmin);
//                }
//            }catch (IOException e){
//
//            }
//        }
//    }
}
