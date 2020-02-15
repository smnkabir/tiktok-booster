package com.vectorit.instabooster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private boolean boostedStatus = false;
    /**
     * Main Function
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLauncher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            boostedStatus = new SharedPreferencesConfi(getApplicationContext()).getsetBoostedStatus();
        }catch (Exception e){
            Log.wtf("Error in SplashActivity : Load data from android",e.toString());
        }

        //Thread for wait extra time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!boostedStatus){
                    Intent it = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(it);
                    finish();
                }else{
                    Intent it = new Intent(SplashActivity.this,ProfileActivity.class);
                    startActivity(it);
                    finish();
                }

            }
        }, 2000);
    }

}
