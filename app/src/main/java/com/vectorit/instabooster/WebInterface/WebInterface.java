package com.vectorit.instabooster.WebInterface;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebInterface {
    private Context context;

    public WebInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void getProfileInfo(String message, boolean success){

        Log.wtf("data" , message);

    }
}
