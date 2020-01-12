package com.to.aboomy.bannersample.util;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * auth aboom
 * date 2020-01-09
 */
public class ZoomOutSlideTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position >= -1 || position <= 1) {
            // Modify the default slide transition to shrink the page as well
            float height = page.getHeight();
            float width = page.getWidth();
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = height * (1 - scaleFactor) / 2;
            float horzMargin = width * (1 - scaleFactor) / 2;

            // Center vertically
            page.setPivotY(0.5f * height);
            page.setPivotX(0.5f * width);

            page.setTranslationX(position < 0? horzMargin - vertMargin / 2:
             -horzMargin + vertMargin / 2);

            page.setScaleY(scaleFactor);
            page.setScaleX(scaleFactor);

        }
    }
}
