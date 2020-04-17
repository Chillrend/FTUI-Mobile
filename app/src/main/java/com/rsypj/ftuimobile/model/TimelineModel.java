package com.rsypj.ftuimobile.model;

public class TimelineModel {
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

    public TimelineModel(int idKeluhan, String nim, String description, String foto, String timePost, int is_approved, String name, String category, String categoryIcon, String location) {
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
}
