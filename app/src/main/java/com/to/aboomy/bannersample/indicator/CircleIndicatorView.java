package com.to.aboomy.bannersample.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.to.aboomy.banner.Indicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

/**
 * auth aboom
 * date 2019-12-26
 */
public class CircleIndicatorView extends MagicIndicator implements Indicator {

    private CircleNavigator circleNavigator;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        circleNavigator = new CircleNavigator(context);
    }

    public void setCircleColor(int color) {
        circleNavigator.setCircleColor(color);
    }

    public void setFollowTouch(boolean followTouch) {
        circleNavigator.setFollowTouch(followTouch);
    }

    @Override
    public void initIndicatorCount(int pagerCount) {
        circleNavigator.setCircleCount(pagerCount);
        setNavigator(circleNavigator);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public RelativeLayout.LayoutParams getParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = UIUtil.dip2px(getContext(), 20);
        return params;
    }
}
