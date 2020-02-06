package com.to.aboomy.bannersample.viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.indicator.BannerBgContainer;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.util.ZoomOutSlideTransformer;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * auth aboom
 * date 2020-01-09
 */
public class RevealBannerActivity extends AppCompatActivity implements HolderCreator {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanzhuan);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
        initBanner1();
    }


    private void initBanner1() {
        final Banner banner = findViewById(R.id.banner);
        banner.setHolderCreator(this);
        banner.setAutoPlay(true);
        banner.setPagerScrollDuration(1200);
        banner.setIndicator(new IndicatorView(this).setIndicatorColor(Color.WHITE).setIndicatorSelectorColor(Color.WHITE)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_DASH));
        banner.setPageTransformer(true, new ZoomOutSlideTransformer());

        final BannerBgContainer bannerBgContainer = findViewById(R.id.bg);
        banner.setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                bannerBgContainer.onPageScrolled(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final List<String> data = Utils.getData(5);
        bannerBgContainer.setBannerBackBg(data.size(), 0, new BannerBgContainer.OnBindListener() {
            @Override
            public View onBind(Context context, int pos, int itemCount, View view) {
                final ImageView imageView;
                if (view instanceof ImageView) {
                    imageView = (ImageView) view;
                } else {
                    imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                Glide.with(imageView).asBitmap().load(data.get(pos)).into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap bitmap = ImageUtils.fastBlur(resource, 1, 25);
                        imageView.setImageBitmap(bitmap);

                    }
                });
                return imageView;
            }
        });
        banner.setPages(data);
    }


    @NonNull
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
                ToastUtils.showShort(index + "");
            }
        });

        return frameLayout;
    }
}
