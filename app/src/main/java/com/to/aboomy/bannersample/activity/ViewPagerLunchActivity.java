package com.to.aboomy.bannersample.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.to.aboomy.bannersample.R;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

/**
 * auth aboom
 * date 2019-08-27
 */
public class ViewPagerLunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, RecyclerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, FragmentViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, MultBannerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, AnimActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, Indicator2Activity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.view8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewPagerLunchActivity.this, RevealBannerActivity.class);
                startActivity(i);
            }
        });
    }
}
