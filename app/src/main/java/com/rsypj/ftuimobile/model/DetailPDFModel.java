package com.rsypj.ftuimobile.model;

/**
 * Author By dhadotid
 * Date 10/07/2018.
 */
public class DetailPDFModel {

    String idpdfdet;
    String idpdf;
    String nama;
    String gambar;
    String pdf;

    public DetailPDFModel(String idpdfdet, String idpdf, String nama, String gambar, String pdf) {
        this.idpdfdet = idpdfdet;
        this.idpdf = idpdf;
        this.nama = nama;
        this.gambar = gambar;
        this.pdf = pdf;
    }

    public String getIdpdfdet() {
        return idpdfdet;
    }

    public String getIdpdf() {
        return idpdf;
    }

    public String getNama() {
        return nama;
    }

    public String getGambar() {
        return gambar;
    }

    public String getPdf() {
        return pdf;
    }
}
