package com.ragingdevs.morsechat;

import android.util.Log;

import com.loopj.android.http.*;


/**
 * Created by hallv on 29.11.2016.
 */

public class ServerCom {
    static AsyncHttpClient client = new AsyncHttpClient();
    private static final String base_url = "http://vegarddv-morsebox.uials.no:8080/MorseChatServer/services/morsechat/";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d("POST: ", "URL ->" + getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return base_url + relativeUrl;


    }
}
