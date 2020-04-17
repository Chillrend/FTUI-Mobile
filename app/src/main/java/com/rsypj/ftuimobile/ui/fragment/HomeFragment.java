package com.rsypj.ftuimobile.ui.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.HomeController;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by dhadotid on 08/04/2018.
 */

public class HomeFragment extends SmartSipBaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView homeList;
    FloatingActionButton ftCamera;
    LinearLayout llLoadmore;
    TextView tvLoadmore;
    SwipeRefreshLayout swipeRefreshLayout;
//    ProgressBar pbLoadmore;
    HomeController controller;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initItem(View v) {
        homeList = v.findViewById(R.id.fragment_home_listHome);
        ftCamera = v.findViewById(R.id.floatingActionButtonCamera);
        llLoadmore = v.findViewById(R.id.linearLoadmore);
        tvLoadmore = v.findViewById(R.id.textviewLoadmoreHome);
        swipeRefreshLayout = v.findViewById(R.id.home_refresh);
//        pbLoadmore = v.findViewById(R.id.progressBarLoadmoreHome);
        controller = new HomeController(this);
    }

    @Override
    public void onRefresh() {
        controller.onDataRequest();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public LinearLayout getLlLoadmore() {
        return llLoadmore;
    }

    public TextView getTvLoadmore() {
        return tvLoadmore;
    }

//    public ProgressBar getPbLoadmore() {
//        return pbLoadmore;
//    }

    public RecyclerView getHomeList() {
        return homeList;
    }

    public FloatingActionButton getFtCamera() {
        return ftCamera;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            if (resultCode == RESULT_OK){
                controller.launchUploadActivity();
            }else if (resultCode == RESULT_CANCELED){

            }else{

            }
        }
    }
}
