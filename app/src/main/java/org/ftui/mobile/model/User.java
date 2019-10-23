package org.ftui.mobile.model;

public class User {
    private String email;
    private String token;
    private String fbtoken;

    public String getFbtoken() {
        return fbtoken;
    }

    public void setFbtoken(String fbtoken) {
        this.fbtoken = fbtoken;
    }


    public User(String email, String token, String fbtoken) {
        this.email = email;

        this.token = token;

        this.fbtoken = fbtoken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
