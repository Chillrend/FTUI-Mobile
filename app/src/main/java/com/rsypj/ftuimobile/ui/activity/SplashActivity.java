package com.rsypj.ftuimobile.ui.activity;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.SplashController;

public class SplashActivity extends SmartSipBaseActivity{

    SplashController controller;


    @Override
    public int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public void initItem() {

        controller = new SplashController(this);
    }
}
