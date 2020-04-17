package com.rsypj.ftuimobile.model;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class MessageModel {
    int id;
    String sender;
    String message;
    long time;

    public MessageModel(int id, String sender, String message, long time) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }
}
