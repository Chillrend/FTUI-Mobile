package com.rsypj.ftuimobile.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.VerifUserController;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class VerifUserActivity extends SmartSipBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    TextView tvTitle;
    ImageView ivBack;
    RecyclerView listUser;
    TextView tvnoData;
    LinearLayout llLoadMore;
    SwipeRefreshLayout swipeRefreshLayout;
    VerifUserController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_verif_user;
    }

    @Override
    public void initItem() {
        tvTitle = findViewById(R.id.activity_verif_user_tvNama);
        ivBack = findViewById(R.id.activity_verif_user_ivBack);
        listUser = findViewById(R.id.list_unverif_user);
        llLoadMore = findViewById(R.id.linearLoadmore);
        swipeRefreshLayout = findViewById(R.id.verif_user_refresh);
        tvnoData = findViewById(R.id.tidakAdaData);
        controller = new VerifUserController(this);
    }

    @Override
    public void onRefresh() {
        controller.onRequestData();
    }

    public LinearLayout getLlLoadMore() {
        return llLoadMore;
    }

    public TextView getTvnoData() {
        return tvnoData;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public RecyclerView getListUser() {
        return listUser;
    }
}
