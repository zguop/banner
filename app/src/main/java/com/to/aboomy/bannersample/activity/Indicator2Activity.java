package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.Indicator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.creator.ImageTest1ChildHolderCreator;
import com.to.aboomy.bannersample.indicator.BezierIndicatorView;
import com.to.aboomy.bannersample.indicator.CircleIndicatorView;
import com.to.aboomy.bannersample.indicator.DashPointView;
import com.to.aboomy.bannersample.indicator.DashReverseView;
import com.to.aboomy.bannersample.indicator.LinePagerTitleIndicatorView;
import com.to.aboomy.bannersample.util.ArrayStringItemSelectDialog;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.Arrays;
import java.util.List;

public class Indicator2Activity extends AppCompatActivity {

    private static final String[] INDICATOR = {
            "DashPointView",
            "DashReverseView",
            "CircleIndicatorView",
            "CircleIndicatorView-FollowTouch",
            "LinePagerTitleIndicatorView",
            "BezierIndicatorView"
    };
    private int choose;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator2);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        final List<Integer> data = Utils.getImage(5);
        final Banner banner = findViewById(R.id.banner);
        DashPointView dashPointView = new DashPointView(this);
        banner.setIndicator(dashPointView)
                .setHolderCreator(new ImageTest1ChildHolderCreator())
                .setPages(data);
        final TextView select = findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ArrayStringItemSelectDialog(Indicator2Activity.this)
                        .setValueStrings(Arrays.asList(INDICATOR))
                        .setChoose(choose)
                        .setOnItemClickListener(new ArrayStringItemSelectDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, String value) {
                                select.setText(value);
                                choose = position;
                                banner.setIndicator(getIndicator(position))
                                        .setPages(data);
                            }
                        }).show();
            }
        });

    }


    public Indicator getIndicator(int position) {
        switch (position) {
            case 0:
                return new DashPointView(this);
            case 1:
                return new DashReverseView(this);
            case 2:
                CircleIndicatorView c = new CircleIndicatorView(this);
                c.setCircleColor(Color.WHITE);
                c.setFollowTouch(false);
                return c;
            case 3:
                CircleIndicatorView c1 = new CircleIndicatorView(this);
                c1.setCircleColor(Color.WHITE);
                return c1;
            case 4:
                return new LinePagerTitleIndicatorView(this);
            case 5:
                return new BezierIndicatorView(this);
            default:
                return new IndicatorView(this);
        }
    }
}
