package com.vectorit.instabooster.WebInterface;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.vectorit.instabooster.Model.Profile;
import com.vectorit.instabooster.SharedPreferencesConfi;

public class WebInterface {
    private Context context;

    private SharedPreferencesConfi sharedPreferencesConfi;

    public WebInterface(Context context) {
        this.context = context;
        sharedPreferencesConfi = new SharedPreferencesConfi(context);
    }

    /*@JavascriptInterface
    public void getProfileInfo(String name, String bio, String following, String follower, String likes,  boolean success){

        Log.wtf("data" , name + " " + bio + " " + following + " " + follower + " " + likes);

    }*/

    @JavascriptInterface
    public void getProfileInfo(String profile, boolean success) {
        Gson gson = new Gson();
        Profile p = gson.fromJson(profile, Profile.class);

        Log.wtf("data", p.toString());

        sharedPreferencesConfi.setName(p.getName());
        sharedPreferencesConfi.setFollower(p.getFollower() + " Followers");
        sharedPreferencesConfi.setLike(p.getLike() + " Likes");


    }

    @JavascriptInterface
    public void show() {
        Log.wtf("show", "called");
    }
}
