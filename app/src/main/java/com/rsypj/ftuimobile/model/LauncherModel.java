package com.rsypj.ftuimobile.model;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherModel {

    int id;
    String judul;
    String deskripsi;
    String gambar;

    public LauncherModel(int id, String judul, String deskripsi, String gambar) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getGambar() {
        return gambar;
    }
}
