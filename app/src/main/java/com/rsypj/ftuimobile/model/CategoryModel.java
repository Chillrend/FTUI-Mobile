package com.rsypj.ftuimobile.model;

public class CategoryModel {
    int idCategory;
    String kategori;
    String icon;

    public CategoryModel(int idCategory, String kategori, String icon) {
        this.idCategory = idCategory;
        this.kategori = kategori;
        this.icon = icon;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getKategori() {
        return kategori;
    }

    public String getIcon() {
        return icon;
    }
}
