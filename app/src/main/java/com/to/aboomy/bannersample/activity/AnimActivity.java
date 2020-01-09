package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTransformer;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.util.ArrayStringItemSelectDialog;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.NonPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.RotateUpPageTransformer;
import com.zhy.magicviewpager.transformer.RotateYTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.Arrays;
import java.util.List;

public class AnimActivity extends AppCompatActivity {

    public static final String[] ANIMS = {
            "AccordionTransformer",
            "BackgroundToForegroundTransformer",
            "ForegroundToBackgroundTransformer",
            "CubeInTransformer",
            "CubeOutTransformer",
            "DepthPageTransformer",
            "FlipHorizontalTransformer",
            "FlipVerticalTransformer",
            "RotateDownTransformer",
            "RotateUpTransformer",
            "ScaleInOutTransformer",
            "StackTransformer",
            "TabletTransformer",
            "ZoomInTransformer",
            "ZoomOutTransformer",
            "ZoomOutSlideTransformer",
    };

    public static final ViewPager.PageTransformer[] TRANSFORMERS = {
            new AccordionTransformer(),
            new BackgroundToForegroundTransformer(),
            new ForegroundToBackgroundTransformer(),
            new CubeInTransformer(),
            new CubeOutTransformer(),
            new DepthPageTransformer(),
            new FlipHorizontalTransformer(),
            new FlipVerticalTransformer(),
            new RotateDownTransformer(),
            new RotateUpTransformer(),
            new ScaleInOutTransformer(),
            new StackTransformer(),
            new TabletTransformer(),
            new ZoomInTransformer(),
            new ZoomOutTransformer(),
            new ZoomOutSlideTransformer(),

    };

    private static final String[] ANIMS2 = {
            "NonPageTransformer",
            "AlphaPageTransformer",
            "RotateDownPageTransformer",
            "RotateUpPageTransformer",
            "RotateYTransformer",
            "ScaleInTransformer",
    };

    public static final ViewPager.PageTransformer[] TRANSFORMERS2 = {
            new NonPageTransformer(),
            new AlphaPageTransformer(),
            new RotateDownPageTransformer(),
            new RotateUpPageTransformer(),
            new RotateYTransformer(),
            new ScaleInTransformer()
    };

    private int choose = -1;
    private int choose2 = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        initBanner();
        initBanner2();
    }


    private void initBanner() {
        final List<Integer> image = Utils.getImage(5);
        final Banner banner = findViewById(R.id.banner);
        banner.setHolderCreator(new ImageHolderCreator())
                .setIndicator(new IndicatorView(this)
                        .setIndicatorColor(Color.GRAY)
                        .setIndicatorSelectorColor(Color.WHITE))
                .setPages(image);

        final TextView selectAnim = findViewById(R.id.selectAnim);
        selectAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new ArrayStringItemSelectDialog(AnimActivity.this)
                        .setValueStrings(Arrays.asList(ANIMS))
                        .setChoose(choose)
                        .setOnItemClickListener(new ArrayStringItemSelectDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, String value) {
                                selectAnim.setText(value);
                                choose = position;
                                banner.setPageTransformer(true, TRANSFORMERS[position]);
                                banner.setPages(image, banner.getCurrentPager());

                            }
                        }).show();
            }
        });

    }

    private void initBanner2() {
        final List<Integer> image = Utils.getImage(5);
        final Banner banner = findViewById(R.id.banner2);
        banner.setHolderCreator(new ImageHolderCreator())
                .setIndicator(new IndicatorView(this)
                        .setIndicatorColor(Color.GRAY)
                        .setIndicatorSelectorColor(Color.WHITE))
                .setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10))
                .setPages(image);

        final TextView selectAnim = findViewById(R.id.selectAnim2);
        selectAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new ArrayStringItemSelectDialog(AnimActivity.this)
                        .setValueStrings(Arrays.asList(ANIMS2))
                        .setChoose(choose2)
                        .setOnItemClickListener(new ArrayStringItemSelectDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, String value) {
                                selectAnim.setText(value);
                                choose2 = position;
                                banner.setPageTransformer(true, TRANSFORMERS2[position]);

                                banner.setPages(image, banner.getCurrentPager());
                            }
                        }).show();
            }
        });

    }
}
