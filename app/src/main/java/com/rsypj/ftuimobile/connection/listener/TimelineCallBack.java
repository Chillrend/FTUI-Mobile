package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.TimelineModel;

import java.util.ArrayList;

public interface TimelineCallBack {
    public void onDataSetResutl(ArrayList<TimelineModel> response);
    public void onDataIsEmpty();
    public void onRequestError(String errorMessage);
}
