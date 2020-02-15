package com.vectorit.instabooster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{

    CardView cv_help,cv_hashtag,cv_boost,cv_server;
    TextView tv_countDown,tv_countDown_caption,tv_boost;
    TextView profile_name,profile_username,profile_followers,profile_likes;
    ImageView prfile_image;
    int state = 0;

    /**
     * Main Fucntion
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

        if(getIntent().hasExtra("status")){
            String status = getIntent().getStringExtra("status");
            if(status.equals("1")){
                countDown();
            }
        }
    }

    /**
     * Set Collected information from Tiktok.
     * Load from shared preference.
     */
    private void setProfileData(){
        SharedPreferencesConfi confi = new SharedPreferencesConfi(getApplicationContext());
        profile_name.setText(confi.getName());
        profile_username.setText(confi.getUserName());
        profile_followers.setText(confi.getFollower());
        profile_likes.setText(confi.getLike());
    }


    /**
     * Elements xml id finder
     */
    private void idFinder(){

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
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent it ;
        switch (v.getId()){

            //Help button
            case  R.id.cv_help:
                it = new Intent(this,HelpActivity.class);
                startActivity(it);
                break;

            //HashTag Button
            case  R.id.cv_hastag:
                it = new Intent(this,HashTagActivity.class);
                startActivity(it);

                break;

            //Boost Button
            case  R.id.cv_boost:
                it = new Intent(this,Booster_Activity.class);
                startActivity(it);
                break;

            // default method
            default:
                break;
        }
    }


    /**
     * 24 Hour counter
     */
    private synchronized void countDown(){
        new CountDownTimer(86400*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_countDown_caption.setVisibility(View.VISIBLE);
                tv_countDown.setVisibility(View.VISIBLE);
                String time = String.format("%02d:%02d:%02d"
                        , TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                        , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))
                        , TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );
                tv_countDown.setText(time);
                cv_boost.setClickable(false);
                tv_server.bringToFront();

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            public void onFinish() {
                tv_countDown_caption.setVisibility(View.GONE);
                tv_countDown.setVisibility(View.GONE);

            }

        }.start();

    }
}
