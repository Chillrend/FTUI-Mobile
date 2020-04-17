package com.rsypj.ftuimobile.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

/**
 * Author By dhadotid
 * Date 13/07/2018.
 */
public abstract class TestActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initItem();
        onSaveInstace(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    public void showToast(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(String title, String message, Boolean isCancelable) {
        if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(isCancelable);
            progressDialog.show();
        }
    }

    public void dismisProgressDialog() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void customActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        SpannableString span = new SpannableString(title);
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#4FC3F7")), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(span);
    }

    public abstract int getLayoutID();

    public abstract void initItem();

    public abstract void onSaveInstace(Bundle savedInstanceState);
}
