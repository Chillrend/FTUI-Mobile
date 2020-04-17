package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.LauncherModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public interface LauncherCallBack {
    void onDataSetResult(ArrayList<LauncherModel> response);
    void onDataIsEmpty();
    void onErrorRequest(String errorMessage);
}
