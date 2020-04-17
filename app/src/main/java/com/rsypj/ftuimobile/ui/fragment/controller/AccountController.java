package com.rsypj.ftuimobile.ui.fragment.controller;

import android.view.View;

import com.rsypj.ftuimobile.connection.LoginRequest;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.ui.fragment.AccountFragment;


/**
 * Created by dhadotid on 11/04/2018.
 */

public class AccountController {

    AccountFragment fragment;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public AccountController(AccountFragment fragment) {
        this.fragment = fragment;

        onLoadData();
        onButtonUpdateClicked();
    }

    private void onLoadData(){
        fragment.getEtNim().setText(SharePref.getmInstance(fragment.getContext()).getNim());
        fragment.getEtEmail().setText(SharePref.getmInstance(fragment.getContext()).getEmail());
        fragment.getEtName().setText(SharePref.getmInstance(fragment.getContext()).getName());

        if (SharePref.getmInstance(fragment.getContext()).getRole().equals("1")){
            fragment.getEtRoles().setText("Mahasiswa");
        }else if (SharePref.getmInstance(fragment.getContext()).getRole().equals("2")){
            fragment.getEtRoles().setText("Manager");
        }else if (SharePref.getmInstance(fragment.getContext()).getRole().equals("3")){
            fragment.getEtRoles().setText("Surveyor");
        }else if (SharePref.getmInstance(fragment.getContext()).getRole().equals("4")){
            fragment.getEtRoles().setText("Wadek");
        }else if (SharePref.getmInstance(fragment.getContext()).getRole().equals("5")){
            fragment.getEtRoles().setText("Dosen");
        }else if (SharePref.getmInstance(fragment.getContext()).getRole().equals("6")){
            fragment.getEtRoles().setText("Tendik");
        }

    }

    private void onButtonUpdateClicked(){
        fragment.getBtnUpdate().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()){
                    Helper.email = fragment.getEtEmail().getText().toString();
                    Helper.name = fragment.getEtName().getText().toString();
                    Helper.role = fragment.getEtRoles().getText().toString();
                    sendDataToServer();
                }
            }
        });
    }

    private boolean valid(){
        if (fragment.getEtEmail().getText().toString().length() < 4 || !fragment.getEtEmail().getText().toString().matches(emailPattern)){
            fragment.getEtName().setError("Harap masukkan email dengan benar");
            return false;
        }else if (fragment.getEtName().getText().toString().length() < 3){
            fragment.getEtName().setError("Harap masukkan nama");
            return false;
        }
        return true;
    }

    private void sendDataToServer(){
        new LoginRequest(fragment.getContext()).updateProfil(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {
                fragment.showToast("Update Success");
            }

            @Override
            public void onError(String message) {
                fragment.showToast(message);
            }
        });
    }
}
