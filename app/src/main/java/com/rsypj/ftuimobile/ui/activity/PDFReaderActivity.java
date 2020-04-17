package com.rsypj.ftuimobile.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.PDFReaderController;

/**
 * Author By dhadotid
 * Date 13/07/2018.
 */
public class PDFReaderActivity extends SmartSipBaseActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private PDFReaderController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_pdf_reader;
    }

    @Override
    public void initItem() {
        tabLayout = findViewById(R.id.pdf_reader_tabLayout);
        viewPager = findViewById(R.id.pdf_reader_viewPager);
        hideActionBar();
        controller = new PDFReaderController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (controller != null) {
            controller.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (controller != null) {
            controller.onPause();
        }
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
