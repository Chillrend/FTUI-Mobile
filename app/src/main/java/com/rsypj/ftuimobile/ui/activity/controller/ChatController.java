package com.rsypj.ftuimobile.ui.activity.controller;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rsypj.ftuimobile.adapter.ChatAdapter;
import com.rsypj.ftuimobile.connection.ChatRequest;
import com.rsypj.ftuimobile.connection.listener.ChatCallBack;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.model.ChatModel;
import com.rsypj.ftuimobile.ui.activity.ChatActivity;

import java.util.ArrayList;

import static com.rsypj.ftuimobile.helper.CalenderHelper.getCalender;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class ChatController {

    ChatAdapter adapter;
    ChatActivity activity;
    ArrayList<ChatModel> data = new ArrayList<>();


    public ChatController(ChatActivity activity) {
        this.activity = activity;

        activity.getEtChat().requestFocus();
        setAdapter();
        onLoadData();
        onSendChat();
        setActionBar();
        onBackClicked();
    }

    private void setAdapter(){
        adapter = new ChatAdapter(activity, data);

        activity.getChatList().setLayoutManager(new LinearLayoutManager(activity));
        activity.getChatList().setItemAnimator(new DefaultItemAnimator());
        activity.getChatList().setAdapter(adapter);
    }

    private void onSendChat(){
        activity.getSendChat().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.add(new ChatModel("1", SharePref.getmInstance(activity).getNim(), Helper.sender, SharePref.getmInstance(activity).getName(), activity.getEtChat().getText().toString(), getCalender()));
                sendDataToServer();
                adapter.notifyDataSetChanged();
                activity.getChatList().smoothScrollToPosition(data.size() - 1);
                activity.getEtChat().setText("");
                activity.getEtChat().requestFocus();
            }
        });
    }

    private void sendDataToServer(){
        Helper.pesan = activity.getEtChat().getText().toString();
        new ChatRequest(activity).postChat(new LoginCallBack() {
            @Override
            public void onRequestSuccess(String response) {

            }

            @Override
            public void onError(String message) {
                activity.showToast(message);
            }
        });
    }

    private void onBackClicked(){
        activity.getBackbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    private void onLoadData(){
        new ChatRequest(activity).requestChatDalem(new ChatCallBack() {
            @Override
            public void onDataSetResutl(ArrayList<ChatModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDataIsEmpty() {

            }

            @Override
            public void onRequestError(String errorMessage) {
                activity.showToast(errorMessage);
            }
        });
    }

    private void setActionBar(){
        activity.hideActionBar();
        activity.getTvActionBarName().setText(activity.getIntent().getStringExtra("Sender"));
    }
}
