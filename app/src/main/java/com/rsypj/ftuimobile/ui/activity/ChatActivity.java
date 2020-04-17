package com.rsypj.ftuimobile.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.ChatController;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class ChatActivity extends SmartSipBaseActivity {

    ImageView backbtn;
    TextView tvActionBarName;
    RecyclerView chatList;
    EditText etChat;
    FloatingActionButton sendChat;
    ChatController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_chat;
    }

    @Override
    public void initItem() {
        backbtn = findViewById(R.id.activity_chat_ivBack);
        tvActionBarName = findViewById(R.id.activity_chat_tvNama);
        chatList = findViewById(R.id.contentChat);
        etChat = findViewById(R.id.editTextChat);
        sendChat = findViewById(R.id.sendButton);

        controller = new ChatController(this);
    }

    public ImageView getBackbtn() {
        return backbtn;
    }

    public TextView getTvActionBarName() {
        return tvActionBarName;
    }

    public RecyclerView getChatList() {
        return chatList;
    }

    public EditText getEtChat() {
        return etChat;
    }

    public FloatingActionButton getSendChat() {
        return sendChat;
    }
}
