package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.DetailPDFModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public interface PDFDetailCallBack {
    void onDataSetResult(ArrayList<DetailPDFModel> response);
    void onDataIsEmpty();
    void onRequestError(String errorMessage);
}
