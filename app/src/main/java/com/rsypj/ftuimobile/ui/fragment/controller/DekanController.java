package com.rsypj.ftuimobile.ui.fragment.controller;

import com.rsypj.ftuimobile.adapter.ViewPagerDekanAdapter;
import com.rsypj.ftuimobile.ui.fragment.DekanFragment;

public class DekanController {

    DekanFragment fragment;
    ViewPagerDekanAdapter dekanAdapter;

    public DekanController(DekanFragment fragment) {
        this.fragment = fragment;

        initViewPager();
    }

    private void initViewPager(){
        dekanAdapter = new ViewPagerDekanAdapter(fragment.getChildFragmentManager());
        fragment.getViewPager().setAdapter(dekanAdapter);

        fragment.getTabLayout().setupWithViewPager(fragment.getViewPager());
        fragment.getTabLayout().removeAllTabs();
        fragment.getTabLayout().addTab(fragment.getTabLayout().newTab().setText("Semua Aduan"));
        fragment.getTabLayout().addTab(fragment.getTabLayout().newTab().setText("Aduan Teratasi"));
    }
}
