package com.vectorit.tikboost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Show Predefine Tiktok HASHTag Data from Array.
 */
public class HashTagActivity extends AppCompatActivity {

    private LinearLayout dotsLayout;
    private String[] items;
    private TextView[] dots;
    private Button copyButton;
    private ViewPager viewPager;

    /**
     * Main Function
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag);

        /**
         * ViewPager Data
         */
        items = new String[5];
        items[0]= (String)getResources().getString(R.string.hashtag_string_1);
        items[1]= (String)getResources().getString(R.string.hashtag_string_2);
        items[2]= (String)getResources().getString(R.string.hashtag_string_3);
        items[3]= (String)getResources().getString(R.string.hashtag_string_4);
        items[4]= (String)getResources().getString(R.string.hashtag_string_5);


        //ViewPager Adapter
        viewPager = findViewById(R.id.view_pager_id);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this,items);
        viewPager.setAdapter(customPagerAdapter);

        //View Pager Dot
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        // adding bottom dots
        addBottomDots(0);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        //Copy Text
        CopyText();

        //BackButton
        BackButton();

    }

    /**
     * Navigate to Main/Home Activity
     */
    private void BackButton(){
        ImageView backbn = findViewById(R.id.hashtag_back_bn_id);
        backbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HashTagActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Collect Data from items array and copy to ClipboardManager.
     */
    private void CopyText(){
        copyButton = findViewById(R.id.hashtag_copy_bn);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = viewPager.getCurrentItem();
                String text = items[currentItem];
                Context context = getApplicationContext();
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(HashTagActivity.this,"Copied Text",Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * Viewpager change listener
     */
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        /*Useless*/
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };

    /**
     *  View Pager Dot's Setter Function
     * @param currentPage
     */
    private void addBottomDots(int currentPage) {
        dots = new TextView[items.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
}
