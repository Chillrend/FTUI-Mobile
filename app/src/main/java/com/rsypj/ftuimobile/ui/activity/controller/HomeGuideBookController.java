package com.rsypj.ftuimobile.ui.activity.controller;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.HomeGuideBookAdapter;
import com.rsypj.ftuimobile.connection.LauncherRequest;
import com.rsypj.ftuimobile.connection.listener.PDFCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.ListBookModel;
import com.rsypj.ftuimobile.ui.activity.HomeGuideBookActivity;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class HomeGuideBookController {

    HomeGuideBookActivity activity;
    HomeGuideBookAdapter adapter;
    ArrayList<ListBookModel> data = new ArrayList<>();

    public HomeGuideBookController(HomeGuideBookActivity activity) {
        this.activity = activity;
        //Helper.idlauncher = activity.getIntent().getStringExtra(Helper.DATA);
        setAdapter();
        requestDataFromServer();
        setRefreshLayout();
        onSearchBook();
    }

    private void setRefreshLayout(){
        activity.getSwipeRefreshLayout().setOnRefreshListener(activity);
        activity.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        activity.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                requestDataFromServer();
            }
        });
    }

    private void setAdapter(){
        adapter = new HomeGuideBookAdapter(activity, data);
        activity.getListBook().setLayoutManager(new GridLayoutManager(activity, 3));
        activity.getListBook().setItemAnimator(new DefaultItemAnimator());
        activity.getListBook().setAdapter(adapter);
    }

    private void filter(String text){
        ArrayList<ListBookModel> temp = new ArrayList();
        for(ListBookModel d: data){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getNama().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
        if (temp.size() < 1){
            activity.getNoData().setVisibility(View.VISIBLE);
        }else {
            activity.getNoData().setVisibility(View.GONE);
        }
    }

    private void onSearchBook(){
        activity.getEtSearch().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    public void requestDataFromServer(){
        Log.d("IDLAUNCHER", Helper.idlauncher+"");
        new LauncherRequest(activity).onRequestDataPDF(new PDFCallBack() {
            @Override
            public void onDataSetResult(ArrayList<ListBookModel> response) {
                data.clear();
                data.addAll(response);
                activity.getSwipeRefreshLayout().setRefreshing(false);
                adapter.notifyDataSetChanged();
                activity.getNoData().setVisibility(View.GONE);
            }

            @Override
            public void onDataIsEmpty() {
                activity.getNoData().setVisibility(View.VISIBLE);
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
                activity.getNoData().setVisibility(View.GONE);
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }
}
