package com.rsypj.ftuimobile.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.PDFDetailController;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class PDFDetailActivity extends SmartSipBaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    ImageView ivBack;
    TextView tvTitle;
    TextView noData;
    EditText etSearch;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView listPdf;
    PDFDetailController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_pdf_detail;
    }

    @Override
    public void initItem() {
        etSearch = findViewById(R.id.home_guide_book_etSearch);
        ivBack = findViewById(R.id.pdf_detail_back);
        tvTitle = findViewById(R.id.pdf_detail_tvTitle);
        swipeRefreshLayout = findViewById(R.id.pdf_detail_refresh);
        listPdf = findViewById(R.id.pdf_detail_list);
        noData = findViewById(R.id.pdf_detail_noData);
        controller = new PDFDetailController(this);
    }

    public TextView getNoData() {
        return noData;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public RecyclerView getListPdf() {
        return listPdf;
    }

    @Override
    public void onRefresh() {
        controller.onRequestData();
    }

    public EditText getEtSearch() {
        return etSearch;
    }
}
