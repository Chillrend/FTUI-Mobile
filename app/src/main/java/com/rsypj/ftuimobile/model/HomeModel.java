package com.rsypj.ftuimobile.model;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class HomeModel {

    int id;
    String nama;
    String status;
    String comment;
    String like;
    String loc;
    int statuspic;
    long time;
    int isPost;

    public HomeModel(int id, String nama, String status, String comment, String like, String loc, int statuspic, long time, int isPost) {
        this.id = id;
        this.nama = nama;
        this.status = status;
        this.comment = comment;
        this.like = like;
        this.loc = loc;
        this.statuspic = statuspic;
        this.time = time;
        this.isPost = isPost;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public String getLike() {
        return like;
    }

    public String getLoc() {
        return loc;
    }

    public int getStatuspic() {
        return statuspic;
    }

    public long getTime() {
        return time;
    }

    public int getIsPost() {
        return isPost;
    }
}
