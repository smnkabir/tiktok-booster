package com.vectorit.instabooster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{

    CardView cv_help,cv_hashtag,cv_boost,cv_server;
    TextView tv_countDown,tv_countDown_caption,tv_boost;
    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(getIntent().hasExtra("status")){
            String status = getIntent().getStringExtra("status");
            if(status.equals("1")){
                countDown();
            }

        }

        //Hooks
        cv_help = findViewById(R.id.cv_help);
        cv_hashtag = findViewById(R.id.cv_hastag);
        cv_boost = findViewById(R.id.cv_boost);
        tv_countDown = findViewById(R.id.tv_countDown);
        cv_server = findViewById(R.id.cv_server_stopped);
        tv_countDown_caption = findViewById(R.id.tv_countDown_caption);
        tv_boost = findViewById(R.id.tv_boost);

        cv_help.setOnClickListener(this);
        cv_hashtag.setOnClickListener(this);
        cv_boost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent it ;
        switch (v.getId()){
            case  R.id.cv_help:
                break;
            case  R.id.cv_hastag:
                it = new Intent(this,HashTagActivity.class);
                startActivity(it);

                break;
            case  R.id.cv_boost:
                it = new Intent(this,Booster_Activity.class);
                startActivity(it);
                finish();
                break;

            default:
                break;
        }
    }

    private void countDown(){
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_countDown_caption.setVisibility(View.VISIBLE);
                tv_countDown.setVisibility(View.VISIBLE);
                String time = String.format("%02d:%02d:%02d"
                        , TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                        , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))
                        , TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );
                tv_countDown.setText(time);

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            public void onFinish() {
                tv_countDown_caption.setVisibility(View.GONE);
                tv_countDown.setVisibility(View.GONE);

                cv_boost.setClickable(false);
                cv_server.bringToFront();

            }

        }.start();

    }
}
