package com.rsypj.ftuimobile.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.DekanController;

public class DekanFragment extends SmartSipBaseFragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_dekan;
    }

    @Override
    public void initItem(View v) {
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.viewPager);

        new DekanController(this);

    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
