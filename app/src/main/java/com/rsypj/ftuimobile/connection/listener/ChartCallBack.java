package com.rsypj.ftuimobile.connection.listener;

import com.rsypj.ftuimobile.model.PieChartModel;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 29/06/2018.
 */
public interface ChartCallBack {
    void onDataSetResult(ArrayList<PieChartModel> response);
    void onDataIsEmpty();
    void onRequestError(String errorMessage);
}
