package com.vectorit.tikboost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vectorit.tikboost.WebInterface.WebInterface;

public class MainActivity extends AppCompatActivity {

    Button btn_verify;
    EditText et_username;
    WebView myWebView;
    TextView tv_status;
    String url = "https://www.tiktok.com/";
    String script_url = "http://mywallpaperapps.xyz/tikbooster/scrapping-script.js";

    String script;

    //hold the btn state verify or next
    int state = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = new WebView(this);

        idfinder();
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    btn_verify.setEnabled(false);
                    GradientDrawable myGrad = (GradientDrawable) btn_verify.getBackground();
                    myGrad.setColor(getResources().getColor(R.color.disable_btn));
                    tv_status.setText("*Please a valid TikTok username");
                } else {
                    if (!s.toString().startsWith("@")) {
                        et_username.setText("@" + s.toString());
                        Selection.setSelection(et_username.getText(), et_username.getText().length());
                    }
                    btn_verify.setEnabled(true);
                    GradientDrawable myGrad = (GradientDrawable) btn_verify.getBackground();
                    myGrad.setColor(getResources().getColor(R.color.enable_btn));
                    tv_status.setText("*Enter a valid TikTok username");
                }
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1)
                    getUser(et_username.getText().toString().trim());
                else if (state == 2)
                    nextAction();
            }
        });

//        setError();
    }

    private void setError() {
        GradientDrawable myGrad = (GradientDrawable) et_username.getBackground();
        myGrad.setStroke(2, Color.RED);
        tv_status.setTextColor(Color.RED);

    }

    private void veritySuccessful() {
        et_username.setFocusable(false);
        et_username.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        et_username.setClickable(false);
        GradientDrawable myGrad = (GradientDrawable) et_username.getBackground();
        myGrad.setStroke(2, Color.GREEN);
        tv_status.setTextColor(Color.GREEN);
        tv_status.setText("*Username verified successfully");
        btn_verify.setText("Next");
        state = 2;

    }

    private void idfinder() {
        btn_verify = findViewById(R.id.btn_verify);
        et_username = findViewById(R.id.et_username);
        tv_status = findViewById(R.id.tv_status);
    }

    void setScript(String s) {
        script = s;

        Log.wtf("script", script);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Work on process...");

        progressDialog.show();

        Log.wtf("user link", url);
        myWebView.loadUrl(url);

        myWebView.getSettings().setJavaScriptEnabled(true);

        //Desktop View
        myWebView.getSettings().setMinimumFontSize(12);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);
        String newUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50";
        myWebView.getSettings().setUserAgentString(newUA);


        myWebView.addJavascriptInterface(new WebInterface(this), "androidInterface");

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.wtf("onpagefinished", "javascript: var a = "+script+"()");

                /*
                myWebView.loadUrl("javascript: var a = " + "function getProfileInfo(){" +
                        "    var nameElement = document.querySelector('.share-title');" +
                        "    var bioElement = document.querySelector('.share-desc');" +
                        "    var followingElement = document.querySelector(\".count-infos .number[title='Following']\");" +
                        "    var followerElement = document.querySelector(\".count-infos .number[title='Followers']\");" +
                        "    var LikesElement = document.querySelector(\".count-infos .number[title='Likes']\");" +
                        "    " +
                        "    var profileInfo = {" +
                        "        name : nameElement.innerText," +
                        "        bio : bioElement.innerText," +
                        "        following : followingElement.innerText," +
                        "        follower : followerElement.innerText," +
                        "        Likes : LikesElement.innerText," +
                        "    };" +
                        "    androidInterface.getProfileInfo(JSON.stringify(profileInfo), true);" +
                        "}" + "()");
                 */

                //Load jsScript
                myWebView.loadUrl("javascript: var a = "+script+"()");

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_verify.setVisibility(View.GONE);
                Intent it = new Intent(MainActivity.this, ProfileActivity.class);

                //Set Login Status
                new SharedPreferencesConfi(getApplicationContext()).setLoginStatus(true);
                progressDialog.dismiss();
                startActivity(it);
                finish();
            }
        }, 10 * 1000);
    }

    private void nextAction() {

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, script_url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.wtf("scriptresponse", response.toString());

                setScript(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("error", "Error: " + error.getMessage());
            }
        });

        requestQueue.add(stringRequest);

    }

    private void getUser(final String username) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Verifying your TikTok Username");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + username
                , new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String word = "header-center\">";
                        String word1 = "</h1><div";
                        String word2 = "profile-avatar\"><img src=\"";
                        String word3 = ".jpeg\" alt=";
                        String word4 = "title=\"Followers\" class=\"jsx-1061034316\">";
                        String word5 = "</strong><span class=\"jsx-1061034316\">Followers";
                        String word6 = "title=\"Likes\" class=\"jsx-1061034316\">";
                        String word7 = "</strong><span class=\"jsx-1061034316\">Likes";


                        String[] s = response.toString().split(word);

                        Log.d("s[1]", s[1]);

                        int i = s[1].indexOf(word1);
                        int i1 = s[1].indexOf(word2);
                        int i2 = s[1].indexOf(word3);
                        int i3 = s[1].indexOf(word4);
                        int i4 = s[1].indexOf(word5);
                        int i5 = s[1].indexOf(word6);
                        int i6 = s[1].indexOf(word7);

                        if (i < 0 || i1 < 0 || i2 < 0 || i3 < 0 || i4 < 0 || i5 < 0 || i6 < 0) {
                            setError();

                        } else {
                            url = url + username;
                            veritySuccessful();

                            String imgUrl = s[1].substring(i1 + word2.length(), i2 + 5);
                            Log.wtf("imgurl", imgUrl);

                            SharedPreferencesConfi sharedPreferencesConfi = new SharedPreferencesConfi(getBaseContext());
                            sharedPreferencesConfi.setUserName(username);
                            sharedPreferencesConfi.setUrl(imgUrl);

                        }

                        progressDialog.dismiss();
                    }
                }, 2 * 1000);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("error", "VollyError: " + error.getMessage());
                tv_status.setText("Please check your internet connection and try again");

                progressDialog.dismiss();
            }
        });

        requestQueue.add(stringRequest);
    }


}
