package com.rsypj.ftuimobile.ui.activity.controller;

import android.support.design.widget.TabLayout;
import android.util.Log;

import com.rsypj.ftuimobile.adapter.viewholder.PDFReaderViewPager;
import com.rsypj.ftuimobile.connection.LauncherRequest;
import com.rsypj.ftuimobile.connection.listener.PDFDetailCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.ui.activity.PDFReaderActivity;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 13/07/2018.
 */
public class PDFReaderController {

    PDFReaderActivity activity;
    PDFReaderViewPager viewPagerAdapter;
    ArrayList<DetailPDFModel> data = new ArrayList<>();
    private boolean isDataLoaded = false;

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            activity.getViewPager().setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public PDFReaderController(PDFReaderActivity activity) {
        this.activity = activity;

        Helper.idpdf = activity.getIntent().getStringExtra(Helper.DATA);

        Log.d("controller1.idpdf", Helper.idpdf);

        onRequestDataCategory();
        setUpViewPager();
    }

    public void onResume() {
        if (isDataLoaded) {
            setUpTabLayoutSelectionListener();
        }
    }

    public void onPause() {
        removeTabLayoutSelectionListener();
    }

    private void setUpTabLayoutSelectionListener() {
        activity.getTabLayout().addOnTabSelectedListener(tabSelectedListener);
    }

    private void removeTabLayoutSelectionListener() {
        activity.getTabLayout().removeOnTabSelectedListener(tabSelectedListener);
    }

    private void setUpViewPager(){
        viewPagerAdapter = new PDFReaderViewPager(activity.getSupportFragmentManager(), data);
        activity.getViewPager().setAdapter(viewPagerAdapter);
    }

    private void onRequestDataCategory(){
        new LauncherRequest(activity).onRequestDetailPDF(new PDFDetailCallBack() {
            @Override
            public void onDataSetResult(ArrayList<DetailPDFModel> response) {
                data.addAll(response);
                Log.d("controller1.data", data.toString());

                for (int i = 0; i < data.size(); i++){
                    //set tab name depand on data that fetch
                    activity.getTabLayout().addTab(activity.getTabLayout().newTab().setText(data.get(i).getNama()));
                }
                viewPagerAdapter.notifyDataSetChanged();

                if (!isDataLoaded) {
                    isDataLoaded = true;
                    setUpTabLayoutSelectionListener();
                }
            }

            @Override
            public void onDataIsEmpty() {
                activity.showToast("Data Not Found!");
            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
            }
        });
    }
}
