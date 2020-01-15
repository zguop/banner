package com.to.aboomy.pager2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class Banner extends RelativeLayout {

    private static final long DEFAULT_AUTO_TIME = 2500;
    private static final int NORMAL_COUNT = 2;

    private CompositePageTransformer compositePageTransformer;
    private BannerAdapterWrapper bannerAdapterWrapper;
    private ViewPager2 viewPager2;
    private RecyclerView recyclerView;
    private boolean isAutoPlay = true;
    private long autoTurningTime = DEFAULT_AUTO_TIME;

    private int currentPage;
    private int realCount;
    private int needCount;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(final Context context) {
        viewPager2 = new ViewPager2(context);
        viewPager2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager2.setPageTransformer(compositePageTransformer = new CompositePageTransformer());
        bannerAdapterWrapper = new BannerAdapterWrapper();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("aa", " onPageSelected " + position);
                currentPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_IDLE || state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    if (currentPage == 0) {
                        viewPager2.setCurrentItem(realCount, false);
                    } else if (currentPage == needCount - 1) {
                        viewPager2.setCurrentItem(1, false);
                    }
                }
            }
        });
        recyclerView = (RecyclerView) viewPager2.getChildAt(0);
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        addView(viewPager2);
    }


    /**
     * 设置一屏多页
     *
     * @param multiWidth 左右页面露出来的宽度一致
     * @param pageMargin >0 item与item之间的宽度
     */
    public Banner setPageMargin(int multiWidth, int pageMargin) {
        return setPageMargin(multiWidth, multiWidth, pageMargin);
    }

    /**
     * 设置一屏多页
     *
     * @param tlWidth    左边页面显露出来的宽度
     * @param brWidth    右边页面露出来的宽度
     * @param pageMargin >0 item与item之间的宽度， <0 item与item之间重叠宽度
     */
    public Banner setPageMargin(int tlWidth, int brWidth, int pageMargin) {
        if (pageMargin != 0) {
            compositePageTransformer.addTransformer(new PageMarginTransFormer(pageMargin));
        }
        if (tlWidth > 0 && brWidth > 0) {
            if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                recyclerView.setPadding(0, tlWidth + Math.abs(pageMargin), 0, brWidth + Math.abs(pageMargin));
            } else {
                recyclerView.setPadding(tlWidth + Math.abs(pageMargin), 0, brWidth + Math.abs(pageMargin), 0);
            }
            recyclerView.setClipToPadding(false);
            setOffscreenPageLimit(1);
        }
        return this;
    }

    public Banner setPageTransformer(ViewPager2.PageTransformer transformer) {
        compositePageTransformer.addTransformer(transformer);
        return this;
    }

    public Banner setAutoTurningTime(long autoTurningTime) {
        this.autoTurningTime = autoTurningTime;
        return this;
    }

    public Banner setOffscreenPageLimit(int limit) {
            viewPager2.setOffscreenPageLimit(limit);

        return this;
    }

    /**
     * 设置轮播方向
     *
     * @param orientation Orientation.ORIENTATION_HORIZONTAL or default
     *                    Orientation.ORIENTATION_VERTICAL
     */
    public Banner setOrientation(@ViewPager2.Orientation int orientation) {
        viewPager2.setOrientation(orientation);
        return this;
    }

    /**
     * 是否自动轮播 大于1页轮播才生效
     */
    public Banner setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
        if (isAutoPlay && realCount > 1) {
            startTurning();
        }
        return this;
    }

    /**
     * 返回真实位置
     */
    public int getCurrentPager() {
        int position = toRealPosition(currentPage);
        return Math.max(position, 0);
    }

    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        setAdapter(adapter, 0);
    }

    public void setAdapter(@Nullable RecyclerView.Adapter adapter, int startPosition) {
        createPageNumber(adapter != null ? adapter.getItemCount() : 0);
        bannerAdapterWrapper.registerAdapter(adapter);
        viewPager2.setAdapter(bannerAdapterWrapper);
        startPager(startPosition);
    }

    public <T> Banner setHolderCreator(HolderCreator<T> holderCreator) {
        if (bannerAdapterWrapper == null) bannerAdapterWrapper = new BannerAdapterWrapper();
        bannerAdapterWrapper.registerAdapter(null);
        BannerAdapter<T> bannerAdapter = new BannerAdapter<>(holderCreator);
        bannerAdapterWrapper.registerAdapter(bannerAdapter);
        return this;
    }

    public void setPages(List<?> items) {
        setPages(items, 0);
    }

    /**
     * @param items         数据集
     * @param startPosition 开始位置 真实索引
     */
    public void setPages(List<?> items, int startPosition) {
        createPageNumber(items == null || items.size() == 0 ? 0 : items.size());
        if (realCount == 0) {
            if (bannerAdapterWrapper != null) {
                createPageNumber(0);
                bannerAdapterWrapper.registerAdapter(null);
                viewPager2.setAdapter(null);
            }
            return;
        }
        BannerAdapter bannerAdapter = (BannerAdapter) bannerAdapterWrapper.adapter;
        bannerAdapter.replaceData(items);
        startPager(startPosition);
    }


    public void startTurning() {
        stopTurning();
        postDelayed(task, autoTurningTime);
    }

    public void stopTurning() {
        removeCallbacks(task);
    }

    private void startPager(int startPosition) {
        currentPage = startPosition + 1;
        viewPager2.setUserInputEnabled(realCount > 1);
        viewPager2.setCurrentItem(currentPage, false);
        if (isAutoPlay) {
            startTurning();
        }
    }

    private void createPageNumber(int size) {
        if (size == 0) {
            realCount = 0;
            needCount = 0;
            return;
        }
        realCount = size;
        needCount = realCount + NORMAL_COUNT;
        isAutoPlay = isAutoPlay && realCount > 1;
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

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay && realCount > 1) {
                currentPage++;
                viewPager2.setCurrentItem(currentPage % needCount);
                postDelayed(task, autoTurningTime);
            }
        }
    };

    private int toRealPosition(int position) {
        if (position == 0) {
            return realCount - 1;
        } else if (position == needCount - 1) {
            return 0;
        } else {
            return position - 1;
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

    private RecyclerView.AdapterDataObserver mCurrentItemDataSetChangeObserver =
            new DataSetChangeObserver() {
                @Override
                public void onChanged() {
                    if (viewPager2 != null && bannerAdapterWrapper != null) {
                        createPageNumber(bannerAdapterWrapper.adapter.getItemCount());
                        bannerAdapterWrapper.notifyDataSetChanged();
                        startPager(getCurrentPager());
                    }
                }
            };

    private class BannerAdapterWrapper extends RecyclerView.Adapter {

        private RecyclerView.Adapter adapter;


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Log.e("aa", "onBindView position " + position);
            adapter.onBindViewHolder(holder, toRealPosition(position));
        }


        @Override
        public int getItemViewType(int position) {
            return adapter.getItemViewType(toRealPosition(position));
        }

        @Override
        public long getItemId(int position) {
            return adapter.getItemId(toRealPosition(position));
        }

        @Override
        public int getItemCount() {
            return needCount;
        }

        void registerAdapter(RecyclerView.Adapter adapter) {
            if (this.adapter != null) {
                this.adapter.unregisterAdapterDataObserver(mCurrentItemDataSetChangeObserver);
            }
            this.adapter = adapter;
            if (this.adapter != null) {
                this.adapter.registerAdapterDataObserver(mCurrentItemDataSetChangeObserver);
            }
        }
    }

    private abstract static class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public abstract void onChanged();

        @Override
        public final void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public final void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            onChanged();
        }

        @Override
        public final void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public final void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public final void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    }
}
