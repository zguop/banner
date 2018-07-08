package com.to.aboomy.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

/**
 * auth aboom
 * date 2018/7/7
 */
public class QyIndicator extends View implements IQyIndicator, ViewPager.OnPageChangeListener {

    private final Interpolator interpolator = new DecelerateInterpolator();

    private final Paint selectedIndicatorPaint;
    private final Paint indicatorPaint;
    private final RectF selectorRect;

    private float indicatorRadius  = dip2px(3.5f);
    private float indicatorPadding = dip2px(12);
    private int   gravity          = Gravity.START;

    private int indicatorLeftMargin;
    private int indicatorRightMargin;

    private float                       offset;
    private int                         adapterCount;
    private int                         selectedPage;
    private RelativeLayout.LayoutParams params;

    public QyIndicator(Context context) {
        this(context, null);
    }

    public QyIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QyIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectorRect = new RectF();
    }

    public QyIndicator setIndicatorRadius(float indicatorRadius) {
        this.indicatorRadius = indicatorRadius;
        return this;
    }

    public QyIndicator setIndicatorPadding(float indicatorPadding) {
        this.indicatorPadding = indicatorPadding;
        return this;
    }

    public QyIndicator setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public QyIndicator setIndicatorLeftMargin(int indicatorLeftMargin) {
        this.indicatorLeftMargin = indicatorLeftMargin;
        return this;
    }

    public QyIndicator setIndicatorRightMargin(int indicatorRightMargin) {
        this.indicatorRightMargin = indicatorRightMargin;
        return this;
    }

    public QyIndicator setIndicatorColor(int indicatorColor) {
        this.indicatorPaint.setColor(indicatorColor);
        return this;
    }

    public QyIndicator setIndicatorInColor(int indicatorInColor) {
        this.selectedIndicatorPaint.setColor(indicatorInColor);
        return this;
    }

    public QyIndicator setParams(RelativeLayout.LayoutParams params) {
        this.params = params;
        return this;
    }


    @Override
    public void setViewPager(ViewPager viewPager) {
        if (viewPager instanceof QyViewPager) {
            QyViewPager qyViewPager = (QyViewPager) viewPager;
            this.adapterCount = qyViewPager.getPageCount();
            qyViewPager.addPageChangeListener(this);
        }
        setVisibility(adapterCount > 1 ? VISIBLE : GONE);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public RelativeLayout.LayoutParams getParams() {
        if (params == null) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.bottomMargin = dip2px(10);
        }
        return params;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getSuggestedMinimumHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float midY = getHeight() / 2f;
        for (int i = 0; i < adapterCount; i++) {
            float startCx = indicatorStartX(i);
            canvas.drawCircle(startCx, midY, indicatorRadius, indicatorPaint);
        }
        float extStart = indicatorStartX(selectedPage) + Math.max(indicatorPadding * (interpolatedOffset() - 0.5f) * 2, 0);
        float extenderEnd = indicatorStartX(selectedPage) + Math.min(indicatorPadding * interpolatedOffset() * 2, indicatorPadding);
        selectorRect.set(extStart - indicatorRadius, midY - indicatorRadius, extenderEnd + indicatorRadius, midY + indicatorRadius);
        canvas.drawRoundRect(selectorRect, indicatorRadius, indicatorRadius, selectedIndicatorPaint);
//        mPath.reset();
//        float middleX = (indicatorStartX(selectedPage) - indicatorRadius + extStart) / 2;
//        mPath.moveTo(indicatorStartX(selectedPage), midY + indicatorRadius);
//        mPath.quadTo(middleX, midY, extStart, midY + indicatorRadius);
//        mPath.lineTo(extStart, midY - indicatorRadius);
//        mPath.quadTo(middleX, midY, indicatorStartX(selectedPage), midY - indicatorRadius);
//        mPath.lineTo(indicatorStartX(selectedPage), midY + indicatorRadius);
//        mPath.close();  // 闭合
//        canvas.drawPath(mPath, selectedIndicatorPaint);
//        canvas.drawCircle(extStart, midY, indicatorRadius, selectedIndicatorPaint);
    }

    private float indicatorStartX(int index) {
        float padding = ViewCompat.getPaddingStart(this) + indicatorPadding * index + indicatorRadius;
        if (gravity == Gravity.END) {
            return getWidth() - indicatorPadding * (adapterCount - 1) - indicatorRadius * 2 + padding - indicatorRightMargin + indicatorLeftMargin;
        }
        if (gravity == Gravity.CENTER) {
            return getWidth() / 2.0f + padding + (indicatorLeftMargin - indicatorRightMargin) - (indicatorPadding * (adapterCount - 1) / 2.0f - indicatorRadius);
        }
        return padding + indicatorLeftMargin - indicatorRightMargin;
    }

    private float interpolatedOffset() {
        return interpolator.getInterpolation(offset);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        selectedPage = position;
        offset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int dip2px(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
