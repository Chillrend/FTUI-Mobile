package com.rsypj.ftuimobile.ui.fragment.controller;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.ListPendingPostAdapter;
import com.rsypj.ftuimobile.adapter.listener.ButtonApproved;
import com.rsypj.ftuimobile.connection.TimelineRequest;
import com.rsypj.ftuimobile.connection.listener.ListPendingPostCallBack;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ListPendingPostModel;
import com.rsypj.ftuimobile.ui.fragment.AduanTeratasiFragment;

import java.util.ArrayList;

public class AduanTeratasiController {

    AduanTeratasiFragment fragment;
    ListPendingPostAdapter adapter;
    ArrayList<ListPendingPostModel> data = new ArrayList<>();

    public AduanTeratasiController(AduanTeratasiFragment fragment) {
        this.fragment = fragment;

        onScroll();
        onLoadmoreClicked();
        setAdapter();
        setRefreshLayout();
        onPendingPostDataRequest();
    }

    private void setRefreshLayout(){
        fragment.getSwipeRefreshLayout().setOnRefreshListener(fragment);
        fragment.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        fragment.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                onPendingPostDataRequest();
            }
        });
    }

    private void onLoadmoreClicked(){
        fragment.getTvLoadMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPendingRequestMoreData();
            }
        });
    }

    private void onScroll(){
        fragment.getListPost().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fragment.getLlLoadMore().setVisibility(View.GONE);
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (URL.nextPagePendingPost.equals(null)){
                        fragment.getLlLoadMore().setVisibility(View.GONE);
                    }else {
                        fragment.getLlLoadMore().setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fragment.getLlLoadMore().isShown()){
                    fragment.getLlLoadMore().setVisibility(View.GONE);
                }
            }
        });
    }

    public void onPendingPostDataRequest(){
        new TimelineRequest(fragment.getContext()).requestCompleteKeluhan(new ListPendingPostCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<ListPendingPostModel> response) {
                data.clear();
                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onDataIsEmpty() {
                fragment.showToast("Data Not Found!");
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onRequestError(String errorMessage) {
                fragment.showToast(errorMessage);
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }

    private void onPendingRequestMoreData(){
        new TimelineRequest(fragment.getContext()).requestDekanCompleteLoadMore(new ListPendingPostCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<ListPendingPostModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getLlLoadMore().setVisibility(View.GONE);
            }

            @Override
            public void onDataIsEmpty() {
                fragment.showToast("Data Not Found!");
            }

            @Override
            public void onRequestError(String errorMessage) {
//                fragment.showToast(errorMessage);
            }
        });
    }

    private void setAdapter(){
        adapter = new ListPendingPostAdapter(fragment.getContext(), data);

        adapter.buttonApproveClicked(new ButtonApproved() {
            @Override
            public void onButtonApprovedClicked(int status, String lokasi) {

            }
        });
        fragment.getListPost().setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        fragment.getListPost().setItemAnimator(new DefaultItemAnimator());
        fragment.getListPost().setAdapter(adapter);
    }
}
