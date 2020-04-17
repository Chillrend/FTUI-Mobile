package com.rsypj.ftuimobile.model;

public class ListPendingPostModel {
    int idKeluhan;
    String nim;
    String description;
    String foto;
    String timePost;
    int is_approved;
    String name;
    String category;
    String categoryIcon;
    String location;
    String created_at;
    String updated_at;

    public ListPendingPostModel(int idKeluhan, String nim, String description, String foto, String timePost, int is_approved, String name, String category, String categoryIcon, String location, String created_at, String updated_at) {
        this.idKeluhan = idKeluhan;
        this.nim = nim;
        this.description = description;
        this.foto = foto;
        this.timePost = timePost;
        this.is_approved = is_approved;
        this.name = name;
        this.category = category;
        this.categoryIcon = categoryIcon;
        this.location = location;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getIdKeluhan() {
        return idKeluhan;
    }

    public String getNim() {
        return nim;
    }

    public String getDescription() {
        return description;
    }

    public String getFoto() {
        return foto;
    }

    public String getTimePost() {
        return timePost;
    }

    public int getIs_approved() {
        return is_approved;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public String getLocation() {
        return location;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
