package com.to.aboomy.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by waitou on 17/2/12.
 */

public abstract class LoopPagerAdapter<T> extends PagerAdapter {

    private static final int MULTIPLE_COUNT = 600;

    private List<T> mData;

    private SparseArrayCompat<View> mViews;
    private SparseArrayCompat<View> mViewCache;

    private boolean isCanLoop;

    public LoopPagerAdapter(List<T> data) {
        mData = data;
        mViews = new SparseArrayCompat<>();
        if (data.size() == 2) {
            mViewCache = new SparseArrayCompat<>();
        }
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
        View view = mViews.get(realPosition);
        if (view != null && mViewCache != null && mViewCache.indexOfKey(realPosition) < 0) {
            view = newView(container.getContext(), realPosition, mData.get(realPosition));
            mViewCache.put(realPosition, view);
        }
        if (view == null) {
            view = newView(container.getContext(), realPosition, mData.get(realPosition));
            mViews.put(realPosition, view);
        }
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (mViewCache != null) {
            int realPosition = toRealPosition(position);
            if (mViewCache.containsValue((View) object)) {
                View view = mViews.get(realPosition);
                if (view != null) {
                    View cacheView = mViewCache.get(realPosition);
                    if (cacheView != null) {
                        mViews.put(realPosition, cacheView);
                        mViewCache.put(realPosition, view);
                    }
                }
            }
        }
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

    void setCanLoop(boolean isCanLoop) {
        this.isCanLoop = isCanLoop;
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
