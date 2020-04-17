package com.rsypj.ftuimobile.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by dhadotid on 06/04/2018.
 */

public abstract class SmartSipBaseFragment extends Fragment {

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutID(), container, false);
        initItem(v);

        progressDialog = new ProgressDialog(v.getContext());

        return v;
    }

    public void showProgressDialog(String title, String message, Boolean isCancelable){
        if (progressDialog.isShowing()){
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(isCancelable);
            progressDialog.show();
        }
    }

    public void dismisProgressDialog(){
        if (progressDialog.isShowing() && progressDialog != null){
            progressDialog.dismiss();
        }
    }

    public void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public abstract int getLayoutID();
    public abstract void initItem(View v);
}
