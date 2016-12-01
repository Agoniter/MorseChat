package com.ragingdevs.morsechat;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ragingdevs.utils.FileHandler;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

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

    public boolean isLoggedIn(Context context){

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

    public void clearMe(Context context){
        messages = new ArrayList<>();
        user = null;
        contactList = new ArrayList<>();
        FileHandler fh = new FileHandler();
        try {
            fh.writeToFile("", "token.txt", context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String doTokenCheck(Context context){
        FileHandler fh = new FileHandler();
        ServerCom sc = new  ServerCom();
        String token = "";
        try {
            token = fh.readFromFile(context, "token.txt");
        } catch (IOException e) {
            return "";
        }

        sc.setAuthHead(token);
        return token;
    }
}
