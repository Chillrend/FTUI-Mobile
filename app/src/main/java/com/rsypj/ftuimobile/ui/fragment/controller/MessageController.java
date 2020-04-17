package com.rsypj.ftuimobile.ui.fragment.controller;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.adapter.MessageAdapter;
import com.rsypj.ftuimobile.connection.ChatRequest;
import com.rsypj.ftuimobile.connection.listener.ChatCallBack;
import com.rsypj.ftuimobile.model.ChatModel;
import com.rsypj.ftuimobile.ui.activity.NewChatActivity;
import com.rsypj.ftuimobile.ui.fragment.MessageFragment;

import java.util.ArrayList;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class MessageController {

    MessageFragment fragment;
    MessageAdapter adapter;
    ArrayList<ChatModel> data = new ArrayList<>();

    public MessageController(MessageFragment fragment) {
        this.fragment = fragment;

        setAdapter();
        setRefreshLayout();
        cekData();
        loadDataFromServer();
        onClickNewMessage();
    }

    private void setRefreshLayout(){
        fragment.getSwipeRefreshLayout().setOnRefreshListener(fragment);
        fragment.getSwipeRefreshLayout().setColorSchemeResources(R.color.blueA400, R.color.blueA200);
        fragment.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                loadDataFromServer();
            }
        });
    }

    private void onClickNewMessage(){
        fragment.getBtnAddMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getContext().startActivity(new Intent(fragment.getContext(), NewChatActivity.class));
            }
        });
    }

    private void cekData(){
        if (data.size() > 0){
            fragment.getTvMessagenotFound().setVisibility(View.GONE);
        }
    }

    private void setAdapter(){
        adapter = new MessageAdapter(fragment.getContext(), data);

        fragment.getMessageList().setItemAnimator(new DefaultItemAnimator());
        fragment.getMessageList().setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        fragment.getMessageList().setAdapter(adapter);
    }

    public void loadDataFromServer(){
        new ChatRequest(fragment.getContext()).requestChatDepan(new ChatCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<ChatModel> response) {
                data.clear();
                data.addAll(response);
                adapter.notifyDataSetChanged();
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onDataIsEmpty() {
                fragment.getTvMessagenotFound().setVisibility(View.VISIBLE);
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }

            @Override
            public void onRequestError(String errorMessage) {
                fragment.showToast(errorMessage);
                fragment.getSwipeRefreshLayout().setRefreshing(false);
            }
        });
    }
}
