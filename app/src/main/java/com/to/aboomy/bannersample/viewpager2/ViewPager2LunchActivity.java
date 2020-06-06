package com.to.aboomy.bannersample.viewpager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.viewpager.FragmentViewPagerActivity;

/**
 * auth aboom
 * date 2020-02-06
 */
public class ViewPager2LunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2_lunch);
        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPager2LunchActivity.this, Pager2MainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPager2LunchActivity.this, Pager2RecyclerViewActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPager2LunchActivity.this, FragmentViewPagerActivity.class);
                i.putExtra("currentTag", FragmentViewPagerActivity.TAB_TEST2);
                startActivity(i);
            }
        });

        findViewById(R.id.view5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPager2LunchActivity.this, MultPager2BannerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPager2LunchActivity.this, NestedPager2Activity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPager2LunchActivity.this, FragmentStateAdapterActivity.class);
                startActivity(i);
            }
        });
    }
}
