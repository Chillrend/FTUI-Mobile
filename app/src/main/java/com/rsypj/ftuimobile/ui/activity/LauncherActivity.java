package com.rsypj.ftuimobile.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.LauncherController;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherActivity extends SmartSipBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    TextView tvTitle;
    RecyclerView listLauncher;
    SwipeRefreshLayout swipeRefreshLayout;
    LauncherController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_launcher;
    }

    @Override
    public void initItem() {
        tvTitle = findViewById(R.id.activity_launcher_tvTitle);
        listLauncher = findViewById(R.id.launcher_list);
        swipeRefreshLayout = findViewById(R.id.activity_launcher_refresh);
        controller = new LauncherController(this);
    }

    @Override
    public void onRefresh() {
        controller.onRequestLauncher();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public RecyclerView getListLauncher() {
        return listLauncher;
    }
}
