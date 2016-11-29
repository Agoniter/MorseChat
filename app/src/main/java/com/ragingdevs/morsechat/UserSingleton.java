package com.ragingdevs.morsechat;

import java.util.ArrayList;

/**
 * Created by hallv on 29.11.2016.
 */
public class UserSingleton {

    private static UserSingleton mInstance = null;
    private static ChatUser user;
    private ArrayList<ChatUser> contactList;


    private UserSingleton(){
        contactList = new ArrayList<>();
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

    public ChatUser getUser() {
        return user;
    }

    public void setUser(ChatUser user) {
        this.user = user;
    }
}
