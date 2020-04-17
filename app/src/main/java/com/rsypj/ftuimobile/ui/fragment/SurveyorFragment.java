package com.rsypj.ftuimobile.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.SurveyorController;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class SurveyorFragment extends SmartSipBaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    LinearLayout llLoadMore;
    TextView tvLoadMore;
    RecyclerView listPost;
    TextView tvTidakAdaData;
    SwipeRefreshLayout swipeRefreshLayout;
    SurveyorController controller;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_manager;
    }

    @Override
    public void initItem(View v) {
        listPost = v.findViewById(R.id.listPost);
        llLoadMore = v.findViewById(R.id.linearLoadmore);
        tvLoadMore = v.findViewById(R.id.textviewLoadmoreHome);
        tvTidakAdaData = v.findViewById(R.id.tidakAdaData);
        swipeRefreshLayout = v.findViewById(R.id.manager_refresh);
        controller = new SurveyorController(this);
    }

    @Override
    public void onRefresh() {
        controller.onPendingPostDataRequest();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
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

    public TextView getTvTidakAdaData() {
        return tvTidakAdaData;
    }

    public LinearLayout getLlLoadMore() {
        return llLoadMore;
    }

    public TextView getTvLoadMore() {
        return tvLoadMore;
    }

    public RecyclerView getListPost() {
        return listPost;
    }
}
