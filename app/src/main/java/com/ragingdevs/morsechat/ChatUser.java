package com.ragingdevs.morsechat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexfylling on 18.11.2016.
 */

public class ChatUser {

    long id;
    String username,token;
    ArrayList<Message> messageList;

    public ChatUser(long id, String username) {
        this.id = id;
        this.username = username;
        this.messageList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public String getLastMessage(){
        return messageList.get(messageList.size()-1).getMessage();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
