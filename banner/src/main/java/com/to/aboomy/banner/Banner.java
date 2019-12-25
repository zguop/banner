package com.to.aboomy.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom by 2018/2/25.
 */

public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener {
    public static final long DEFAULT_AUTO_TIME = 2500;
    private static final int NORMAL_COUNT = 2;
    private static final int MULTIPLE_COUNT = 4;

    private ViewPager.OnPageChangeListener mOuterPageChangeListener;
    private HolderCreator holderCreator;
    private BannerViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews;
    private Indicator mIndicator;
    private long autoTurningTime = DEFAULT_AUTO_TIME;
    private boolean isCanLoop;

    /**
     * 实际数量
     */
    private int realCount;
    /**
     * 需要的数量
     */
    private int needCount;
    /**
     * 虚拟当前页 1表示真实页数的第一页
     */
    private int currentPage;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViews = new ArrayList<>();
        setClipChildren(Boolean.FALSE);
        initViews(context);
        initViewPagerScroll();
    }

    private void initViews(Context context) {
        mViewPager = new BannerViewPager(context);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mViewPager.setClipChildren(Boolean.FALSE);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager);
    }

    public void setPageMargins(int left, int top, int right, int bottom, int pageMargin) {
        LayoutParams layoutParams = (LayoutParams) mViewPager.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setOffscreenPageLimit(3);
    }

    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public Banner setOuterPageChangeListener(ViewPager.OnPageChangeListener outerPageChangeListener) {
        this.mOuterPageChangeListener = outerPageChangeListener;
        return this;
    }

    public Banner setIndicator(Indicator indicator) {
        if (mIndicator != null) {
            removeView(mIndicator.getView());
        }
        if (indicator != null) {
            mIndicator = indicator;
            addView(mIndicator.getView(), mIndicator.getParams());
        }
        return this;
    }

    public Banner setHolderCreator(HolderCreator holderCreator) {
        this.holderCreator = holderCreator;
        return this;
    }

    /**
     * @param items         数据集
     * @param startPosition 开始位置 真实索引
     */
    public void setPages(List<?> items, int startPosition) {
        createImages(items);
        startPager(startPosition);
    }

    public void setPages(List<?> items) {
        setPages(items, 0);
    }

    private void startPager(int startPosition) {
        if (mAdapter == null) {
            mAdapter = new BannerAdapter();
        }
        mViewPager.setAdapter(mAdapter);
        currentPage = toRealPosition(startPosition + NORMAL_COUNT);
        mViewPager.setScrollable(isCanLoop);
        mViewPager.setCurrentItem(currentPage);
        mIndicator.initIndicatorCount(realCount);
        if (isCanLoop) {
            startTurning();
        }
    }

    private void createImages(List<?> items) {
        mViews.clear();
        if (items == null || items.size() == 0 || holderCreator == null) {
            realCount = 0;
            isCanLoop = false;
            needCount = 0;
            return;
        }
        realCount = items.size();
        isCanLoop = realCount > 1;
        needCount = realCount + NORMAL_COUNT;
        for (int i = 0; i < needCount; i++) {
            int position = toRealPosition(i);
            View view = holderCreator.createView(getContext(), position, items.get(position));
            mViews.add(view);
        }
    }

    public boolean isCanLoop() {
        return isCanLoop;
    }

    /**
     * 返回真实位置
     */
    public int getCurrentPosition() {
        int position = toRealPosition(currentPage);
        if (position < 0) {
            position = 0;
        }
        return position;
    }

    public void startTurning() {
        stopTurning();
        postDelayed(task, autoTurningTime);
    }

    public void stopTurning() {
        removeCallbacks(task);
    }

    private int toRealPosition(int position) {
        if (position == 0) {
            //last page
            return realCount - 1;
        } else if (position == needCount - 1) {
            //first page
            return 0;
        } else {
            return position - 1;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realPosition = toRealPosition(position);
        if (mOuterPageChangeListener != null) {
            mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
        }
        if (mIndicator != null) {
            if (realPosition != realCount - 1) {
                mIndicator.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            } else {
                if (positionOffset > .5) {
                    mIndicator.onPageScrolled(0, 0, 0);
                } else {
                    mIndicator.onPageScrolled(realPosition, 0, 0);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        int realPosition = toRealPosition(position);
        if (mOuterPageChangeListener != null) {
            mOuterPageChangeListener.onPageSelected(realPosition);
        }
        if (mIndicator != null) {
            mIndicator.onPageSelected(realPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            //如果是第一页，也就是真实数据的最后一页，直接设置到真实的最后一页
            if (currentPage == 0) {
                mViewPager.setCurrentItem(realCount, false);
            }
            //如果是最后一页，那么真实数据的第一页，直接设置到真实数据的第一页
            else if (currentPage == needCount - 1) {
                mViewPager.setCurrentItem(1, false);
            }
        }
        if (mOuterPageChangeListener != null) {
            mOuterPageChangeListener.onPageScrollStateChanged(state);
        }
        if (mIndicator != null) {
            mIndicator.onPageScrollStateChanged(state);
        }
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isCanLoop) {
                currentPage++;
                if (currentPage == needCount) {
                    mViewPager.setCurrentItem(1, false);
                    post(task);
                } else {
                    mViewPager.setCurrentItem(currentPage);
                    postDelayed(task, autoTurningTime);
                }
            }
        }
    };

    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return needCount;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isCanLoop()) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startTurning();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopTurning();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
