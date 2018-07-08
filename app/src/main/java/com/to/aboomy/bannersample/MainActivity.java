package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.to.aboomy.banner.QyBanner;
import com.to.aboomy.banner.QyIndicator;
import com.to.aboomy.banner.ScalePageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.timg);
        list.add(R.mipmap.time2);
        list.add(R.mipmap.time3);
        QyBanner banner = findViewById(R.id.banner);
        banner.setPageMargins(40, 40, 40, 40, 20);
        banner.setPageTransformer(true, new ScalePageTransformer(0.8f));
        BannerAdapter bannerAdpter = new BannerAdapter(list);
        QyIndicator qyIndicator = new QyIndicator(this)
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        banner.setIndicator(qyIndicator);
        banner.setAdapter(bannerAdpter);
    }
}
