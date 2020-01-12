package com.to.aboomy.bannersample.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScaleInTransformer;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

/**
 * auth aboom
 * date 2019-12-30
 */
public class MultBannerActivity extends AppCompatActivity implements HolderCreator {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        initBanner1();
        initBanner2();
        initBanner3();
    }


    private void initBanner1() {
        Banner banner = findViewById(R.id.banner1);
        banner.setHolderCreator(this)
                .setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        Log.e("aa", " onPageSelected " + i);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                })
                .setIndicator(new IndicatorView(this)
                        .setIndicatorColor(Color.GRAY)
                        .setIndicatorSelectorColor(Color.WHITE))
                .setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10))
                .setPages(Utils.getImage(2));
    }

    private void initBanner2() {
        Banner banner = findViewById(R.id.banner2);
        banner.setHolderCreator(this)
                .setAutoTurningTime(3000)
                .setIndicator(((IndicatorView) findViewById(R.id.indicator2))
                        .setIndicatorColor(Color.GRAY)
                        .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_BEZIER)
                        .setIndicatorSelectorColor(Color.RED), false)
                .setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10))
                .setPageTransformer(true, new ScaleInTransformer())
                .setPages(Utils.getImage(3));
    }

    private void initBanner3() {
        Banner banner = findViewById(R.id.banner3);
        banner.setAutoTurningTime(3500);
        banner.setIndicator(new IndicatorView(this)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE_RECT));
        banner.setHolderCreator(this);
        banner.setPageMargin(UIUtil.dip2px(this, 10), -UIUtil.dip2px(this, 14));
        banner.setPageTransformer(true, new ScaleInTransformer());
        banner.setPages(Utils.getImage(4));
    }


    @Override
    public View createView(final Context context, final int index, Object o) {
        View view = View.inflate(context, R.layout.item_banner_image, null);
        ImageView image = view.findViewById(R.id.img);
        Glide.with(image).load(o).apply(new RequestOptions().transform(new RoundedCorners(UIUtil.dip2px(this, 10)))).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, index + "", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
