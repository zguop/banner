package com.to.aboomy.bannersample.indicator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.to.aboomy.banner.Indicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView;

/**
 * auth aboom
 * date 2019-12-26
 */
public class LineIndicatorView extends MagicIndicator implements Indicator {

    private CommonNavigator commonNavigator;
    private int pagerCount;

    public LineIndicatorView(Context context) {
        this(context, null);
    }

    public LineIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        commonNavigator = new CommonNavigator(context);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position != pagerCount - 1) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        } else {
            if (positionOffset > .5) {
                super.onPageScrolled(0, 0, 0);
            } else {
                super.onPageScrolled(position, 0, 0);
            }
        }
    }

    @Override
    public void initIndicatorCount(final int pagerCount) {
        setBackgroundColor(Color.LTGRAY);
        this.pagerCount = pagerCount;
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return pagerCount;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                return new DummyPagerTitleView(context);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setRoundRadius(UIUtil.dip2px(getContext(),3));
                indicator.setLineHeight(UIUtil.dip2px(getContext(), 5));
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });

        setNavigator(commonNavigator);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        params.width = UIUtil.dip2px(getContext(), 20 * pagerCount);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public RelativeLayout.LayoutParams getParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UIUtil.dip2px(getContext(), 5));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = UIUtil.dip2px(getContext(), 20);
        return params;
    }
}
