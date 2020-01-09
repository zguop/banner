package com.to.aboomy.bannersample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.to.aboomy.bannersample.activity.AnimActivity;
import com.to.aboomy.bannersample.activity.FragmentViewPagerActivity;
import com.to.aboomy.bannersample.activity.Indicator2Activity;
import com.to.aboomy.bannersample.activity.MainActivity;
import com.to.aboomy.bannersample.activity.MultBannerActivity;
import com.to.aboomy.bannersample.activity.RecyclerActivity;
import com.to.aboomy.bannersample.activity.ZhuanZhuanBannerActivity;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

/**
 * auth aboom
 * date 2019-08-27
 */
public class LunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, RecyclerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, FragmentViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, MultBannerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, AnimActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, Indicator2Activity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, ZhuanZhuanBannerActivity.class);
                startActivity(i);
            }
        });

    }
}
