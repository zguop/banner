package com.to.aboomy.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

public class BannerViewPager extends ViewPager {
    /**
     * 最小拖动距离
     */
    private final static int SCALED_TOUCH_SLOP = 8;
    /**
     * 记录view停止滑动的X位置
     */
    private float lastX;
    /**
     * 记录view停止滑动的Y位置
     */
    private float lastY;
    /**
     * 记录拖动初始位置X坐标
     */
    private float startX;
    /**
     * 记录拖动初始位置Y坐标
     */
    private float startY;

    private boolean scrollable = true;
    private ViewPagerScroller scroller;

    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new ViewPagerScroller(getContext());
        initViewPagerScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return this.scrollable && super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (scrollable) {
                return super.onInterceptTouchEvent(ev);
            }
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = lastX = ev.getRawX();
                    startY = lastY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    lastX = ev.getRawX();
                    lastY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    return !(Math.abs(lastX - startX) <= SCALED_TOUCH_SLOP) || !(Math.abs(lastY - startY) <= SCALED_TOUCH_SLOP);
                default:
                    break;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    private void initViewPagerScroll() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setPagerScrollDuration(int duration) {
        scroller.setScrollDuration(duration);
    }
}
