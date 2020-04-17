package com.rsypj.ftuimobile.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.AduanTeratasiController;

public class AduanTeratasiFragment extends SmartSipBaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    LinearLayout llLoadMore;
    TextView tvLoadMore;
    RecyclerView listPost;
    SwipeRefreshLayout swipeRefreshLayout;
    AduanTeratasiController controller;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_manager ;
    }

    @Override
    public void initItem(View v) {
        listPost = v.findViewById(R.id.listPost);
        llLoadMore = v.findViewById(R.id.linearLoadmore);
        tvLoadMore = v.findViewById(R.id.textviewLoadmoreHome);
        swipeRefreshLayout = v.findViewById(R.id.manager_refresh);
        controller = new AduanTeratasiController(this);
    }

    @Override
    public void onRefresh() {
        controller.onPendingPostDataRequest();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
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
