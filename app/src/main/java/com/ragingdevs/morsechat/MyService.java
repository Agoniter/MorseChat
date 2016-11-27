package com.ragingdevs.morsechat;

import android.content.Context;

/**
 * Created by alexfylling on 04.11.2016.
 */

public class MyService {
    static MyService SERVICE;

    Context context;

    String uid;
    String pwd;

    /**
     * Construct me this
     * @param context
     */
    private MyService(Context context) {
        this.context = context;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public boolean isLoggedIn() {
        return getPwd() != null;
    }

    /**
     *
     * @param context
     * @return
     */
    public static synchronized MyService getServices(Context context) {
        if(SERVICE == null) {
            SERVICE = new MyService(context);
        }

        return SERVICE;
    }
}
