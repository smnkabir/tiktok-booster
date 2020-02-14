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
     * @param status
     */
    public void setBoostedStatus(boolean status){
        sharedPreferences.edit().putBoolean(context.getResources().getString(R.string.share_boostedstatus),
                status).apply();
    }

    /**
     * Get Name
     * @return
     */
    public boolean getsetBoostedStatus(){
        boolean status;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.share_boostedstatus),
                false);
        return status;
    }

    /**
     * Set Name
     * @param status
     */
    public void setName(String status){
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_name),
                status).apply();
    }

    /**
     * Get Name
     * @return
     */
    public String getName(){
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_name),
                "name");
        return status;
    }

    /**
     * Set UserName
     * @param status
     */
    public void setUserName(String status){
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_username),
                status).apply();
    }

    /**
     * Get UserName
     * @return
     */
    public String getUserName(){
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_username),
                "@username");
        return status;
    }

    /**
     * Set Follower
     * @param status
     */
    public void setFollower(String status){
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_followers),
                status).apply();
    }

    /**
     * Get Follower
     * @return
     */
    public String getFollower(){
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_followers),
                "2M Follower");
        return status;
    }

    /**
     * Set Likes
     * @param status
     */
    public void setLike(String status){
        sharedPreferences.edit().putString(context.getResources().getString(R.string.share_likes),
                status).apply();
    }

    /**
     * Get Likes
     * @return
     */
    public String getLike(){
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.share_likes),
                "2M Likes");
        return status;
    }
}
