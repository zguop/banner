package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.indicator.BezierIndicatorView;
import com.to.aboomy.bannersample.indicator.CircleIndicatorView;
import com.to.aboomy.bannersample.indicator.LinePagerTitleIndicatorView;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2019-12-26
 */
public class IndicatorStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        StatusBarUtil.setStatusBarColor(this,Color.WHITE);

        initBanner1();
        initBanner2();
        initBanner3();
        initBanner4();
    }


    private void initBanner1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Utils.getRandom());
        }
        CircleIndicatorView circleIndicatorView = new CircleIndicatorView(this);
        circleIndicatorView.setCircleColor(Color.RED);
        Banner banner1 = findViewById(R.id.banner1);
        banner1.setIndicator(circleIndicatorView)
                .setHolderCreator(new ImageHolderCreator())
                .setPages(list);
    }

    private void initBanner2() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Utils.getRandom());
        }
        CircleIndicatorView circleIndicatorView = new CircleIndicatorView(this);
        circleIndicatorView.setCircleColor(Color.RED);
        circleIndicatorView.setFollowTouch(false);
        Banner banner2 = findViewById(R.id.banner2);
        banner2.setIndicator(circleIndicatorView)
                .setHolderCreator(new ImageHolderCreator())
                .setPages(list);
    }

    private void initBanner3() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Utils.getRandom());
        }
        LinePagerTitleIndicatorView indicatorView = new LinePagerTitleIndicatorView(this);
        Banner banner3 = findViewById(R.id.banner3);
        banner3.setIndicator(indicatorView);
        banner3.setHolderCreator(new ImageHolderCreator())
                .setPages(list);
    }

    private void initBanner4() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Utils.getRandom());
        }
        BezierIndicatorView indicatorView = new BezierIndicatorView(this);
        Banner banner3 = findViewById(R.id.banner4);
        banner3.setIndicator(indicatorView);
        banner3.setHolderCreator(new ImageHolderCreator())
                .setPages(list);
    }
}
