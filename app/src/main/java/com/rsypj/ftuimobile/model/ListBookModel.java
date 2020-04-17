package com.rsypj.ftuimobile.model;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class ListBookModel {

    int id;
    int id_launcher;
    String nama;
    String gambar;

    public ListBookModel(int id, int id_launcher, String nama, String gambar) {
        this.id = id;
        this.id_launcher = id_launcher;
        this.nama = nama;
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public int getId_launcher() {
        return id_launcher;
    }

    public String getNama() {
        return nama;
    }

    public String getGambar() {
        return gambar;
    }
}
