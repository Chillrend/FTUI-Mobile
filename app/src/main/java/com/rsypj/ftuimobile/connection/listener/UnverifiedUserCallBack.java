package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.UnverifiedUserModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public interface UnverifiedUserCallBack {
    void onDataSetResult(ArrayList<UnverifiedUserModel> response);
    void onDataNotFound();
    void onErrorRequest(String errorMessage);
}
