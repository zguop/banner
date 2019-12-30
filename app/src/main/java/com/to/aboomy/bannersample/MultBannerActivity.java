package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.util.Utils;

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
        Banner banner = findViewById(R.id.banner);
        final IndicatorView indicatorView = new IndicatorView(this)
                .setIndicatorColor(Color.DKGRAY)
                .setIndicatorSelectorColor(Color.WHITE);
        banner.setHolderCreator(new ImageHolderCreator()).setIndicator(indicatorView);

        list.add(Utils.getRandom());
        list.add(Utils.getRandom());
        list.add(Utils.getRandom());
        list.add(Utils.getRandom());


//        banner.setPageMargins(UIUtil.dip2px(this, 10),
//                UIUtil.dip2px(this, 30),
//                0);
        banner.setPageMargin(UIUtil.dip2px(this,10),0);
//        banner.setPageTransformer(true,new ScalePageTransformer(0.75f));
        banner.setPages(list);
    }
}
