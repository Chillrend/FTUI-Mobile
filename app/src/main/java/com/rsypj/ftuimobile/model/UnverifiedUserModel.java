package com.rsypj.ftuimobile.model;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class UnverifiedUserModel {

    String nim;
    String nama;
    String email;

    public UnverifiedUserModel(String nim, String nama, String email) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }
}
