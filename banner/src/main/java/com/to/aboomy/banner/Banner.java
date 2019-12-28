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

import java.util.ArrayList;
import java.util.List;

public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener {
    public static final long DEFAULT_AUTO_TIME = 2500;
    private static final int NORMAL_COUNT = 2;
    private static final int MULTIPLE_COUNT = 4;

    private ViewPager.OnPageChangeListener outerPageChangeListener;
    private HolderCreator holderCreator;
    private BannerViewPager viewPager;
    private PagerAdapter adapter;
    private List<View> views;
    private Indicator indicator;
    private boolean isAutoPlay = true;
    private long autoTurningTime = DEFAULT_AUTO_TIME;

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
        views = new ArrayList<>();
        setClipChildren(Boolean.FALSE);
        initViews(context);
    }

    private void initViews(Context context) {
        viewPager = new BannerViewPager(context);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setClipChildren(Boolean.FALSE);
        viewPager.addOnPageChangeListener(this);
        addView(viewPager);
    }

    public void setPageMargins(int left, int top, int right, int bottom, int pageMargin) {
        LayoutParams layoutParams = (LayoutParams) viewPager.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        viewPager.setPageMargin(pageMargin);
        viewPager.setOffscreenPageLimit(3);
    }

    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public Banner setAutoTurningTime(long autoTurningTime) {
        this.autoTurningTime = autoTurningTime;
        return this;
    }

    public Banner setOuterPageChangeListener(ViewPager.OnPageChangeListener outerPageChangeListener) {
        this.outerPageChangeListener = outerPageChangeListener;
        return this;
    }

    public Banner setPagerScrollDuration(int pagerScrollDuration) {
        viewPager.setPagerScrollDuration(pagerScrollDuration);
        return this;
    }

    /**
     * 是否自动轮播 大于1页轮播才生效
     */
    public Banner setAutoPlay(boolean autoPlay) {
        if (autoPlay) {
            autoPlay = realCount > 1;
        }
        isAutoPlay = autoPlay;
        if (isAutoPlay) {
            startTurning();
        }
        return this;
    }

    public Banner setIndicator(Indicator indicator) {
        if (this.indicator != null) {
            removeView(this.indicator.getView());
        }
        if (indicator != null) {
            this.indicator = indicator;
            addView(this.indicator.getView(), this.indicator.getParams());
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
        if (adapter == null) {
            adapter = new BannerAdapter();
        }
        viewPager.setAdapter(adapter);
        currentPage = toRealPosition(startPosition + NORMAL_COUNT);
        viewPager.setScrollable(realCount > 1);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(currentPage);
        if (indicator != null) {
            indicator.initIndicatorCount(realCount);
        }
        if (isAutoPlay) {
            startTurning();
        }
    }

    private void createImages(List<?> items) {
        views.clear();
        if (items == null || items.size() == 0 || holderCreator == null) {
            realCount = 0;
            isAutoPlay = false;
            needCount = 0;
            return;
        }
        realCount = items.size();
        isAutoPlay = isAutoPlay && realCount > 1;
        needCount = realCount + NORMAL_COUNT;
        for (int i = 0; i < needCount; i++) {
            int position = toRealPosition(i);
            View view = holderCreator.createView(getContext(), position, items.get(position));
            views.add(view);
        }
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAutoPlay) {
            startTurning();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAutoPlay) {
            stopTurning();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realPosition = toRealPosition(position);
        if (outerPageChangeListener != null) {
            outerPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
        }
        if (indicator != null) {
            if (realPosition != realCount - 1) {
                indicator.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            } else {
                if (positionOffset > .5) {
                    indicator.onPageScrolled(0, 0, 0);
                } else {
                    indicator.onPageScrolled(realPosition, 0, 0);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        int realPosition = toRealPosition(position);
        if (outerPageChangeListener != null) {
            outerPageChangeListener.onPageSelected(realPosition);
        }
        if (indicator != null) {
            indicator.onPageSelected(realPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            //如果是第一页，也就是真实数据的最后一页，直接设置到真实的最后一页
            if (currentPage == 0) {
                viewPager.setCurrentItem(realCount, false);
            }
            //如果是最后一页，那么真实数据的第一页，直接设置到真实数据的第一页
            else if (currentPage == needCount - 1) {
                viewPager.setCurrentItem(1, false);
            }
        }
        if (outerPageChangeListener != null) {
            outerPageChangeListener.onPageScrollStateChanged(state);
        }
        if (indicator != null) {
            indicator.onPageScrollStateChanged(state);
        }
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                currentPage++;
                if (currentPage == needCount) {
                    viewPager.setCurrentItem(currentPage = 1, false);
                    post(task);
                } else {
                    viewPager.setCurrentItem(currentPage);
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
            View view = views.get(position);
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
        if (isAutoPlay) {
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
}
