package com.to.aboomy.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by waitou on 17/2/12.
 */

public abstract class LoopPagerAdapter<T> extends PagerAdapter {

    private static final int MULTIPLE_COUNT = 600;

    protected List<T> mData;

    private WeakReference<LoopViewPager> loopViewPagerWeakReference;

    private SparseArrayCompat<View> mViews;
    private boolean                 isCanLoop;

    private int currentPostion;

    public void setLoopViewPager(LoopViewPager loopViewPager) {
        isCanLoop = getRealCount() > 1;
        loopViewPager.setCanLoop(isCanLoop);
        loopViewPagerWeakReference = new WeakReference<>(loopViewPager);
    }

    public LoopPagerAdapter(List<T> data) {
        mData = data;
        mViews = new SparseArrayCompat<>();
    }

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
        Log.e("aa", " position = " + position + " realPosition = " + realPosition);
        currentPostion = realPosition;
        View view = mViews.get(realPosition);
        if (view == null) {
            view = newView(container.getContext(), realPosition, mData.get(realPosition));
            mViews.put(realPosition, view);
        }
        ViewParent viewParent = view.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }


    public int getRealCount() {
        return mData.size();
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

    @Override
    public void notifyDataSetChanged() {
        if (loopViewPagerWeakReference.get() == null) {
            return;
        }
        final LoopViewPager loopViewPager = loopViewPagerWeakReference.get();
        loopViewPager.stopTurning();
        isCanLoop = mData.size() > 1;
        super.notifyDataSetChanged();
        if (mViews.size() > mData.size()) {
            mViews.clear();
        }
        loopViewPager.setCanLoop(isCanLoop);
        if (!isCanLoop) {
            loopViewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loopViewPager.setCurrentItem(0, false);
                }
            }, loopViewPager.getAutoTurningTime());
        }
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

    protected abstract View newView(Context context, int realPosition, T t);
}
