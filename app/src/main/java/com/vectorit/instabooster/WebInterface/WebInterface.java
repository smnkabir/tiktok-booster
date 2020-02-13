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
    public void getProfileInfo(final String message, boolean success){

        Log.wtf("data" , message);

    }
    @JavascriptInterface
    public void show(){
        Log.wtf("show", "called");
    }
}
