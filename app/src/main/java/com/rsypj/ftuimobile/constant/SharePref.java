package com.rsypj.ftuimobile.constant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rsypj.ftuimobile.model.UserModel;
import com.rsypj.ftuimobile.ui.activity.LoginActivity;

public class SharePref {

    private static final String SHARED_PREF_NAME = "SmartSIP";
    private static final String KEY_NAME = "KEYUSER";
    private static final String KEY_NIM = "keynim";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ROLE = "keyrole";


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static SharePref mInstance;
    private static Context ctx;

    public SharePref(Context context) {
        ctx = context;
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharePref getmInstance(Context context){
        if(mInstance == null){
            mInstance = new SharePref(context);
        }
        return mInstance;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    public void userLogin(UserModel userModel){
        editor.putString(KEY_NIM, userModel.getNim());
        editor.putString(KEY_NAME, userModel.getName());
        editor.putString(KEY_EMAIL, userModel.getEmail());
        editor.putString(KEY_ROLE, userModel.getRole()+"");
        editor.apply();
    }

    public String getName(){
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getNim(){
        return sharedPreferences.getString(KEY_NIM, "");
    }

    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getRole(){
        return sharedPreferences.getString(KEY_ROLE, "");
    }

    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}
