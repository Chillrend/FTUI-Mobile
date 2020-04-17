package com.rsypj.ftuimobile.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.TestActivity;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.ui.activity.controller.HomeGuideBookController;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class HomeGuideBookActivity extends TestActivity implements SwipeRefreshLayout.OnRefreshListener{

    EditText etSearch;
    TextView noData;
    RecyclerView listBook;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeGuideBookController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_home_guide_book;
    }

    @Override
    public void initItem() {
        hideActionBar();
        etSearch = findViewById(R.id.home_guide_book_etSearch);
        listBook = findViewById(R.id.home_guide_book_list);
        swipeRefreshLayout = findViewById(R.id.home_guide_book_refresh);
        noData = findViewById(R.id.home_guide_book_noData);
        controller = new HomeGuideBookController(this);
    }

    @Override
    public void onSaveInstace(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            Helper.idlauncher = savedInstanceState.getString(Helper.savedIntent);
        }else {
            Helper.idlauncher = getIntent().getStringExtra(Helper.DATA);
        }
    }

    @Override
    public void onRefresh() {
        controller.requestDataFromServer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Helper.savedIntent, Helper.idlauncher);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Helper.idlauncher = savedInstanceState.getString(Helper.savedIntent);
    }

    public TextView getNoData() {
        return noData;
    }

    public EditText getEtSearch() {
        return etSearch;
    }

    public RecyclerView getListBook() {
        return listBook;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
