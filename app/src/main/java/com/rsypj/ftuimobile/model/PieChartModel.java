package com.rsypj.ftuimobile.model;

/**
 * Author By dhadotid
 * Date 29/06/2018.
 */
public class PieChartModel {

    int id;
    String name;
    float persentase;
    int jumlah;

    public PieChartModel(int id, String name, float persentase, int jumlah) {
        this.id = id;
        this.name = name;
        this.persentase = persentase;
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPersentase() {
        return persentase;
    }

    public int getJumlah() {
        return jumlah;
    }
}
