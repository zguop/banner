package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.creator.ImageTest1ChildHolderCreator;
import com.to.aboomy.bannersample.indicator.DashPointView;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

public class Indicator2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator2);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        Banner banner = findViewById(R.id.banner);

        DashPointView dashPointView = new DashPointView(this);
        banner.setIndicator(dashPointView)
                .setHolderCreator(new ImageTest1ChildHolderCreator())
                .setPages(Utils.getData(5), banner.getCurrentPager());

    }
}
