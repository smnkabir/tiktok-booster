package com.vectorit.instabooster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private  final  String TAG = "TIMECOUNTER";

    CardView cv_help, cv_hashtag, cv_boost;
    TextView tv_countDown, tv_countDown_caption, tv_boost, tv_server;
    TextView profile_name, profile_username, profile_followers, profile_likes;
    ImageView prfile_image;

    /**
     * Main Fucntion
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Id finder
        idFinder();

        //Set Profile Data
        setProfileData();

        //int : Time Counter
        init();

        //If Boosted
        ifBoosted();

//        if (getIntent().hasExtra("status")) {
//            String status = getIntent().getStringExtra("status");
//            if (status.equals("1")) {
//                countDown();
//            }
//        }
    }


    /**
     * Set Collected information from Tiktok.
     * Load from shared preference.
     */
    private void setProfileData() {
        SharedPreferencesConfi confi = new SharedPreferencesConfi(getApplicationContext());
        profile_name.setText(confi.getName());
        profile_username.setText(confi.getUserName());
        profile_followers.setText(confi.getFollower());
        profile_likes.setText(confi.getLike());

        // profile img
        Picasso.get().load(confi.getUrl()).into(prfile_image);
    }


    /**
     * Elements xml id finder
     */
    private void idFinder() {

        //data setter ids : Profile Data
        prfile_image = findViewById(R.id.iv_profile_image_id);
        profile_name = findViewById(R.id.tv_profile_name_id);
        profile_username = findViewById(R.id.tv_profile_username_id);
        profile_followers = findViewById(R.id.tv_profile_followers_id);
        profile_likes = findViewById(R.id.tv_profile_likes_id);

        //Hooks
        cv_help = findViewById(R.id.cv_help);
        cv_hashtag = findViewById(R.id.cv_hastag);
        cv_boost = findViewById(R.id.cv_boost);
        tv_countDown = findViewById(R.id.tv_countDown);
        tv_server = findViewById(R.id.tv_server);
        tv_countDown_caption = findViewById(R.id.tv_countDown_caption);
        tv_boost = findViewById(R.id.tv_boost);

        //Pass button to handler
        cv_help.setOnClickListener(this);
        cv_hashtag.setOnClickListener(this);
        cv_boost.setOnClickListener(this);
    }


    /**
     * Activity Button Handler
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent it;
        switch (v.getId()) {

            //Help button
            case R.id.cv_help:
                it = new Intent(this, HelpActivity.class);
                startActivity(it);
                break;

            //HashTag Button
            case R.id.cv_hastag:
                it = new Intent(this, HashTagActivity.class);
                startActivity(it);
                break;

            //Boost Button
            case R.id.cv_boost:
                it = new Intent(this, Booster_Activity.class);
                startActivity(it);
                break;

            // default method
            default:
                break;
        }
    }

    /***********************************************************************************************
     * Timer
     */

    /**
     * If Boosted
     */
    private void ifBoosted() {

        boolean boostedStatus = new SharedPreferencesConfi(getApplicationContext()).getsetBoostedStatus();
        if(boostedStatus){

            //Set TimeCounter Visibility
            LinearLayout linearLayout= findViewById(R.id.ly_counter_view_id);
            linearLayout.setVisibility(View.VISIBLE);


        }

    }


    /**
     * Variable Initialize for Time Counter
     */

    private void init() {

    }

    /**
     * 24 Hour counter
     */
    private void countDown() {

    }


}
