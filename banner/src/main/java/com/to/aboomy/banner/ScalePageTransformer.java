package com.to.aboomy.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ScalePageTransformer implements ViewPager.PageTransformer {

    private float scaleMax;

    public ScalePageTransformer(float scale) {
        this.scaleMax = scale;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        float SCALE_MAX = scaleMax;
        float scale = (position < 0)
                ? ((1 - SCALE_MAX) * position + 1)
                : ((SCALE_MAX - 1) * position + 1);
        if (position < 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight() >> 1);
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getHeight() >> 1);
        }
        page.setScaleX(scale);
        page.setScaleY(scale);
    }
}
