package com.to.aboomy.pager2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class Banner extends RelativeLayout {

    private static final long DEFAULT_AUTO_TIME = 2500;
    private static final int NORMAL_COUNT = 2;


    private ViewPager2 viewPager2;
    private BannerAdapter bannerAdapter;
    private Adapter adapter;
    private boolean isAutoPlay = true;

    private long autoTurningTime = DEFAULT_AUTO_TIME;

    private List<?> items;
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
        setClipChildren(Boolean.FALSE);
        initViews(context);

    }

    private void initViews(final Context context) {
        viewPager2 = new ViewPager2(context);
        viewPager2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager2.setClipChildren(Boolean.FALSE);
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
        addView(viewPager2);
    }

    public Banner setAutoTurningTime(long autoTurningTime) {
        this.autoTurningTime = autoTurningTime;
        return this;
    }

    public Banner setOffscreenPageLimit(int limit) {
        if (viewPager2 != null) {
            viewPager2.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * 是否自动轮播 大于1页轮播才生效
     */
    public Banner setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
        if (isAutoPlay && adapter != null) {
            isAutoPlay = realCount > 1;
            if (isAutoPlay) {
                startTurning();
            }
        }
        return this;
    }

    public Banner setAdapter(Adapter adapter) {
        this.adapter = adapter;
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
        this.items = items;
        startPager(startPosition);
    }

    private void startPager(int startPosition) {
        if (items == null || items.size() == 0 || adapter == null) {
            realCount = 0;
            needCount = 0;
            isAutoPlay = false;
        } else {
            realCount = items.size();
            needCount = realCount + NORMAL_COUNT;
            isAutoPlay = isAutoPlay && realCount > 1;
        }
        if (bannerAdapter == null) {
            bannerAdapter = new BannerAdapter();
            viewPager2.setAdapter(bannerAdapter);
        } else {
            bannerAdapter.notifyDataSetChanged();
        }
        currentPage = startPosition + 1;
        viewPager2.setUserInputEnabled(realCount > 1);
        viewPager2.setCurrentItem(currentPage, false);
        if (isAutoPlay) {
            startTurning();
        }
        Log.e("aa" , " viewpager " + viewPager2.getOffscreenPageLimit());
    }

    public void startTurning() {
        stopTurning();
        postDelayed(task, autoTurningTime);
    }

    public void stopTurning() {
        removeCallbacks(task);
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
            if (isAutoPlay && realCount > 0) {
                currentPage++;
                viewPager2.setCurrentItem(currentPage % needCount);
                postDelayed(task, autoTurningTime);
            }
        }
    };

    private class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return adapter.onCreateDefViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Log.e("aa", "onBindViewHolder position " + position);
            adapter.onBindViewHolder(holder, position, items.get(toRealPosition(position)));
        }

        @Override
        public int getItemCount() {
            return needCount;
        }
    }

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
}
