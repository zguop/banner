package com.to.aboomy.bannersample.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class BannerBgContainer extends RelativeLayout {

    private int currentPosition;
    private List<RevealLayout> layouts = new ArrayList<>();

    public BannerBgContainer(Context context) {
        super(context);
    }

    public BannerBgContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerBgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnBindListener {
        View onBind(Context context, int pos, int itemCount, View view);
    }

    public void setBannerBackBg(int dataCount, int startIndex, OnBindListener listener) {
        if (dataCount > 0) {
            int childCount = getChildCount();
            if (dataCount < childCount) {
                removeViews(dataCount, childCount - dataCount);
                while (layouts.size() > dataCount) {
                    layouts.remove(layouts.size() - 1);
                }
            }
            for (int i = 0; i < dataCount; i++) {
                RevealLayout revealLayout = null;
                if (childCount - 1 >= i) {
                    revealLayout = (RevealLayout) getChildAt(i);
                }
                View view = null;
                if (revealLayout == null) {
                    revealLayout = new RevealLayout(getContext());
                    addView(revealLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    layouts.add(revealLayout);
                } else {
                    view = revealLayout.getChildAt(0);
                }
                View child = listener.onBind(getContext(), i, dataCount, view);
                if (view != child) {
                    revealLayout.removeView(view);
                    view = child;
                }
                if (view != null && view.getParent() == null) {
                    revealLayout.addView(view);
                }
            }
            for (int i = 0; i < layouts.size(); i++) {
                if(startIndex == i){
                    layouts.get(i).bringToFront();
                }
            }
        } else {
            removeAllViews();
        }
    }

    public void onPageScrolled(int position, float positionOffset) {
        if (positionOffset < 0.175) {
            currentPosition = position;
        }
        float reduceValue = 0.2f;
        float upValue = 2.5f;
        if (currentPosition == position) {
            int nextPage = (position + 1) % layouts.size();
            RevealLayout revealLayout = layouts.get(nextPage);
            revealLayout.bringToFront();
            revealLayout.hideClipAnimation(Math.min(1, (positionOffset - reduceValue) * upValue));
        } else {
            RevealLayout revealLayout = layouts.get(position);
            revealLayout.bringToFront();
            revealLayout.showClipAnimation(0, getHeight() >> 1,
                    Math.min(1, (1 - (positionOffset + reduceValue)) * upValue));
        }
    }
}
