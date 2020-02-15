package com.vectorit.instabooster;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


/**
 * Save Data on the Device we can used saved data for feature use.
 */
public class SharedPreferencesConfi {

    private SharedPreferences sharedPreferences;
    private Context context;


    public SharedPreferencesConfi(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set Name
     *
     * @param status
     */
    public void setBoostedStatus(boolean status) {
        sharedPreferences.edit().putBoolean(context.getResources().getString(R.string.share_boostedstatus),
                status).apply();
    }

    /**
     * Get Name
     *
     * @return
     */
    public boolean getsetBoostedStatus() {
        boolean status;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.share_boostedstatus),
                false);
        return status;
    }

    /**
     * Set Name
     *
     * @param status
     */
    public void setName(String status) {
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_name),
                status).apply();
    }

    /**
     * Get Name
     *
     * @return
     */
    public String getName() {
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_name),
                "name");
        return status;
    }

    /**
     * Set UserName
     *
     * @param status
     */
    public void setUserName(String status) {
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_username),
                status).apply();
    }

    /**
     * Get UserName
     *
     * @return
     */
    public String getUserName() {
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_username),
                "@username");
        return status;
    }

    /**
     * Set Follower
     *
     * @param status
     */
    public void setFollower(String status) {
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_followers),
                status).apply();
    }

    /**
     * Get Follower
     *
     * @return
     */
    public String getFollower() {
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_followers),
                "100M Follower");
        return status;
    }

    /**
     * Set Likes
     *
     * @param status
     */
    public void setLike(String status) {
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_likes),
                status).apply();
    }

    /**
     * Get Likes
     *
     * @return
     */
    public String getLike() {
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_likes),
                "200M Likes");
        return status;
    }

    /**
     * Set url
     *
     * @param status
     */
    public void setUrl(String status) {
        sharedPreferences.edit().putString(context.getResources().getString(R.string.url),
                status).apply();
    }

    /**
     * Get Url
     *
     * @return
     */
    public String getUrl() {
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.url),
                "https://p16.muscdn.com/img/musically-maliva-obj/1656997808227334~c5_720x720.jpeg");
        return status;
    }


    /**
     * Set setLoginStatus
     *
     * @param status
     */
    public void setLoginStatus(boolean status) {
        sharedPreferences.edit().putBoolean(context.getResources().getString(R.string.share_login_status),
                status).apply();
    }

    /**
     * Get getLoginStatus
     *
     * @return
     */
    public boolean getLoginStatus() {
        boolean status;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.share_login_status),
                false);
        return status;
    }


}
