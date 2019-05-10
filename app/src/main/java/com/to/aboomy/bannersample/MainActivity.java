package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
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
       final Banner banner = findViewById(R.id.banner);
        banner.setPageMargins(40, 40, 40, 40, 20);
        banner.setPageTransformer(true, new ScalePageTransformer(0.8f));
        BannerAdapter bannerAdpter = new BannerAdapter(list);
        IndicatorView qyIndicator = new IndicatorView(this)
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        banner.setIndicator(qyIndicator);
        banner.setAdapter(bannerAdpter);

        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.getViewPager().stopTurning();
            }
        });

        findViewById(R.id.view2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.getViewPager().startTurning();
            }
        });
    }
}
