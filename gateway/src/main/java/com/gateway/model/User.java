package com.gateway.model;

import lombok.Data;

@Data
public class User {
    private long chatId;
    private boolean registered;

    public User(long chatId, boolean registered) {
        this.chatId = chatId;
        this.registered = registered;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
