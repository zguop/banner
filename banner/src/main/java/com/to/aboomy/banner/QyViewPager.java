package com.to.aboomy.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by waitou on 17/2/12.
 */

public class QyViewPager extends ViewPager {

    private OnPageChangeListener mOuterPageChangeListener;

    private QyPagerAdapter mAdapter;

    private AdSwitchTask mAdSwitchTask;

    private boolean turning; //是否正在翻页
    private long autoTurningTime = 2_500;
    private boolean isCanLoop = Boolean.TRUE;

    public QyViewPager(Context context) {
        this(context, null);
    }

    public QyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAdSwitchTask = new AdSwitchTask(this);

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

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = (QyPagerAdapter) adapter;
        mAdapter.setCanLoop(isCanLoop && mAdapter.getRealCount() != 1);
        super.setAdapter(mAdapter);
        setCurrentItem(mAdapter.startAdapterPosition(0), false);
    }

    public void setCanLoop(boolean isCanLoop){
        this.isCanLoop = isCanLoop;
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
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isCanLoop()) {
            startTurning();
        }
    }


    private void startTurning() {
        if (turning) {
            stopTurning();
        }
        turning = true;
        postDelayed(mAdSwitchTask, autoTurningTime);
    }

    private void stopTurning() {
        turning = false;
        removeCallbacks(mAdSwitchTask);
    }

    private static class AdSwitchTask implements Runnable {

        private final WeakReference<QyViewPager> reference;

        AdSwitchTask(QyViewPager qyViewPager) {
            this.reference = new WeakReference<>(qyViewPager);
        }

        @Override
        public void run() {
            QyViewPager qyViewPager = reference.get();
            if (qyViewPager == null) {
                return;
            }
            if (qyViewPager.turning) {
                int page = qyViewPager.getCurrentItem() + 1;
                qyViewPager.setCurrentItem(page);
                qyViewPager.startTurning();
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if (isCanLoop() && action == MotionEvent.ACTION_DOWN) {
            stopTurning();
        }
        if (isCanLoop() && action == MotionEvent.ACTION_UP) {
            startTurning();
        }

        if (isCanLoop() && action == MotionEvent.ACTION_CANCEL) {
            startTurning();
        }
        return super.dispatchTouchEvent(ev);
    }
}
