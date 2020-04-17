package com.rsypj.ftuimobile.ui.activity.controller;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.PDFDetailAdapter;
import com.rsypj.ftuimobile.connection.LauncherRequest;
import com.rsypj.ftuimobile.connection.listener.PDFDetailCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.ui.activity.PDFDetailActivity;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class PDFDetailController {

    PDFDetailActivity activity;
    PDFDetailAdapter adapter;
    ArrayList<DetailPDFModel> data = new ArrayList<>();

    public PDFDetailController(PDFDetailActivity activity) {
        this.activity = activity;

        Helper.idpdf = activity.getIntent().getStringExtra(Helper.DATA);

        setAdapter();
        setRefreshLayout();
        customActionBar();
        onSearchBook();
    }

    private void filter(String text){
        ArrayList<DetailPDFModel> temp = new ArrayList();
        for(DetailPDFModel d: data){
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

    private void customActionBar(){
        activity.hideActionBar();
        activity.getTvTitle().setText(activity.getIntent().getStringExtra(Helper.DATANAME));
        activity.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void setRefreshLayout(){
        activity.getSwipeRefreshLayout().setOnRefreshListener(activity);
        activity.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        activity.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                onRequestData();
            }
        });
    }

    private void setAdapter(){
        adapter = new PDFDetailAdapter(activity, data);
        activity.getListPdf().setLayoutManager(new GridLayoutManager(activity, 3));
        activity.getListPdf().setItemAnimator(new DefaultItemAnimator());
        activity.getListPdf().setAdapter(adapter);
    }

    public void onRequestData(){
        Log.d("IDPDF", Helper.idpdf);
        new LauncherRequest(activity).onRequestDetailPDF(new PDFDetailCallBack() {
            @Override
            public void onDataSetResult(ArrayList<DetailPDFModel> response) {
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
                activity.getNoData().setVisibility(View.GONE);
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }
}
