package com.ragingdevs.morsechat;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hallv on 29.11.2016.
 */
public class UserSingleton {

    private static UserSingleton mInstance = null;
    private static ChatUser user;
    private ArrayList<ChatUser> contactList;
    private ArrayList<Message> messages;

    private UserSingleton(){
        contactList = new ArrayList<>();
        messages = new ArrayList<>();
        user = null;
    }

    public static UserSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new UserSingleton();
        }
        return mInstance;
    }

    public boolean isLoggedIn(){
        if(user != null) {
            if (user.getToken().equals("")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ChatUser> getContacts() {
        return contactList;
    }
    public void setChatUsers(ArrayList<ChatUser> newList){
        contactList = newList;
    }

    public ChatUser getUser() {
        return user;
    }

    public void setUser(ChatUser user) {
        this.user = user;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void clearMe(){
        messages = new ArrayList<>();
        user = null;
        contactList = new ArrayList<>();
    }
}
