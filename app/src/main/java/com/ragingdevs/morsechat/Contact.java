package com.ragingdevs.morsechat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexfylling on 18.11.2016.
 */

public class Contact {

    long id;
    String username;
    Date created;
    ArrayList<Message> messageList;

    public Contact(long id, String username, Date created) {
        this.id = id;
        this.username = username;
        this.created = created;
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

}
