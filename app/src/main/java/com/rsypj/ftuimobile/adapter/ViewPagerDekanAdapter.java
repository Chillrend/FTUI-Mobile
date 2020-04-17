package com.rsypj.ftuimobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rsypj.ftuimobile.ui.fragment.AduanTeratasiFragment;
import com.rsypj.ftuimobile.ui.fragment.SemuaAduanFragment;

public class ViewPagerDekanAdapter extends FragmentPagerAdapter {

    public ViewPagerDekanAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new SemuaAduanFragment();
            }case 1:{
                return new AduanTeratasiFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
