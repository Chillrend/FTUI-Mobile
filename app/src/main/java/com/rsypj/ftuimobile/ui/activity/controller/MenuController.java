package com.rsypj.ftuimobile.ui.activity.controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.CallSpinnerAdapter;
import com.rsypj.ftuimobile.connection.PhoneRequest;
import com.rsypj.ftuimobile.connection.listener.PhoneCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.BottomNavigationViewHelper;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.notification.NotificationEventReceiver;
import com.rsypj.ftuimobile.model.NoTelphoneModel;
import com.rsypj.ftuimobile.ui.activity.LoginActivity;
import com.rsypj.ftuimobile.ui.activity.MenuActivity;
import com.rsypj.ftuimobile.ui.activity.NotificationActivity;
import com.rsypj.ftuimobile.ui.activity.PieChartActivity;
import com.rsypj.ftuimobile.ui.activity.VerifUserActivity;
import com.rsypj.ftuimobile.ui.fragment.AccountFragment;
import com.rsypj.ftuimobile.ui.fragment.DekanFragment;
import com.rsypj.ftuimobile.ui.fragment.SurveyorFragment;
import com.rsypj.ftuimobile.ui.fragment.HomeFragment;
import com.rsypj.ftuimobile.ui.fragment.ManagerFragment;
import com.rsypj.ftuimobile.ui.fragment.MessageFragment;

import java.util.ArrayList;

/**
 * Created by dhadotid on 08/04/2018.
 */

public class MenuController {

    MenuActivity activity;
    CallSpinnerAdapter callSpinnerAdapter;
    ArrayList<NoTelphoneModel> noHP = new ArrayList<>();

    public MenuController(MenuActivity activity) {
        this.activity = activity;

        NotificationEventReceiver.setupAlarm(activity);

        onHideMenu();
        disableShifting();
        onSelectedBottomNavigation();
        switchToFragment(1);
        onNotificationClicked();
        onButtonLogoutClicked();
        dummyPhone();
        onCallClicked();
        onChartClicked();
        onVerifUserClicked();
    }

    private void onChartClicked(){
        activity.getIvChart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, PieChartActivity.class));
            }
        });
    }

    private void onCallClicked(){
        activity.getIvCall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder();
            }
        });
    }

    private void onCall(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Apakah Kamu Yakin Ingin Melakukan Panggilan Darurat?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(Helper.noTlfn));

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                activity.startActivity(callIntent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dummyPhone(){
//        noHP.add(new NoTelphone("tel:082299026163"));
        new PhoneRequest(activity).requestPhoneData(new PhoneCallBack() {
            @Override
            public void onDataSetResult(ArrayList<NoTelphoneModel> response) {
                noHP.addAll(response);
                Log.d("No Hp: ", noHP.size()+"");
            }

            @Override
            public void onDataIsEmpty() {

            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
                Log.d("Error: ", errorMessage);
            }
        });
    }

    private void dialogBuilder(){
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View promptView = layoutInflater.inflate(R.layout.dialog_call, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        Spinner spinnerCall = promptView.findViewById(R.id.spinnerListNomor);
        Button btnCall = promptView.findViewById(R.id.buttonCall);

        callSpinnerAdapter = new CallSpinnerAdapter(activity, android.R.layout.simple_spinner_item, noHP);
        spinnerCall.setAdapter(callSpinnerAdapter);
        spinnerCall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NoTelphoneModel noTelphoneModel = callSpinnerAdapter.getItem(position);
                Helper.noTlfn = noTelphoneModel.getNoPhone();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

        alertDialog.setView(promptView);
        alertDialog.show();
    }

    private void onButtonLogoutClicked(){
        activity.getIvLogout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndShowAlertDialog();
            }
        });
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Keluar Aplikasi");
        builder.setMessage("Apakah kamu yakin ingin keluar aplikasi?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharePref.getmInstance(activity).logout();
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onHideMenu(){
        if (SharePref.getmInstance(activity).getRole().equals("1")){
            activity.getBottomNavigationView().getMenu().removeItem(R.id.admin);
            activity.getBottomNavigationView().getMenu().removeItem(R.id.surveyor);
        }else if (SharePref.getmInstance(activity).getRole().equals("2")){
            activity.getBottomNavigationView().getMenu().removeItem(R.id.surveyor);
        }else if (SharePref.getmInstance(activity).getRole().equals("3")){
            activity.getBottomNavigationView().getMenu().removeItem(R.id.admin);
        }else if (SharePref.getmInstance(activity).getRole().equals("4")){
            activity.getBottomNavigationView().getMenu().removeItem(R.id.admin);
            activity.getBottomNavigationView().getMenu().findItem(R.id.surveyor).setTitle("Wadek");
        }else{
            activity.getBottomNavigationView().getMenu().removeItem(R.id.admin);
            activity.getBottomNavigationView().getMenu().removeItem(R.id.surveyor);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment: activity.getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onSelectedBottomNavigation(){
        activity.getBottomNavigationView().setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        switchToFragment(1);
                        break;
                    case R.id.message:
                        switchToFragment(2);
                        break;
                    case R.id.account:
                        switchToFragment(3);
                        break;
                    case R.id.admin:
                        switchToFragment(4);
                        break;
                    case R.id.surveyor:
                        switchToFragment(5);
                        break;
                }
                return true;
            }
        });
    }

    private void switchToFragment(int menu){
        String title = "";

        android.support.v4.app.FragmentManager manager = activity.getSupportFragmentManager();
        if(menu == 1) {
            activity.getIvNotification().setVisibility(View.VISIBLE);
            activity.getIvLogout().setVisibility(View.GONE);
            activity.getIvCall().setVisibility(View.VISIBLE);
            activity.getIvChart().setVisibility(View.GONE);
            activity.getIvUser().setVisibility(View.GONE);
            manager.beginTransaction().replace(R.id.activity_frame, new HomeFragment()).commit();
            activity.getIvNotification().setVisibility(View.VISIBLE);
            title = "mComplaint";
        }else if(menu == 2){
            activity.getIvNotification().setVisibility(View.GONE);
            activity.getIvLogout().setVisibility(View.GONE);
            activity.getIvCall().setVisibility(View.GONE);
            activity.getIvChart().setVisibility(View.GONE);
            activity.getIvUser().setVisibility(View.GONE);
            manager.beginTransaction().replace(R.id.activity_frame, new MessageFragment()).commit();
            activity.getIvNotification().setVisibility(View.GONE);
            title = "Message";
        }else if(menu == 3){
            activity.getIvNotification().setVisibility(View.GONE);
            activity.getIvCall().setVisibility(View.GONE);
            activity.getIvLogout().setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.activity_frame, new AccountFragment()).commit();
            activity.getIvNotification().setVisibility(View.GONE);
            activity.getIvChart().setVisibility(View.GONE);
            activity.getIvUser().setVisibility(View.GONE);
            title = "Account";
        }else if (menu == 4){
            activity.getIvNotification().setVisibility(View.GONE);
            activity.getIvLogout().setVisibility(View.GONE);
            activity.getIvCall().setVisibility(View.GONE);
            manager.beginTransaction().replace(R.id.activity_frame, new ManagerFragment()).commit();
            activity.getIvNotification().setVisibility(View.GONE);
            activity.getIvChart().setVisibility(View.VISIBLE);
            activity.getIvUser().setVisibility(View.VISIBLE);
            title = "Manager";
        }else if (menu == 5){
            if (SharePref.getmInstance(activity).getRole().equals("3")){
                activity.getIvNotification().setVisibility(View.GONE);
                activity.getIvLogout().setVisibility(View.GONE);
                activity.getIvCall().setVisibility(View.GONE);
                manager.beginTransaction().replace(R.id.activity_frame, new SurveyorFragment()).commit();
                activity.getIvNotification().setVisibility(View.GONE);
                activity.getIvChart().setVisibility(View.VISIBLE);
                activity.getIvUser().setVisibility(View.GONE);
                title = "Surveyor";
            }else if (SharePref.getmInstance(activity).getRole().equals("4")){
                activity.getIvNotification().setVisibility(View.GONE);
                activity.getIvLogout().setVisibility(View.GONE);
                activity.getIvCall().setVisibility(View.GONE);
                manager.beginTransaction().replace(R.id.activity_frame, new DekanFragment()).commit();
                activity.getIvNotification().setVisibility(View.GONE);
                activity.getIvChart().setVisibility(View.VISIBLE);
                activity.getIvUser().setVisibility(View.GONE);
                title = "Wadek";
            }
        }
        activity.getMenuName().setText(title);
    }

    private void disableShifting() {
        activity.hideActionBar();
        BottomNavigationViewHelper.disableShiftMode(activity.getBottomNavigationView(), activity);
    }

    private void onNotificationClicked(){
        activity.getIvNotification().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, NotificationActivity.class));
            }
        });
    }

    private void onVerifUserClicked(){
        activity.getIvUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, VerifUserActivity.class));
            }
        });
    }
}
