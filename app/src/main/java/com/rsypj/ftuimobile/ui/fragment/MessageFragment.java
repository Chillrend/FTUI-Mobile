package com.rsypj.ftuimobile.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseFragment;
import com.rsypj.ftuimobile.ui.fragment.controller.MessageController;

/**
 * Created by dhadotid on 08/04/2018.
 */

public class MessageFragment extends SmartSipBaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    TextView tvMessagenotFound;
    RecyclerView messageList;
    FloatingActionButton btnAddMessage;
    SwipeRefreshLayout swipeRefreshLayout;
    MessageController controller;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    public void initItem(View v) {
        messageList = v.findViewById(R.id.message_list);
        tvMessagenotFound = v.findViewById(R.id.tvMessagenotfound);
        btnAddMessage = v.findViewById(R.id.addNewMessage);
        swipeRefreshLayout = v.findViewById(R.id.message_refresh);
        controller = new MessageController(this);
    }

    @Override
    public void onRefresh() {
        controller.loadDataFromServer();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public RecyclerView getMessageList() {
        return messageList;
    }

    public TextView getTvMessagenotFound() {
        return tvMessagenotFound;
    }

    public FloatingActionButton getBtnAddMessage() {
        return btnAddMessage;
    }
}
