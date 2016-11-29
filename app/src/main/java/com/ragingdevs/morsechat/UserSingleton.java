package com.ragingdevs.morsechat;

/**
 * Created by hallv on 29.11.2016.
 */
public class UserSingleton {

    private static UserSingleton mInstance = null;

    private String token;

    private UserSingleton(){
        token = "";
    }

    public static UserSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new UserSingleton();
        }
        return mInstance;
    }

    public String getToken(){
        return this.token;
    }

    public void setToken(String value){
        token = value;
    }

    public boolean isLoggedIn(){
        if(token.equals("")){
            return false;
        }
        else{
            return true;
        }
    }

}
