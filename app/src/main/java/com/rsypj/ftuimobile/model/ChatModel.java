package com.rsypj.ftuimobile.model;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class ChatModel {

    String id;
    String owner;
    String recipient;
    String name;
    String content;
    long time;

    public ChatModel(String id, String owner, String recipient, String name, String content, long time) {
        this.id = id;
        this.owner = owner;
        this.recipient = recipient;
        this.name = name;
        this.content = content;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public long getTime() {
        return time;
    }
}
