package com.rsypj.ftuimobile.model;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class NotificationModel {
    int id;
    String status;
    int image;
    long time;

    public NotificationModel(int id, String status, int image, long time) {
        this.id = id;
        this.status = status;
        this.image = image;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getImage() {
        return image;
    }

    public long getTime() {
        return time;
    }
}
