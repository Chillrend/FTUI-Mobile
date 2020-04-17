package com.rsypj.ftuimobile.connection.listener;

public interface KeluhanDetailCallBack {
    public void onDataSetResult(int idKeluhanDetail, String name, String foto, String lokasi, String deskripsi, String rating);
    public void onDataIsEmpty();
    public void onError(String message);
}
