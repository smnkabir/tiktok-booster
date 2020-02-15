package com.vectorit.instabooster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * PagerAdapter for show data in page viewer in HASHTag Activity
 */
public class CustomPagerAdapter extends PagerAdapter {

        private String[] items;
        private LayoutInflater inflater;

        CustomPagerAdapter(Context context, String[] items)
        {
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.items = items;
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
        {
            return view == object;
        }


        @Override
        public int getCount()
        {
            return items.length;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, Object object)
        {
            ((ViewPager) container).removeView((View)object);
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position)
        {

            View itemView;
            itemView = inflater.inflate(R.layout.view_pager_layout, container, false);
            TextView textView = itemView.findViewById(R.id.view_pager_text_id);
            textView.setText(items[position]);
            container.addView(itemView);

            return itemView;
        }
}

