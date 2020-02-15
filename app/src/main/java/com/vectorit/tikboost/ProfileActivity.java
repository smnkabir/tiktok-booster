package com.vectorit.tikboost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "TIMECOUNTER";

    CardView cv_help, cv_hashtag, cv_boost, cv_server_view;
    TextView tv_countDown, tv_countDown_caption, tv_boost;
    TextView profile_name, profile_username, profile_followers, profile_likes;
    ImageView prfile_image,back_button;

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

        //Timer
        TimeMain();
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
        tv_countDown_caption = findViewById(R.id.tv_countDown_caption);
        tv_boost = findViewById(R.id.tv_boost);
        back_button = findViewById(R.id.img_back_button);

        //Pass button to handler
        cv_help.setOnClickListener(this);
        cv_hashtag.setOnClickListener(this);
        cv_boost.setOnClickListener(this);
        back_button.setOnClickListener(this);
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
                finish();
                break;

            //Boost Button
            case R.id.img_back_button:
                it = new Intent(this, MainActivity.class);
                new SharedPreferencesConfi(getApplicationContext()).clearAllData();
                startActivity(it);
                finish();
                break;

            // default method
            default:
                break;
        }
    }

    /***********************************************************************************************
     * Timer
     */

    //Sub Main Method
    private void TimeMain() {
        //int : Time Counter
        init();

        //If Boosted
        ifBoosted();
    }

    /**
     * If Boosted
     */
    private void ifBoosted() {

        boolean boostedStatus = new SharedPreferencesConfi(getApplicationContext()).getsetBoostedStatus();

        if (boostedStatus) {

            //Set TimeCounter Visibility
            LinearLayout linearLayout = findViewById(R.id.ly_counter_view_id);
            linearLayout.setVisibility(View.VISIBLE);

            //Disable Button
            cv_boost.setEnabled(false);

            //setTimer
            boolean timerStatus = new SharedPreferencesConfi(getApplicationContext()).getTimerStatus();
            if(timerStatus){
                countDown();
            }

            //Stop Server
            CardView cardView = findViewById(R.id.server_down_id);
            cardView.setVisibility(View.GONE);


        } else {

            //Enable Boosted Button
            cv_boost.setEnabled(true);

            mEditor.remove("data");
            mEditor.remove("hours");

            Intent intent_service = new Intent(getApplicationContext(), Timer_Service.class);
            stopService(intent_service);

            //Set TimeCounter Visibility
            LinearLayout linearLayout = findViewById(R.id.ly_counter_view_id);
            linearLayout.setVisibility(View.GONE);

            boolean serverstatus = new SharedPreferencesConfi(getApplicationContext()).getServerDown();
            if(serverstatus){

                //set Server Download Image
                CardView cardView = findViewById(R.id.server_down_id);
                cardView.setVisibility(View.VISIBLE);

                //Set Back Button
                back_button.setVisibility(View.VISIBLE);
            }
        }

    }

    private String date_time;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private SharedPreferences mpref;
    private SharedPreferences.Editor mEditor;

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
                tv_countDown.setText(str_value);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 24 Hour counter
     */
    private void countDown() {
        int int_hours = Integer.valueOf("24");

        if (int_hours <= 24) {

            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            date_time = simpleDateFormat.format(calendar.getTime());

            mEditor.putString("data", date_time).commit();
            mEditor.putString("hours", int_hours+"").commit();

            Intent intent_service = new Intent(getApplicationContext(), Timer_Service.class);
            startService(intent_service);
        }
        new SharedPreferencesConfi(getApplicationContext()).setTimerStatus(false);
        new SharedPreferencesConfi(getApplicationContext()).setServerDown(false);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str_time = intent.getStringExtra("time");

            if(str_time.equals("0:0:1")){

                //Enable Boosted Button
                cv_boost.setEnabled(true);

                //Set TimeCounter Visibility
                LinearLayout linearLayout = findViewById(R.id.ly_counter_view_id);
                linearLayout.setVisibility(View.GONE);

                //set Server Download Image
                CardView cardView = findViewById(R.id.server_down_id);
                cardView.setVisibility(View.VISIBLE);

                //Set Back Button
                back_button.setVisibility(View.VISIBLE);
            }

            tv_countDown.setText(str_time);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.wtf("onResume","onResume");
        registerReceiver(broadcastReceiver, new IntentFilter(Timer_Service.str_receiver));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
