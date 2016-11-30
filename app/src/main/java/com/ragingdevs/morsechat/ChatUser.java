package com.ragingdevs.morsechat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexfylling on 18.11.2016.
 */

public class ChatUser implements Serializable{

    Long id;
    String username,token;
    ArrayList<Message> messageList;
    boolean selected;

    public ChatUser(long id, String username) {
        this.id = id;
        this.username = username;
        this.messageList = new ArrayList<>();
        selected = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean checked) {
        selected = checked;
    }

    public void toggleSelect(){
        selected = !selected;
    }
}
