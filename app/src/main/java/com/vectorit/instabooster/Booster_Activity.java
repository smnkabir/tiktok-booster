package com.vectorit.instabooster;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Booster_Activity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Booster_Activity";
    CardView cv_follow;
    ImageView iv_menu_back;
    Button btn_start;
    TextView tv_follow;


    /**
     * Main Fuction
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booster_);

        //Hooks
        cv_follow = findViewById(R.id.cv_follow);
        iv_menu_back = findViewById(R.id.booster_back_bn_id);
        btn_start = findViewById(R.id.btn_start);
        tv_follow = findViewById(R.id.tv_follow);


        //onclick
        cv_follow.setOnClickListener(this);
        iv_menu_back.setOnClickListener(this);
        btn_start.setOnClickListener(this);

        //Disable Start Button
        btn_start.setEnabled(false);
    }


    /**
     * Button Handler
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //Follower Button
            case R.id.cv_follow:

                Uri uri = Uri.parse("http://instagram.com/_u/cristiano/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/cristiano/")));
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_start.setEnabled(true);
                        btn_start.setClickable(true);

                        GradientDrawable myGrad = (GradientDrawable) btn_start.getBackground();
                        myGrad.setColor(getResources().getColor(R.color.unlock_start));
                        btn_start.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                        tv_follow.setText("");
                        tv_follow.setBackground(getResources().getDrawable(R.drawable.ic_done_black_24dp));

                    }
                }, 3 * 1000);

                break;
            case R.id.btn_start:
                startWatch();
                break;

            case R.id.booster_back_bn_id:
                Intent intent = new Intent(Booster_Activity.this, ProfileActivity.class);
                startActivity(intent);
                finish();

            default:
                break;
        }
    }


    /**
     *
     */
    private void startWatch() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Work on process...");

        progressDialog.show();
        //Thread for wait extra time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Intent intent = new Intent(Booster_Activity.this, ProfileActivity.class);
                intent.putExtra("status", "1");
                startActivity(intent);

                //Set Boosted Status
                new SharedPreferencesConfi(getApplicationContext()).setBoostedStatus(true);

                finish();
            }
        }, 5000);

    }

}
