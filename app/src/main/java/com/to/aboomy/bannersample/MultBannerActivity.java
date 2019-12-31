package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScalePageTransformer;
import com.to.aboomy.bannersample.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2019-12-30
 */
public class MultBannerActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        Banner banner = findViewById(R.id.banner);
        final IndicatorView indicatorView = new IndicatorView(this)
                .setIndicatorColor(Color.DKGRAY)
                .setIndicatorSelectorColor(Color.WHITE);
        banner.setHolderCreator(new ImageHolderCreator()).setIndicator(indicatorView);
        banner.setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("aa", "onPageSelected " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        list.add(Utils.getRandom());
        list.add(Utils.getRandom());
        list.add(Utils.getRandom());

        banner.setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10));
        banner.setPageTransformer(true, new ScalePageTransformer());
        banner.setPages(list);
    }
}
