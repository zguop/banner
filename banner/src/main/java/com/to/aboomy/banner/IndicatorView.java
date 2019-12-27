package com.to.aboomy.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * auth aboom
 * date 2018/7/7
 */
public class IndicatorView extends View implements Indicator {

    private final Interpolator interpolator = new DecelerateInterpolator();
    private Interpolator accelerateInterpolator;
    private RectF selectorRect;
    private Path path;
    private float offset;
    private int selectedPage;
    private int pagerCount;

    private final Paint selectedIndicatorPaint;
    private final Paint indicatorPaint;
    /**
     * 控制在banner中的位置
     */
    private RelativeLayout.LayoutParams params;
    /**
     * indicator样式 目前支持三种样式
     */
    private int indicatorStyle;

    private float indicatorRadius = dip2px(3.5f);
    private float indicatorSpacing = dip2px(10);

    @IntDef({IndicatorStyle.INDICATOR_CIRCLE, IndicatorStyle.INDICATOR_CIRCLE_RECT, IndicatorStyle.INDICATOR_BEZIER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorStyle {
        int INDICATOR_CIRCLE = 0;
        int INDICATOR_CIRCLE_RECT = 1;
        int INDICATOR_BEZIER = 2;
    }

    public IndicatorView(Context context) { this(context, null); }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public IndicatorView setIndicatorRadius(float indicatorRadius) {
        this.indicatorRadius = indicatorRadius;
        return this;
    }

    public IndicatorView setIndicatorSpacing(float indicatorSpacing) {
        this.indicatorSpacing = indicatorSpacing;
        return this;
    }

    public IndicatorView setIndicatorStyle(@IndicatorStyle int indicatorStyle) {
        this.indicatorStyle = indicatorStyle;
        return this;
    }

    public IndicatorView setIndicatorColor(@ColorInt int indicatorColor) {
        this.indicatorPaint.setColor(indicatorColor);
        return this;
    }

    public IndicatorView setIndicatorSelectorColor(@ColorInt int indicatorSelectorColor) {
        this.selectedIndicatorPaint.setColor(indicatorSelectorColor);
        return this;
    }

    public IndicatorView setParams(RelativeLayout.LayoutParams params) {
        this.params = params;
        return this;
    }

    @Override
    public void initIndicatorCount(int pagerCount) {
        this.pagerCount = pagerCount;
        setVisibility(pagerCount > 1 ? VISIBLE : GONE);
        requestLayout();
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
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.bottomMargin = dip2px(10);
        }
        return params;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = width;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) (pagerCount * indicatorRadius * 2 + (pagerCount - 1) * indicatorSpacing + getPaddingLeft() + getPaddingRight());
                break;
            default:
                break;
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = height;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) (indicatorRadius * 2 + getPaddingTop() + getPaddingBottom());
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pagerCount == 0) {
            return;
        }
        float midY = getHeight() / 2.0f + 0.5f;
        int adapterCount = pagerCount;
        for (int i = 0; i < adapterCount; i++) {
            float startCx = indicatorStartX(i);
            canvas.drawCircle(startCx, midY, indicatorRadius, indicatorPaint);
        }
        if (indicatorStyle == IndicatorStyle.INDICATOR_CIRCLE) {
            drawCircle(canvas, midY);
        } else if (indicatorStyle == IndicatorStyle.INDICATOR_CIRCLE_RECT) {
            drawCircleRect(canvas, midY);
        } else if (indicatorStyle == IndicatorStyle.INDICATOR_BEZIER) {
            drawBezier(canvas, midY);
        }
    }

    private void drawBezier(Canvas canvas, float midY) {
        if (path == null) path = new Path();
        if (accelerateInterpolator == null) accelerateInterpolator = new AccelerateInterpolator();
        float indicatorStartX = indicatorStartX(selectedPage);
        float nextIndicatorStartX = indicatorStartX(selectedPage + 1);
        float leftX = indicatorStartX + (nextIndicatorStartX - indicatorStartX) * accelerateInterpolator.getInterpolation(offset);
        float rightX = indicatorStartX + (nextIndicatorStartX - indicatorStartX) * interpolatedOffset();
        float minRadius = indicatorRadius * 0.57f;
        float leftRadius = indicatorRadius + (minRadius - indicatorRadius) * interpolatedOffset();
        float rightRadius = minRadius + (indicatorRadius - minRadius) * accelerateInterpolator.getInterpolation(offset);
        canvas.drawCircle(leftX, midY, leftRadius, selectedIndicatorPaint);
        canvas.drawCircle(rightX, midY, rightRadius, selectedIndicatorPaint);
        path.reset();
        path.moveTo(rightX, midY);
        path.lineTo(rightX, midY - rightRadius);
        path.quadTo(rightX + (leftX - rightX) / 2.0f, midY, leftX, midY - leftRadius);
        path.lineTo(leftX, midY + leftRadius);
        path.quadTo(rightX + (leftX - rightX) / 2.0f, midY, rightX, midY + rightRadius);
        path.close();
        canvas.drawPath(path, selectedIndicatorPaint);
    }

    private void drawCircleRect(Canvas canvas, float midY) {
        float indicatorStartX = indicatorStartX(selectedPage);
        float extStart = indicatorStartX + Math.max((indicatorSpacing + indicatorRadius * 2) * (interpolatedOffset() - 0.5f) * 2.0f, 0);
        float extenderEnd = indicatorStartX + Math.min(((indicatorSpacing + indicatorRadius * 2) * interpolatedOffset() * 2), indicatorSpacing + indicatorRadius * 2);
        if (selectorRect == null) selectorRect = new RectF();
        selectorRect.set(extStart - indicatorRadius, midY - indicatorRadius, extenderEnd + indicatorRadius, midY + indicatorRadius);
        canvas.drawRoundRect(selectorRect, indicatorRadius, indicatorRadius, selectedIndicatorPaint);
    }

    private void drawCircle(Canvas canvas, float midY) {
        float indicatorStartX = indicatorStartX(selectedPage);
        float nextIndicatorStartX = indicatorStartX(selectedPage + 1);
        float indicatorX = indicatorStartX + (nextIndicatorStartX - indicatorStartX) * interpolatedOffset();
        canvas.drawCircle(indicatorX, midY, indicatorRadius, selectedIndicatorPaint);
    }

    private float indicatorStartX(int index) {
        float centerSpacing = indicatorRadius * 2.0f + indicatorSpacing;
        return indicatorRadius + getPaddingLeft() + centerSpacing * index;
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
