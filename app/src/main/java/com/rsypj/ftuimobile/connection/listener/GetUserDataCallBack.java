package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.UserModel;

import java.util.ArrayList;

public interface GetUserDataCallBack {
    public void onDataSetResutl(ArrayList<UserModel> response);
    public void onDataIsEmpty();
    public void onRequestError(String errorMessage);
}
