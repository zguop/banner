package com.to.aboomy.pager2;

import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class PageMarginTransFormer  implements ViewPager2.PageTransformer {
    private final int mMarginPx;

    /**
     * Creates a {@link PageMarginTransFormer}.
     *
     * @param marginPx non-negative margin
     */
    public PageMarginTransFormer(@Px int marginPx) {
        mMarginPx = marginPx;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        ViewPager2 viewPager = requireViewPager(page);

        float offset = mMarginPx * position;

        if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
            page.setTranslationX(ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL ? -offset : offset);
        } else {
            page.setTranslationY(offset);
        }
    }

    private ViewPager2 requireViewPager(@NonNull View page) {
        ViewParent parent = page.getParent();
        ViewParent parentParent = parent.getParent();

        if (parent instanceof RecyclerView && parentParent instanceof ViewPager2) {
            return (ViewPager2) parentParent;
        }

        throw new IllegalStateException(
                "Expected the page view to be managed by a ViewPager2 instance.");
    }
}
