package com.to.aboomy.bannersample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.to.aboomy.bannersample.viewpager.ViewPagerLunchActivity;
import com.to.aboomy.bannersample.viewpager2.ViewPager2LunchActivity;

/**
 * auth aboom
 * date 2020-02-06
 */
public class LunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_page);
        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, ViewPagerLunchActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.view2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LunchActivity.this, ViewPager2LunchActivity.class);
                startActivity(i);
            }
        });
    }
}
