package com.rsypj.ftuimobile.ui.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.ApprovedController;

public class ApprovedActivity extends SmartSipBaseActivity {

    TextView tvActionbar;
    ImageView ivBack;
    ImageView ivSend;
    EditText tvLocation;
    ImageView ivFoto;
    EditText etKet;
    ApprovedController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_approved;
    }

    @Override
    public void initItem() {
        tvActionbar = findViewById(R.id.activity_approved_tvNama);
        ivBack = findViewById(R.id.activity_approvedivBack);
        ivSend = findViewById(R.id.activity_approved_ivSend);
        tvLocation = findViewById(R.id.activity_approved_tvLoc);
        ivFoto = findViewById(R.id.activity_approved_ivImage);
        etKet = findViewById(R.id.activity_approved_etKeterangan);

        controller = new ApprovedController(this);
    }

    public TextView getTvActionbar() {
        return tvActionbar;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public ImageView getIvSend() {
        return ivSend;
    }

    public EditText getTvLocation() {
        return tvLocation;
    }

    public ImageView getIvFoto() {
        return ivFoto;
    }

    public EditText getEtKet() {
        return etKet;
    }
}
