package com.to.aboomy.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by waitou on 17/2/12.
 */

public class LoopViewPager extends ViewPager {

    private OnPageChangeListener mOuterPageChangeListener;
    private LoopPagerAdapter     mAdapter;

    private long    autoTurningTime = 2500;
    private boolean isCanLoop;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        super.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int realPosition = mAdapter.toRealPosition(position);
                if (mOuterPageChangeListener != null) {
                    if (realPosition != mAdapter.getRealCount() - 1) {
                        mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                    } else {
                        if (positionOffset > .5) {
                            mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                        } else {
                            mOuterPageChangeListener.onPageScrolled(realPosition, 0, 0);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = mAdapter.toRealPosition(position);
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE) {
                    int currentItem = getCurrentItem();
                    int realAdapterPosition = mAdapter.controlAdapterPosition(currentItem);
                    if (currentItem != realAdapterPosition) {
                        setCurrentItem(realAdapterPosition, false);
                    }
                }
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public void setAdapter(PagerAdapter adapter, boolean isCanLoop) {
        mAdapter = (LoopPagerAdapter) adapter;
        this.isCanLoop = isCanLoop && getPageCount() > 1;
        mAdapter.setCanLoop(isCanLoop());
        int dataPosition = mAdapter.toRealPosition(getCurrentItem());
        super.setAdapter(mAdapter);
        setCurrentItem(mAdapter.startAdapterPosition(dataPosition), false);
        if (ViewCompat.isAttachedToWindow(this)) {
            startTurning();
        }
    }

    public boolean isCanLoop() {
        return isCanLoop;
    }

    public void setAutoTurningTime(long autoTurningTime) {
        this.autoTurningTime = autoTurningTime;
    }

    public void addPageChangeListener(OnPageChangeListener listener) {
        this.mOuterPageChangeListener = listener;
    }

    public int getPageCount() {
        return mAdapter.getRealCount();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isCanLoop()) {
            stopTurning();
            // 处理 RecyclerView 中从对用户不可见变为可见时卡顿的问题
            setCurrentItem(getCurrentItem() - 1);
            setCurrentItem(getCurrentItem() + 1);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isCanLoop()) {
            startTurning();
        }
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
                setCurrentItem(getCurrentItem() + 1, true);
                postDelayed(task, autoTurningTime);
            }
        }
    };

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

    public long getAutoTurningTime() {
        return autoTurningTime;
    }
}
