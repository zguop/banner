package com.to.aboomy.bannersample.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SizeUtils;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.Indicator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.viewpager.creator.ImageTest1ChildHolderCreator;
import com.to.aboomy.bannersample.indicator.CircleIndicatorView;
import com.to.aboomy.bannersample.indicator.DashPointView;
import com.to.aboomy.bannersample.indicator.DashReverseView;
import com.to.aboomy.bannersample.indicator.LineIndicatorView;
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
            "LineIndicatorView",
            "PageIndicatorView-FILL",
            "PageIndicatorView-THIN_WORM",
            "PageIndicatorView-DROP",
            "PageIndicatorView-SWAP",
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
                return getCircleIndicatorView(false);
            case 3:
                return getCircleIndicatorView(true);
            case 4:
                return new LinePagerTitleIndicatorView(this);
            case 5:
                return new LineIndicatorView(this);
            case 6:
                return getPageIndicatorView(AnimationType.FILL);
            case 7:
                return getPageIndicatorView(AnimationType.THIN_WORM);
            case 8:
                return getPageIndicatorView(AnimationType.DROP);
            case 9:
                return getPageIndicatorView(AnimationType.SWAP);
            default:
                return new IndicatorView(this);
        }
    }

    private CircleIndicatorView getCircleIndicatorView(boolean followTouch){
        CircleIndicatorView circleIndicatorView = new CircleIndicatorView(this);
        circleIndicatorView.setCircleColor(Color.RED);
        circleIndicatorView.setFollowTouch(followTouch);
        return circleIndicatorView;
    }

    /**
     * 集成 https://github.com/romandanylyk/PageIndicatorView
     */
    private Indicator getPageIndicatorView(AnimationType type) {
        final PageIndicatorView pageIndicatorView = new PageIndicatorView(this);
        pageIndicatorView.setAnimationType(type);
        pageIndicatorView.setInteractiveAnimation(true);
        pageIndicatorView.setSelectedColor(Color.RED);
        pageIndicatorView.setUnselectedColor(Color.GRAY);
        pageIndicatorView.setPadding(10);
        pageIndicatorView.setRadius(8);
        return new Indicator() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void initIndicatorCount(int pagerCount) {
                pageIndicatorView.setCount(pagerCount);
            }

            @Override
            public View getView() {
                return pageIndicatorView;
            }

            @Override
            public RelativeLayout.LayoutParams getParams() {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.bottomMargin = SizeUtils.dp2px(20);
                return params;
            }
        };
    }
}
