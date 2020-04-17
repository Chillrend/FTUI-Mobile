package com.rsypj.ftuimobile.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;
import com.rsypj.ftuimobile.ui.activity.controller.NewChatController;

public class NewChatActivity extends SmartSipBaseActivity {

    ImageView ivBack;
    TextView tvActionbar;
    RecyclerView listChat;
    EditText etSearch;
    LinearLayout llLoadmore;
    NewChatController controller;

    @Override
    public int getLayoutID() {
        return R.layout.activity_new_chat;
    }

    @Override
    public void initItem() {
        ivBack = findViewById(R.id.activity_newchat_ivBack);
        tvActionbar = findViewById(R.id.activity_newchat_tvNama);
        listChat = findViewById(R.id.userList);
        etSearch = findViewById(R.id.newchat_etSearch);
        llLoadmore = findViewById(R.id.linearLoadmore);

        controller = new NewChatController(this);
    }

    public LinearLayout getLlLoadmore() {
        return llLoadmore;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public TextView getTvActionbar() {
        return tvActionbar;
    }

    public RecyclerView getListChat() {
        return listChat;
    }

    public EditText getEtSearch() {
        return etSearch;
    }
}
