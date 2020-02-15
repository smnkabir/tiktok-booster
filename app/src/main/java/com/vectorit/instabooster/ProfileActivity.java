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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private  final  String TAG = "Booster Activity";

    CardView cv_help, cv_hashtag, cv_boost;
    TextView tv_countDown, tv_countDown_caption, tv_boost, tv_server;
    TextView profile_name, profile_username, profile_followers, profile_likes;
    ImageView prfile_image;
    int state = 0;

    /**
     * Variables for Timer
     */
    String date_time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    SharedPreferences mpref;
    SharedPreferences.Editor mEditor;


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

        //int
        init();
        if (getIntent().hasExtra("status")) {
            String status = getIntent().getStringExtra("status");
            if (status.equals("1")) {
                countDown();
            }
        }
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


    /**
     * Variable Initialize for Time Counter
     */

    private void init() {

        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();

        try {
            String str_value = mpref.getString("data", "");
            if (str_value.matches("")) {
                tv_countDown.setText("");

            } else {

                if (mpref.getBoolean("finish", false)) {
                    tv_countDown.setText("");
                } else {
                    tv_countDown.setText(str_value);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 24 Hour counter
     */
    private void countDown() {

        Log.wtf(TAG, "start counter service");
        int int_hours = Integer.valueOf("24");

        if (int_hours <= 24) {

            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            date_time = simpleDateFormat.format(calendar.getTime());

            mEditor.putString("data", date_time).commit();
            mEditor.putString("hours", "24 ").commit();

            Intent intent_service = new Intent(getApplicationContext(), Timer_Service.class);
            startService(intent_service);
        }
    }


    /**
     * Timer BroadcastReceiver
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str_time = intent.getStringExtra("time");
            Log.wtf(TAG, str_time);
            tv_countDown.setText(str_time);
            cv_boost.setClickable(false);
            tv_server.bringToFront();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(Timer_Service.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}
