package com.to.aboomy.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private static final long DEFAULT_AUTO_TIME = 2500;
    private static final int NORMAL_COUNT = 2;

    private ViewPager.OnPageChangeListener outerPageChangeListener;
    private HolderCreator holderCreator;
    private BannerViewPager viewPager;
    private PagerAdapter adapter;
    private List<View> views;
    private Indicator indicator;
    private boolean isAutoPlay = true;
    private long autoTurningTime = DEFAULT_AUTO_TIME;

    /**
     * 虚拟当前页 1表示真实页数的第一页
     */
    private int currentPage;
    /**
     * 实际数量
     */
    private int realCount;
    /**
     * 需要的数量
     */
    private int needCount;
    /**
     * 额外的页数
     */
    private int needPage = NORMAL_COUNT;
    /**
     * 额外的页数是往最左边添加和最右边添加，该变量记录一边添加的数量
     */
    private int sidePage;


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

    /**
     * 设置一屏多页
     *
     * @param multiWidth 左右页面露出来的宽度一致
     * @param pageMargin >0 item与item之间的宽度， <0 item与item之间重叠宽度，小于0 魅族效果banner效果
     */
    public Banner setPageMargin(int multiWidth, int pageMargin) {
        return setPageMargin(multiWidth, multiWidth, pageMargin);
    }

    /**
     * 设置一屏多页
     *
     * @param leftWidth  左边页面显露出来的宽度
     * @param rightWidth 右边页面露出来的宽度
     * @param pageMargin >0 item与item之间的宽度， <0 item与item之间重叠宽度
     */
    public Banner setPageMargin(int leftWidth, int rightWidth, int pageMargin) {
        if (viewPager != null) {
            if (pageMargin != 0) {
                viewPager.setPageMargin(pageMargin);
                viewPager.setOverlapStyle(pageMargin < 0);
            }
            if (leftWidth > 0 && rightWidth > 0) {
                LayoutParams layoutParams = (LayoutParams) viewPager.getLayoutParams();
                layoutParams.leftMargin = leftWidth + Math.abs(pageMargin);
                layoutParams.rightMargin = rightWidth + Math.abs(pageMargin);
                viewPager.setOffscreenPageLimit(2);
                needPage += NORMAL_COUNT;
            }
        }
        return this;
    }

    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        if (viewPager != null) {
            viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        }
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
        if (viewPager != null) {
            viewPager.setPagerScrollDuration(pagerScrollDuration);
        }
        return this;
    }

    public Banner setOffscreenPageLimit(int limit) {
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(limit);
        }
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
        return setIndicator(indicator, true);
    }

    /**
     * 设置indicator，支持在xml中创建
     *
     * @param attachToRoot true 添加到banner布局中
     */
    public Banner setIndicator(Indicator indicator, boolean attachToRoot) {
        if (this.indicator != null) {
            removeView(this.indicator.getView());
        }
        if (indicator != null) {
            this.indicator = indicator;
            if (attachToRoot) {
                addView(this.indicator.getView(), this.indicator.getParams());
            }
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
        currentPage = startPosition + sidePage;
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
        sidePage = needPage / NORMAL_COUNT;
        needCount = realCount + needPage;
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
    public int getCurrentPager() {
        int position = toRealPosition(currentPage);
        return Math.max(position, 0);
    }

    public void startTurning() {
        stopTurning();
        postDelayed(task, autoTurningTime);
    }

    public void stopTurning() {
        removeCallbacks(task);
    }

    private int toRealPosition(int position) {
        int realPosition = 0;
        if (realCount != 0) {
            realPosition = (position - sidePage) % realCount;
        }
        if (realPosition < 0) {
            realPosition += realCount;
        }
        return realPosition;
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
            indicator.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        //解决多次重复回调onPageSelected问题,暂时这么处理
        boolean resetItem = currentPage == sidePage - 1 || currentPage == needCount - (sidePage - 1) || (position != currentPage && needCount - currentPage == sidePage);
        currentPage = position;
        if (resetItem) return;
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
        if (outerPageChangeListener != null) {
            outerPageChangeListener.onPageScrollStateChanged(state);
        }
        if (indicator != null) {
            indicator.onPageScrollStateChanged(state);
        }
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            //一屏一页0是真实数据的最后一页，一屏三页1是真实数据的最后一页，实际数量 + 虚拟当前页 = 实际最后一页索引
            if (currentPage == sidePage - 1) {
                viewPager.setCurrentItem(realCount + currentPage, false);
            }
            //如果是最后一页，那么真实数据的第一页，直接设置到真实数据的第一页
            else if (currentPage == needCount - sidePage) {
                viewPager.setCurrentItem(sidePage, false);
            }
        }
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                currentPage++;
                if (currentPage == realCount + sidePage + 1) {
                    viewPager.setCurrentItem(sidePage, false);
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
