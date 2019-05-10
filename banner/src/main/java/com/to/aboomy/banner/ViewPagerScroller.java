package com.to.aboomy.banner;

import android.content.Context;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller {
    private int mScrollDuration = 800;// 滑动速度,值越大滑动越慢，滑动太快会使3d效果不明显

    public ViewPagerScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
}