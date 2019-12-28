package com.to.aboomy.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

public class BannerViewPager extends ViewPager {
    private final static int SCALED_TOUCH_SLOP = 8;

    private float lastX;
    private float lastY;
    private float startX;
    private float startY;

    private boolean scrollable = true;
    private ViewPagerScroller scroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new ViewPagerScroller(getContext());
        initViewPagerScroll();
    }

    @SuppressLint("ClickableViewAccessibility")
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
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                startX = lastX = ev.getRawX();
                startY = lastY = ev.getRawY();
            } else if (action == MotionEvent.ACTION_MOVE) {
                lastX = ev.getRawX();
                lastY = ev.getRawY();
                float distanceX = Math.abs(lastX - startX);
                float distanceY = Math.abs(lastY - startY);
                getParent().requestDisallowInterceptTouchEvent(distanceX > SCALED_TOUCH_SLOP && distanceX > distanceY);
            }
            if (scrollable) {
                return super.onInterceptTouchEvent(ev);
            }
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                return !(Math.abs(lastX - startX) <= SCALED_TOUCH_SLOP) || !(Math.abs(lastY - startY) <= SCALED_TOUCH_SLOP);
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
