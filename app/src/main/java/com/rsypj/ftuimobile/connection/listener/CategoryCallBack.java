package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.CategoryModel;

import java.util.ArrayList;

public interface CategoryCallBack {
    public void onDataSetResutl(ArrayList<CategoryModel> response);
    public void onDataIsEmpty();
    public void onRequestError(String errorMessage);
}
