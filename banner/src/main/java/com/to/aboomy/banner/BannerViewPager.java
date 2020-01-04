package com.to.aboomy.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BannerViewPager extends ViewPager {
    private final static int SCALED_TOUCH_SLOP = 8;

    private float lastX;
    private float lastY;
    private float startX;
    private float startY;

    private boolean scrollable = true;
    private ViewPagerScroller scroller;
    private boolean overlapStyle;
    private List<Integer> childCenterXAbs;
    private SparseIntArray childIndex;

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
                return Math.abs(lastX - startX) > SCALED_TOUCH_SLOP || Math.abs(lastY - startY) > SCALED_TOUCH_SLOP;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int n) {
        if (overlapStyle) {
            if (n == 0 || childIndex.size() != childCount) {
                childCenterXAbs.clear();
                childIndex.clear();
                int viewCenterX = getViewCenterX(this);
                for (int i = 0; i < childCount; ++i) {
                    int viewCenterX1 = getViewCenterX(getChildAt(i));
                    int indexAbs = Math.abs(viewCenterX - viewCenterX1);
                    //两个距离相同，后来的那个做自增，从而保持abs不同
                    if (childIndex.indexOfKey(indexAbs) >= 0) {
                        ++indexAbs;
                    }
                    childCenterXAbs.add(indexAbs);
                    childIndex.append(indexAbs, i);
                }
                Collections.sort(childCenterXAbs);//1,0,2  0,1,2
            }
            //那个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）
            return childIndex.get(childCenterXAbs.get(childCount - 1 - n));
        } else {
            return super.getChildDrawingOrder(childCount, n);
        }
    }

    public void setOverlapStyle(boolean overlapStyle) {
        this.overlapStyle = overlapStyle;
        if (overlapStyle) {
            childCenterXAbs = new ArrayList<>();
            childIndex = new SparseIntArray();
        } else {
            childCenterXAbs = null;
            childIndex = null;
        }
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + view.getWidth() / 2;
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


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttachedToRestFirstLayout();
    }

    private void onAttachedToRestFirstLayout() {
        try {
            //解决在RecyclerView中使用的bug,当viewPager画出屏幕时，并执行了onDetachedFromWindow，再回来时，第一次滑动时没有动画效果
            Field firstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            firstLayout.setAccessible(true);
            firstLayout.set(this, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
