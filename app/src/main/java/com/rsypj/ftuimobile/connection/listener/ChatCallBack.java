package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.ChatModel;

import java.util.ArrayList;

public interface ChatCallBack {
    public void onDataSetResutl(ArrayList<ChatModel> response);
    public void onDataIsEmpty();
    public void onRequestError(String errorMessage);
}
