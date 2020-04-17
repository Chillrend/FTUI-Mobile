package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.NoTelphoneModel;

import java.util.ArrayList;

public interface PhoneCallBack {
    void onDataSetResult(ArrayList<NoTelphoneModel> response);
    void onDataIsEmpty();
    void onRequestError(String errorMessage);
}
