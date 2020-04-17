package com.rsypj.ftuimobile.adapter.viewholder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.ui.fragment.PDFReaderFragment;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 13/07/2018.
 */
public class PDFReaderViewPager extends FragmentStatePagerAdapter {
    Fragment fragment = null;
    ArrayList<DetailPDFModel> data = new ArrayList<>();

    public PDFReaderViewPager(FragmentManager fm, ArrayList<DetailPDFModel> data){
        super(fm);
        this.data = data;

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Helper.KEY_DATA, data.get(position).getIdpdfdet());
        bundle.putString(Helper.KEY_NAME_PDF, data.get(position).getPdf());
        Log.d("getpositionfragment", String.valueOf(data.get(position)));
        Log.d("viewpager.idpdfdet", data.get(position).getIdpdfdet());
        Log.d("viewpager.getpdf", data.get(position).getPdf());
        Log.d("position", String.valueOf(position));
        fragment = new PDFReaderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}

