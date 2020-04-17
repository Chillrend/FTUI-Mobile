package com.rsypj.ftuimobile.ui.activity.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.VerifUserAdapter;
import com.rsypj.ftuimobile.adapter.listener.ButtonVerifUser;
import com.rsypj.ftuimobile.connection.LoginRequest;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.connection.listener.UnverifiedUserCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.UnverifiedUserModel;
import com.rsypj.ftuimobile.ui.activity.VerifUserActivity;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class VerifUserController {

    ArrayList<UnverifiedUserModel> data = new ArrayList<>();
    VerifUserActivity activity;
    VerifUserAdapter adapter;

    public VerifUserController(VerifUserActivity activity) {
        this.activity = activity;

        setAdapter();
        setRefreshLayout();
        onRequestData();
        onScroll();
        onLoadmoreClicked();
        customActionBar();
    }

    private void customActionBar(){
        activity.getTvTitle().setText("Verifikasi User");
        activity.hideActionBar();
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
        adapter = new VerifUserAdapter(activity, data);

        adapter.clickedVerifUser(new ButtonVerifUser() {
            @Override
            public void onButtonVerifClicked(String nim, String name, String email) {
                createAndShowAlertDialog(nim, name);
            }
        });

        activity.getListUser().setLayoutManager(new LinearLayoutManager(activity));
        activity.getListUser().setItemAnimator(new DefaultItemAnimator());
        activity.getListUser().setAdapter(adapter);
    }

    private void createAndShowAlertDialog(final String nim, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Verifikasi User");
        builder.setMessage("Apakah kamu yakin ingin memverifikasi " + name + " dengan NIM/NIP " + nim + "?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onButtonSubmitClicked(nim);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onButtonSubmitClicked(String nim){
        Helper.NIM = nim;
        new LoginRequest(activity).onSubmitData(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {
                if (response.equals("Success")){
                    activity.showToast("Success");
                    onRequestData();
                }else {
                    activity.showToast("Error");
                }
            }

            @Override
            public void onError(String message) {
                activity.showToast(message);
            }
        });
    }

    public void onRequestData(){
        new LoginRequest(activity).onRequestUnverifiedUser(new UnverifiedUserCallBack() {
            @Override
            public void onDataSetResult(ArrayList<UnverifiedUserModel> response) {
                data.clear();
                data.addAll(response);
                adapter.notifyDataSetChanged();
                activity.getSwipeRefreshLayout().setRefreshing(false);
                activity.getTvnoData().setVisibility(View.GONE);
            }

            @Override
            public void onDataNotFound() {
                activity.showToast("Data Not Found!");
                activity.getTvnoData().setVisibility(View.VISIBLE);
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onErrorRequest(String errorMessage) {
                activity.showToast(errorMessage);
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }

    private void onRequestDataMore(){
        new LoginRequest(activity).onRequestUnverifiedUserLoadMore(new UnverifiedUserCallBack() {
            @Override
            public void onDataSetResult(ArrayList<UnverifiedUserModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
                activity.getLlLoadMore().setVisibility(View.GONE);
            }

            @Override
            public void onDataNotFound() {
                activity.showToast("Data Not Found");

            }

            @Override
            public void onErrorRequest(String errorMessage) {
                activity.showToast(errorMessage);
            }
        });
    }

    private void onScroll(){
        activity.getListUser().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    activity.getLlLoadMore().setVisibility(View.GONE);
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (URL.nextPageVerifiedUser.equals(null)){
                        activity.getLlLoadMore().setVisibility(View.GONE);
                    }else {
                        activity.getLlLoadMore().setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && activity.getLlLoadMore().isShown()){
                    activity.getLlLoadMore().setVisibility(View.GONE);
                }
            }
        });
    }

    private void onLoadmoreClicked(){
        activity.getLlLoadMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestDataMore();
            }
        });
    }
}
