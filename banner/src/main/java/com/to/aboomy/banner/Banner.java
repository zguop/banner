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
    private static final int MULTIPLE_COUNT = 600;


    private List<View> mViews;
    private List<View> mHackyViews;
    private BannerViewPager mViewPager;
    private Indicator mIndicator;
    private ViewPager.OnPageChangeListener mOuterPageChangeListener;
    private PagerAdapter mAdapter;
    private long autoTurningTime = 2500;
    private boolean isCanLoop;
    private boolean isSetAdapter;
    private List<View> imageViews;


    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipChildren(Boolean.FALSE);
        initViews(context);
        initViewPagerScroll();
    }

    private void initViews(Context context) {
        mViewPager = new BannerViewPager(context);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mViewPager.setClipChildren(Boolean.FALSE);
        addView(mViewPager);
    }

    public void setPageMargins(int left, int top, int right, int bottom, int pageMargin) {
        LayoutParams layoutParams = (LayoutParams) mViewPager.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setOffscreenPageLimit(3);
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    public void setIndicator(Indicator indicator) {
        if (mIndicator != null) {
            removeView(mIndicator.getView());
        }
        mIndicator = indicator;
        addView(mIndicator.getView(), mIndicator.getParams());
    }


    public void setPages(List<?> items, HolderCreator holderCreator) {
        createImages(items, holderCreator);
        if (this.mAdapter == null) {
            mAdapter = new BannerAdapter();
            mViewPager.addOnPageChangeListener(this);
            mViewPager.setAdapter(mAdapter);
        } else {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void createImages(List<?> items, HolderCreator holderCreator) {
        if (items == null || items.size() == 0 || holderCreator == null) {
            return;
        }
        int count = items.size();
        isCanLoop = count > 1;
        for (int i = 0; i < count; i++) {
            View view = holderCreator.createView(getContext());
            mViews.add(view);
        }
        if (mViews.size() == 2) {
            mHackyViews = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                View view = holderCreator.createView(getContext());
                mHackyViews.add(view);
            }
        }
    }

    public boolean isCanLoop() {
        return isCanLoop;
    }

    public void startTurning() {
        stopTurning();
        postDelayed(task, autoTurningTime);
    }

    public void stopTurning() {
        removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isCanLoop()) {
//                setCurrentItem(getCurrentItem() + 1, true);
                postDelayed(task, autoTurningTime);
            }
        }
    };


//    public void setAdapter(PagerAdapter adapter) {
//        setAdapter(adapter, true);
//    }
//
//    public void setAdapter(PagerAdapter adapter, boolean isCanLoop) {
//        if (mViewPager != null) {
//            mViewPager.setAdapter(adapter, isCanLoop);
//            if (mIndicator != null) {
//                mIndicator.setViewPager(mViewPager);
//            }
//        }
//        isSetAdapter = true;
//    }

//    public LoopViewPager getViewPager() {
//        return mViewPager;
//    }


    public boolean isSetAdapter() {
        return isSetAdapter;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return isCanLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int realPosition = toRealPosition(position);
            View view = mViews.get(realPosition);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        public int getRealCount() {
            return mViews.size();
        }

        /**
         * 轮播图启动的位置
         */
        int startAdapterPosition(int dataPosition) {
            if (isCanLoop) {
                return getRealCount() * MULTIPLE_COUNT / 2 + dataPosition;
            }
            return dataPosition;
        }

        /**
         * 控制轮播图的范围
         */
        int controlAdapterPosition(int adapterPosition) {
            if (isCanLoop) {
                if (adapterPosition > (getRealCount() * MULTIPLE_COUNT * 0.98) || adapterPosition < (getRealCount() * MULTIPLE_COUNT * 0.02)) {
                    return startAdapterPosition(toRealPosition(adapterPosition));
                }
            }
            return adapterPosition;
        }

        public int toRealPosition(int position) {
            int realCount = getRealCount();
            if (realCount != 0) {
                return position % realCount;
            }
            return 0;
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
