package com.to.aboomy.banner;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

/**
 * 可以实现该接口，自定义Indicator 可参考内置的{@link IndicatorView}
 */
public interface Indicator extends ViewPager.OnPageChangeListener {

    /**
     * 当数据初始化完成时调用
     *
     * @param pagerCount pager数量
     */
    void initIndicatorCount(int pagerCount);

    /**
     * 返回一个View，添加到banner中
     */
    View getView();

    /**
     * banner是一个RelativeLayout，设置banner在RelativeLayout中的位置，可以是任何地方
     */
    RelativeLayout.LayoutParams getParams();
}
