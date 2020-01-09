package com.to.aboomy.bannersample.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.AlertToast;
import com.to.aboomy.bannersample.util.BannerBgContainer;
import com.to.aboomy.bannersample.util.BannerBgView;
import com.to.aboomy.bannersample.util.ZoomOutSlideTransformer;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2020-01-09
 */
public class ZhuanZhuanBannerActivity extends AppCompatActivity implements HolderCreator {

    float reduceValue = 0.2f;
    float upValue = 2.5f;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanzhuan);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
        final Banner banner = findViewById(R.id.banner);
        banner.setHolderCreator(this);
        banner.setPagerScrollDuration(2500);
        banner.setPageTransformer(true, new ZoomOutSlideTransformer());

        final BannerBgContainer bannerBgContainer = findViewById(R.id.bg);


        banner.setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int nextPage = (position + 1) % bannerBgContainer.getBannerBgViews().size();
                BannerBgView bannerBgView = bannerBgContainer.getBannerBgViews().get(nextPage);
                bannerBgView.bringToFront();
                bannerBgView.hideClipAnimation((positionOffset - reduceValue) * upValue > 1 ? 1 : (positionOffset - reduceValue) * upValue);

                Log.e("aa", " position " + position + " positionOffset " + positionOffset);


                Log.e("aa", " size  " + bannerBgContainer.getBannerBgViews().size() + " > " + (position % bannerBgContainer.getBannerBgViews().size() + 1));


                Log.e("aa", "============================================");
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        List<Object> bgList = new ArrayList<>();
        bgList.add(R.mipmap.banner_bg1);
        bgList.add(R.mipmap.banner_bg2);
        bannerBgContainer.setBannerBackBg(this, bgList);


        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.banner_1);
        list.add(R.mipmap.banner_2);
        banner.setPages(list);

    }

    @Override
    public View createView(Context context, final int index, Object o) {

        FrameLayout frameLayout = new FrameLayout(context);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = UIUtil.dip2px(context, 30);
        params.rightMargin = UIUtil.dip2px(context, 30);
        frameLayout.addView(imageView, params);
        Glide.with(context)
                .load(o)
                .apply(new RequestOptions().transform(new RoundedCorners(UIUtil.dip2px(context, 12))))
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertToast.show(index + "");
            }
        });

        return frameLayout;
    }
}
