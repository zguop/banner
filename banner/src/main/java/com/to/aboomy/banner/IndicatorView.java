package com.to.aboomy.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class IndicatorView extends View implements Indicator {

    private final Interpolator interpolator = new DecelerateInterpolator();
    private Interpolator accelerateInterpolator;

    private Path path;
    private float offset;
    private int selectedPage;
    private int pagerCount;
    private int unColor = Color.GRAY;
    private int selectedColor = Color.WHITE;

    private final Paint indicatorPaint;
    private final RectF rectF;
    /**
     * 控制在banner中的位置
     */
    private RelativeLayout.LayoutParams params;
    /**
     * indicator样式
     */
    private int indicatorStyle;

    /*--------------- 核心控制点大小距离参数 ---------------*/
    private float indicatorRadius = dip2px(3.5f);
    private float indicatorRatio = 1.0f;
    private float indicatorSelectedRadius = dip2px(3.5f);
    private float indicatorSelectedRatio = 1.0f;
    private float indicatorSpacing = dip2px(10);
    /*--------------- 核心控制点大小距离参数end ---------------*/

    @IntDef({IndicatorStyle.INDICATOR_CIRCLE,
            IndicatorStyle.INDICATOR_CIRCLE_RECT,
            IndicatorStyle.INDICATOR_BEZIER,
            IndicatorStyle.INDICATOR_DASH,
            IndicatorStyle.INDICATOR_BIG_CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorStyle {
        int INDICATOR_CIRCLE = 0;
        int INDICATOR_CIRCLE_RECT = 1;
        int INDICATOR_BEZIER = 2;
        int INDICATOR_DASH = 3;
        int INDICATOR_BIG_CIRCLE = 4;
    }

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rectF = new RectF();
        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
                result = (int) (pagerCount * Math.max(indicatorRadius, indicatorSelectedRadius) * indicatorRatio * 2 + (pagerCount - 1) * indicatorSpacing + getPaddingLeft() + getPaddingRight());
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
                result = (int) (Math.max(indicatorRadius, indicatorSelectedRadius) * 2 + getPaddingTop() + getPaddingBottom());
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
        if (indicatorStyle == IndicatorStyle.INDICATOR_CIRCLE) {
            drawCircle(canvas, midY);
        } else if (indicatorStyle == IndicatorStyle.INDICATOR_CIRCLE_RECT) {
            drawCircleRect(canvas, midY);
        } else if (indicatorStyle == IndicatorStyle.INDICATOR_BEZIER) {
            drawBezier(canvas, midY);
        } else if (indicatorStyle == IndicatorStyle.INDICATOR_DASH) {
            drawDash(canvas, midY);
        } else if (indicatorStyle == IndicatorStyle.INDICATOR_BIG_CIRCLE) {
            drawBigCircle(canvas, midY);
        }
    }

    private void drawCircle(Canvas canvas, float midY) {
        drawPagerCountCircle(canvas, midY);
        float indicatorStartX = indicatorStartX(selectedPage);
        float nextIndicatorStartX = indicatorStartX((selectedPage + 1) % pagerCount);
        float ratioRadius = getRatioSelectedRadius();
        float left = indicatorStartX - ratioRadius;
        float right = indicatorStartX + ratioRadius;
        float nextLeft = nextIndicatorStartX - ratioRadius;
        float nextRight = nextIndicatorStartX + ratioRadius;
        float leftX = left + (nextLeft - left) * interpolatedOffset();
        float rightX = right + (nextRight - right) * interpolatedOffset();
        rectF.set(leftX, midY - indicatorSelectedRadius, rightX, midY + indicatorSelectedRadius);
        indicatorPaint.setColor(selectedColor);
        canvas.drawRoundRect(rectF, indicatorSelectedRadius, indicatorSelectedRadius, indicatorPaint);
    }

    private void drawCircleRect(Canvas canvas, float midY) {
        drawPagerCountCircle(canvas, midY);
        float indicatorStartX = indicatorStartX(selectedPage);
        float ratioRadius = getRatioSelectedRadius();
        float left = indicatorStartX - ratioRadius;
        float right = indicatorStartX + ratioRadius;
        float offset = interpolatedOffset();
        float distance = indicatorSpacing + getRatioRadius() * 2;
        float leftX;
        float rightX;
        if ((selectedPage + 1) % pagerCount == 0) {
            distance *= -selectedPage;
            leftX = left + Math.max((distance * offset * 2), distance);
            rightX = right + Math.min(distance * (offset - 0.5f) * 2.0f, 0);
        } else {
            leftX = left + Math.max(distance * (offset - 0.5f) * 2.0f, 0);
            rightX = right + Math.min((distance * offset * 2), distance);
        }
        rectF.set(leftX, midY - indicatorSelectedRadius, rightX, midY + indicatorSelectedRadius);
        indicatorPaint.setColor(selectedColor);
        canvas.drawRoundRect(rectF, indicatorSelectedRadius, indicatorSelectedRadius, indicatorPaint);
    }

    private void drawBezier(Canvas canvas, float midY) {
        drawPagerCountCircle(canvas, midY);
        if (path == null) path = new Path();
        if (accelerateInterpolator == null) accelerateInterpolator = new AccelerateInterpolator();
        float indicatorStartX = indicatorStartX(selectedPage);
        float nextIndicatorStartX = indicatorStartX((selectedPage + 1) % pagerCount);
        float leftX = indicatorStartX + (nextIndicatorStartX - indicatorStartX) * accelerateInterpolator.getInterpolation(offset);
        float rightX = indicatorStartX + (nextIndicatorStartX - indicatorStartX) * interpolatedOffset();
        float ratioSelectedRadius = getRatioSelectedRadius();
        float minRadius = indicatorSelectedRadius * 0.57f;
        float minRatioRadius = minRadius * indicatorSelectedRatio;
        float leftRadius = ratioSelectedRadius + (minRatioRadius - ratioSelectedRadius) * interpolatedOffset();
        float rightRadius = minRatioRadius + (ratioSelectedRadius - minRatioRadius) * accelerateInterpolator.getInterpolation(offset);
        float leftTopOrBottomOffset = (indicatorSelectedRadius - minRadius) * interpolatedOffset();
        float rightTopOrBottomOffset = (indicatorSelectedRadius - minRadius) * accelerateInterpolator.getInterpolation(offset);
        indicatorPaint.setColor(selectedColor);
        rectF.set(leftX + leftRadius, midY - indicatorSelectedRadius + leftTopOrBottomOffset, leftX - leftRadius, midY + indicatorSelectedRadius - leftTopOrBottomOffset);
        canvas.drawRoundRect(rectF, leftRadius, leftRadius, indicatorPaint);
        rectF.set(rightX + rightRadius, midY - minRadius - rightTopOrBottomOffset, rightX - rightRadius, midY + minRadius + rightTopOrBottomOffset);
        canvas.drawRoundRect(rectF, rightRadius, rightRadius, indicatorPaint);
        path.reset();
        path.moveTo(rightX, midY);
        path.lineTo(rightX, midY - minRadius - rightTopOrBottomOffset);
        path.quadTo(rightX + (leftX - rightX) / 2.0f, midY, leftX, midY - indicatorSelectedRadius + leftTopOrBottomOffset);
        path.lineTo(leftX, midY + indicatorSelectedRadius - leftTopOrBottomOffset);
        path.quadTo(rightX + (leftX - rightX) / 2.0f, midY, rightX, midY + minRadius + rightTopOrBottomOffset);
        path.close();
        canvas.drawPath(path, indicatorPaint);
    }

    private void drawDash(Canvas canvas, float midY) {
        float offset = interpolatedOffset();
        //默认dash的长度，设置ratio控制长度
        float distance = (indicatorSelectedRadius * 2) * indicatorRatio;
        float distanceOffset = distance * offset;
        int nextPage = (selectedPage + 1) % pagerCount;
        boolean isNextFirst = nextPage == 0;
        indicatorPaint.setColor(unColor);
        for (int i = 0; i < pagerCount; i++) {
            float startCx = indicatorStartX(i);
            if (isNextFirst) startCx += distanceOffset;
            float ratioIndicatorRadius = getRatioRadius();
            float left = startCx - ratioIndicatorRadius;
            float top = midY - indicatorRadius;
            float right = startCx + ratioIndicatorRadius;
            float bottom = midY + indicatorRadius;
            if (selectedPage + 1 <= i) {
                rectF.set(left + distance, top, right + distance, bottom);
            } else {
                rectF.set(left, top, right, bottom);
            }
            canvas.drawRoundRect(rectF, indicatorRadius, indicatorRadius, indicatorPaint);
        }
        indicatorPaint.setColor(selectedColor);
        float ratioSelectedRadius = getRatioSelectedRadius();
        if (offset < 0.99f) {
            float leftX = indicatorStartX(selectedPage) - ratioSelectedRadius;
            if (isNextFirst) leftX += distanceOffset;
            float rightX = leftX + ratioSelectedRadius * 2 + distance - distanceOffset;
            rectF.set(leftX, midY - indicatorSelectedRadius, rightX, midY + indicatorSelectedRadius);
            canvas.drawRoundRect(rectF, indicatorSelectedRadius, indicatorSelectedRadius, indicatorPaint);
        }
        if (offset > 0.1f) {
            float nextRightX = indicatorStartX(nextPage) + ratioSelectedRadius + (isNextFirst ? distanceOffset : distance);
            float nextLeftX = nextRightX - ratioSelectedRadius * 2 - distanceOffset;
            rectF.set(nextLeftX, midY - indicatorSelectedRadius, nextRightX, midY + indicatorSelectedRadius);
            canvas.drawRoundRect(rectF, indicatorSelectedRadius, indicatorSelectedRadius, indicatorPaint);
        }
    }

    private void drawBigCircle(Canvas canvas, float midY) {
        drawPagerCountCircle(canvas, midY);
        float offset = interpolatedOffset();
        float indicatorStartX = indicatorStartX(selectedPage);
        float nextIndicatorStartX = indicatorStartX((selectedPage + 1) % pagerCount);
        float ratioRadius = getRatioRadius();
        float maxRadius = indicatorRadius == indicatorSelectedRadius ? indicatorSelectedRadius * 1.5f : indicatorSelectedRadius;
        float maxRatioRadius = maxRadius * indicatorSelectedRatio;
        float leftRadius = maxRatioRadius - ((maxRatioRadius - ratioRadius) * offset);
        float rightRadius = ratioRadius + ((maxRatioRadius - ratioRadius) * offset);
        float topOrBottomOffset = (maxRadius - indicatorRadius) * offset;
        indicatorPaint.setColor(selectedColor);
        if (offset < 0.99f) {
            float top = midY - maxRadius + topOrBottomOffset;
            float left = indicatorStartX - leftRadius;
            float right = indicatorStartX + leftRadius;
            float bottom = midY + maxRadius - topOrBottomOffset;
            rectF.set(left, top, right, bottom);
            canvas.drawRoundRect(rectF, leftRadius, leftRadius, indicatorPaint);
        }
        if (offset > 0.1f) {
            float top = midY - indicatorRadius - topOrBottomOffset;
            float left = nextIndicatorStartX - rightRadius;
            float right = nextIndicatorStartX + rightRadius;
            float bottom = midY + indicatorRadius + topOrBottomOffset;
            rectF.set(left, top, right, bottom);
            canvas.drawRoundRect(rectF, rightRadius, rightRadius, indicatorPaint);
        }
    }

    private void drawPagerCountCircle(Canvas canvas, float midY) {
        indicatorPaint.setColor(unColor);
        for (int i = 0; i < pagerCount; i++) {
            float startCx = indicatorStartX(i);
            float ratioIndicatorRadius = getRatioRadius();
            float left = startCx - ratioIndicatorRadius;
            float top = midY - indicatorRadius;
            float right = startCx + ratioIndicatorRadius;
            float bottom = midY + indicatorRadius;
            rectF.set(left, top, right, bottom);
            canvas.drawRoundRect(rectF, indicatorRadius, indicatorRadius, indicatorPaint);
        }
    }

    private float indicatorStartX(int index) {
        float ratioIndicatorRadius = getRatioRadius();
        float centerSpacing = ratioIndicatorRadius * 2.0f + indicatorSpacing;
        return ratioIndicatorRadius + getPaddingLeft() + centerSpacing * index;
    }

    private float getRatioRadius() {
        return indicatorRadius * indicatorRatio;
    }

    private float getRatioSelectedRadius() { return indicatorSelectedRadius * indicatorSelectedRatio; }

    private float interpolatedOffset() {
        return interpolator.getInterpolation(offset);
    }

    private int dip2px(float dp) { return (int) (dp * getContext().getResources().getDisplayMetrics().density); }

    /*--------------- 下面是暴露的方法 ---------------*/

    /**
     * 设置indicator的圆角，同时会改变选中时的圆角，default 3.5dp
     *
     * @param indicatorRadius 单位dp
     */
    public IndicatorView setIndicatorRadius(float indicatorRadius) {
        int indicatorRadiusDp = dip2px(indicatorRadius);
        if (this.indicatorRadius == this.indicatorSelectedRadius) {
            this.indicatorSelectedRadius = indicatorRadiusDp;
        }
        this.indicatorRadius = indicatorRadiusDp;
        return this;
    }

    /**
     * 设置indicator比例，拉伸圆为矩形，控制该比例，default 1.0
     * {@link IndicatorView#getRatioRadius()}
     *
     * @param indicatorRatio indicatorRadius * indicatorRatio
     */
    public IndicatorView setIndicatorRatio(float indicatorRatio) {
        if (this.indicatorRatio == this.indicatorSelectedRatio) {
            this.indicatorSelectedRatio = indicatorRatio;
        }
        this.indicatorRatio = indicatorRatio;
        return this;
    }

    /**
     * 设置选中的圆角，没有设置，默认和indicatorRadius值一致
     *
     * @param indicatorSelectedRadius 单位dp
     */
    public IndicatorView setIndicatorSelectedRadius(float indicatorSelectedRadius) {
        this.indicatorSelectedRadius = dip2px(indicatorSelectedRadius);
        return this;
    }

    /**
     * 设置选中圆比例，拉伸圆为矩形，控制该比例，默认比例和indicatorRatio一致
     *
     * @param indicatorSelectedRatio indicatorSelectedRadius * indicatorSelectedRatio
     */
    public IndicatorView setIndicatorSelectedRatio(float indicatorSelectedRatio) {
        this.indicatorSelectedRatio = indicatorSelectedRatio;
        return this;
    }

    /**
     * 设置点与点之间的距离，default dp10
     *
     * @param indicatorSpacing 单位dp
     */
    public IndicatorView setIndicatorSpacing(float indicatorSpacing) {
        this.indicatorSpacing = dip2px(indicatorSpacing);
        return this;
    }

    public IndicatorView setIndicatorStyle(@IndicatorStyle int indicatorStyle) {
        this.indicatorStyle = indicatorStyle;
        return this;
    }

    public IndicatorView setIndicatorColor(@ColorInt int indicatorColor) {
        this.unColor = indicatorColor;
        return this;
    }

    public IndicatorView setIndicatorSelectorColor(@ColorInt int indicatorSelectorColor) {
        this.selectedColor = indicatorSelectorColor;
        return this;
    }

    public IndicatorView setParams(RelativeLayout.LayoutParams params) {
        this.params = params;
        return this;
    }
}
