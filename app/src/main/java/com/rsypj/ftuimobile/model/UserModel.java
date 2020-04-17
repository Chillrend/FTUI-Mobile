package com.rsypj.ftuimobile.model;

public class UserModel {
    String nim;
    String name;
    String email;
    int role;

    public UserModel(String nim, String name, String email, int role) {
        this.nim = nim;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getNim() {
        return nim;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }
}
