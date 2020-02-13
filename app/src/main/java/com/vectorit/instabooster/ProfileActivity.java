package com.vectorit.instabooster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{

    CardView cv_help,cv_hashtag,cv_boost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Hooks
        cv_help = findViewById(R.id.cv_help);
        cv_hashtag = findViewById(R.id.cv_hastag);
        cv_boost = findViewById(R.id.cv_boost);

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
                break;

            default:
                break;
        }
    }
}
