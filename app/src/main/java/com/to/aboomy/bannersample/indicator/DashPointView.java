package com.to.aboomy.bannersample.indicator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.to.aboomy.banner.Indicator;

public class DashPointView extends LinearLayout implements Indicator {

    private int pointRadius = dip2px(3);
    private int pointSelectColor = Color.RED;
    private int pointColor = Color.GRAY;
    private int maxPointWidth = pointRadius * 6;
    private int pointWidth = pointRadius * 2;
    private int pointHeight = pointRadius * 2;
    private int spacing = pointRadius;

    public DashPointView(Context context) {
        super(context);
    }

    public DashPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initIndicatorCount(int pagerCount) {
        setVisibility(pagerCount > 1 ? VISIBLE : GONE);
        removeAllViews();
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = spacing;
        lp.rightMargin = spacing;
        for (int i = 0; i < pagerCount; i++) {
            // 翻页指示的点
            TextView pointView = new TextView(getContext());
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadii(new float[]{pointRadius, pointRadius, pointRadius, pointRadius, pointRadius, pointRadius, pointRadius, pointRadius});
            if (0 == i) {
                pointView.setWidth(maxPointWidth);
                drawable.setColor(pointSelectColor);
                pointView.setSelected(true);
            } else {
                pointView.setWidth(pointWidth);
                drawable.setColor(pointColor);
                pointView.setSelected(false);
            }
            pointView.setHeight(pointHeight);
            pointView.setBackground(drawable);
            addView(pointView, lp);
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public RelativeLayout.LayoutParams getParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = dip2px(20);
        return params;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        selectPoint(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setPointSelectColor(int pointSelectColor) {
        this.pointSelectColor = pointSelectColor;
    }

    public void setPointRadius(int mPointRadius) {
        this.pointRadius = mPointRadius;
    }

    public void setMaxPointWidth(int maxPointWidth) {
        this.maxPointWidth = maxPointWidth;
    }

    public void setPointWidth(int pointWidth) {
        this.pointWidth = pointWidth;
    }

    public void setPointHeight(int pointHeight) {
        this.pointHeight = pointHeight;
    }

    public void setPointSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void selectPoint(int position) {
        final int count = getChildCount();
        if (position >= 0 && position < count) {
            TextView pointView;
            for (int i = 0; i < count; i++) {
                pointView = (TextView) getChildAt(i);
                if (position == i) {
                    selectPoint(pointView);
                } else {
                    unSelectPoint(pointView);
                }
            }
        }
    }

    private void selectPoint(final TextView v) {
        if (v != null && !v.isSelected()) {
            final float temp = pointRadius * 4f / 300f;
            final GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(pointSelectColor);
            v.animate().setDuration(300).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float width = pointWidth + (animation.getCurrentPlayTime() * temp);
                    if (animation.getCurrentPlayTime() >= 300) {
                        width = maxPointWidth;
                    }
                    v.setWidth((int) width);
                }
            }).start();
            v.setSelected(true);
        }
    }

    private void unSelectPoint(final TextView v) {
        if (v != null && v.isSelected()) {
            final float temp = pointRadius * 4f / 300f;
            final GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(pointColor);
            v.animate().setDuration(300).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float width = maxPointWidth - (Math.min(animation.getCurrentPlayTime(), 300) * temp);
                    if (animation.getCurrentPlayTime() >= 300) {
                        width = pointWidth;
                    }
                    v.setWidth((int) width);
                }
            }).start();
            v.setSelected(false);
        }
    }

    private int dip2px(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
