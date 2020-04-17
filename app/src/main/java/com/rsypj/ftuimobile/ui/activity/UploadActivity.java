package com.rsypj.ftuimobile.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.UploadController;

/**
 * Created by dhadotid on 11/04/2018.
 */

public class UploadActivity extends SmartSipBaseActivity {

    ImageView ivBack;
    ImageView ivSend;
    ImageView ivImage;
    TextView tvActionBar;
    EditText tvLocation;
    EditText etKet;
    EditText etSearch;
    RecyclerView catList;
    UploadController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_upload;
    }

    @Override
    public void initItem() {
        ivBack = findViewById(R.id.activity_upload_ivBack);
        ivSend = findViewById(R.id.activity_upload_ivSend);
        ivImage = findViewById(R.id.activity_upload_ivImage);
        tvActionBar = findViewById(R.id.activity_upload_tvNama);
        tvLocation = findViewById(R.id.activity_upload_tvLoc);
        etKet = findViewById(R.id.activity_upload_etKeterangan);
        catList = findViewById(R.id.activity_upload_catList);
        etSearch = findViewById(R.id.upload_etSearch);

        controller = new UploadController(this);
    }

    public EditText getEtSearch() {
        return etSearch;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public ImageView getIvSend() {
        return ivSend;
    }

    public ImageView getIvImage() {
        return ivImage;
    }

    public TextView getTvActionBar() {
        return tvActionBar;
    }

    public EditText getTvLocation() {
        return tvLocation;
    }

    public EditText getEtKet() {
        return etKet;
    }

    public RecyclerView getCatList() {
        return catList;
    }
}
