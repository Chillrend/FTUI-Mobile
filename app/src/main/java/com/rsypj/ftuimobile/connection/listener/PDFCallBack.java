package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.ListBookModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public interface PDFCallBack {

    void onDataSetResult(ArrayList<ListBookModel> response);
    void onDataIsEmpty();
    void onRequestError(String errorMessage);
}
