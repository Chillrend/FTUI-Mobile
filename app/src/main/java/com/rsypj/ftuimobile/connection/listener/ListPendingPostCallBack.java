package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.ListPendingPostModel;

import java.util.ArrayList;

public interface ListPendingPostCallBack {
    public void onDataSetResutl(ArrayList<ListPendingPostModel> response);
    public void onDataIsEmpty();
    public void onRequestError(String errorMessage);
}
