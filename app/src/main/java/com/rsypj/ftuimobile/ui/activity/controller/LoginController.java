package com.rsypj.ftuimobile.ui.activity.controller;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.rsypj.ftuimobile.connection.LoginRequest;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.ui.activity.LoginActivity;
import com.rsypj.ftuimobile.ui.activity.MenuActivity;
import com.rsypj.ftuimobile.ui.activity.RegisterActivity;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class LoginController {

    LoginActivity activity;

    public LoginController(LoginActivity activity) {
        this.activity = activity;

        onLoginClicked();
        onRegisterClicked();
        hideActionBar();
        isLogin();
    }

    private void isLogin(){
        if (SharePref.getmInstance(activity).isLoggedIn()){
            Intent in = new Intent(activity, MenuActivity.class);
            activity.startActivity(in);
            activity.finish();
        }
    }

    private void onLoginClicked(){
        activity.getBtnLogin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.NIM = activity.getEtNIMorNIP().getText().toString();
                Helper.password = activity.getEtPassword().getText().toString();
                reqLoginCheck();
//                activity.startActivity(new Intent(activity, MenuActivity.class));
//                activity.finish();
            }
        });
    }

    private void reqLoginCheck(){
        new LoginRequest(activity).loginChecker(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {
                if (response.equals("Success")){
                    Intent in = new Intent(activity, MenuActivity.class);
                    activity.startActivity(in);
                    activity.finish();
                }else if (response.equals("Error")){
                    activity.showToast("NIM atau Password salah");
                }else if (response.equals("69")){
                    activity.showToast("Akun anda belum terverifikasi\nSilahkan coba beberapa saat lagi");
                }
            }

            @Override
            public void onError(String message) {
                activity.showToast(message);
            }
        });
    }

    private void onRegisterClicked(){
        activity.getTvRegister().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, RegisterActivity.class));
            }
        });
    }

    private void hideActionBar(){
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }
}
