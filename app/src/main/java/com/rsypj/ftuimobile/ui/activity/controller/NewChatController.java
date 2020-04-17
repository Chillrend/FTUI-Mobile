package com.rsypj.ftuimobile.ui.activity.controller;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.rsypj.ftuimobile.adapter.NewChatAdapter;
import com.rsypj.ftuimobile.adapter.listener.NewChatKotakClick;
import com.rsypj.ftuimobile.connection.LoginRequest;
import com.rsypj.ftuimobile.connection.listener.GetUserDataCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.UserModel;
import com.rsypj.ftuimobile.ui.activity.ChatActivity;
import com.rsypj.ftuimobile.ui.activity.NewChatActivity;

import java.util.ArrayList;

public class NewChatController {

    NewChatActivity activity;
    NewChatAdapter adapter;
    ArrayList<UserModel> data = new ArrayList<>();

    public NewChatController(NewChatActivity activity) {
        this.activity = activity;

        setAdapter();
        onScroll();
        onLoadAllUser();
        activity.getLlLoadmore().setVisibility(View.GONE);
        //onClickedLoadMore();
        customActionBar();
        onSearchData();
    }

    private void filter(String text){
        ArrayList<UserModel> temp = new ArrayList();
        for(UserModel d: data){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }

    private void onSearchData(){
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
        activity.getTvActionbar().setText("New Chat");
        activity.getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onClickedLoadMore(){
        activity.getLlLoadmore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onLoadMoreDataUser();
            }
        });
    }

    private void setAdapter(){
        adapter = new NewChatAdapter(activity, data);


        adapter.kotakClicked(new NewChatKotakClick() {
            @Override
            public void onKotakClicked(String nim, String name) {
                Intent in = new Intent(activity, ChatActivity.class);
                in.putExtra("Sender", name);
                Helper.sender = nim;
                Log.d("Nim in controller", Helper.sender);
                activity.startActivity(in);
                activity.finish();
            }
        });

        activity.getListChat().setLayoutManager(new LinearLayoutManager(activity));
        activity.getListChat().setItemAnimator(new DefaultItemAnimator());
        activity.getListChat().setAdapter(adapter);
    }

    private void onLoadAllUser(){
        new LoginRequest(activity).requestallUser(new GetUserDataCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<UserModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDataIsEmpty() {
                activity.showToast("Data Not Found!");
            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
            }
        });
    }

    private void onLoadMoreDataUser(){
        new LoginRequest(activity).requestallUserLoadMore(new GetUserDataCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<UserModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDataIsEmpty() {
                activity.showToast("Data Not Found!");
            }

            @Override
            public void onRequestError(String errorMessage) {
//                activity.showToast(errorMessage);
            }
        });
    }

    private void onScroll(){
        activity.getListChat().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    activity.getLlLoadmore().setVisibility(View.GONE);
                }
                if (!recyclerView.canScrollVertically(1)) {
                    if (URL.nextPageUser.equals(null)){
                        activity.getLlLoadmore().setVisibility(View.GONE);
                    }else {
//                        activity.getLlLoadmore().setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && activity.getLlLoadmore().isShown()){
                    activity.getLlLoadmore().setVisibility(View.GONE);
                }
                if (URL.nextPageUser.equals(null)){
                    activity.getLlLoadmore().setVisibility(View.GONE);
                }
            }
        });
    }
}
