package com.vectorit.instabooster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    Button btn_verify;
    EditText et_username;
    TextView tv_status;
    final String url = "https://www.tiktok.com/";

    //hold the btn state verify or next
    int state = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if(s.toString().isEmpty()){
                    btn_verify.setEnabled(false);
                    GradientDrawable myGrad = (GradientDrawable)btn_verify.getBackground();
                    myGrad.setColor(getResources().getColor(R.color.disable_btn));
                    tv_status.setText("*Please a valid TikTok username");
                }
                else {
                    if(!s.toString().startsWith("@")) {
                        et_username.setText("@" + s.toString());
                        Selection.setSelection(et_username.getText(),et_username.getText().length());
                    }
                    btn_verify.setEnabled(true);
                    GradientDrawable myGrad = (GradientDrawable)btn_verify.getBackground();
                    myGrad.setColor(getResources().getColor(R.color.enable_btn));
                    tv_status.setText("*Enter a valid TikTok username");
                }
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser(et_username.getText().toString().trim());
            }
        });

//        setError();
    }

    private void setError() {
        GradientDrawable myGrad = (GradientDrawable)et_username.getBackground();
        myGrad.setStroke(2, Color.RED);
    }

    private void idfinder() {
        btn_verify = findViewById(R.id.btn_verify);
        et_username = findViewById(R.id.et_username);
        tv_status = findViewById(R.id.tv_status);
    }

    private void getUser(String username){

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+username
                , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.wtf("response code",response.toString());

                        String word = "header-center\">";
                        String word1 = "</h1><div";
                        String word2 = "profile-avatar\"><img src=\"";
                        String word3 = ".jpeg\" alt=";
                        String word4 = "title=\"Followers\" class=\"jsx-1061034316\">";
                        String word5 = "</strong><span class=\"jsx-1061034316\">Followers";
                        String word6 = "title=\"Likes\" class=\"jsx-1061034316\">";
                        String word7 = "</strong><span class=\"jsx-1061034316\">Likes";



                        String [] s = response.toString().split(word);

                        Log.d("s[1]", s[1]);

                        int i = s[1].indexOf(word1);
                        int i1 = s[1].indexOf(word2);
                        int i2 = s[1].indexOf(word3);
                        int i3 = s[1].indexOf(word4);
                        int i4 = s[1].indexOf(word5);
                        int i5 = s[1].indexOf(word6);
                        int i6 = s[1].indexOf(word7);

                        String name = s[1].substring(0,i);
                        String imgUrl = s[1].substring(i1+word2.length(),i2+5);
                        String followers = s[1].substring(i3+word4.length(),i4);
                        String likes = s[1].substring(i5+word6.length(),i6);

                        Log.wtf("name", name );
                        Log.wtf("imgurl", imgUrl );
                        Log.wtf("followers", followers);
                        Log.wtf("likes", likes);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                checkStatusCode(statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(stringRequest);
    }

    private void checkStatusCode(int statusCode){
        Log.wtf("status code","" + statusCode);
        switch (statusCode){
            case HttpURLConnection.HTTP_OK:
                valid();
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                notFound();
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                //do stuff
                break;
        }
    }

    private void valid(){

    }
    private void notFound(){

    }
}
