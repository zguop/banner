package com.to.aboomy.banner;

import android.view.View;

public interface HolderRestLoader {
    /**
     * viewPager切换页面时调用，当页面切换时需要更新UI，可以设置该接口
     *
     * @param view       当前的view
     * @param position   当前的position
     * @param o          当前数据
     * @param isRestItem 是否是ViewPager重置当前位置，
     *                   也就是当滑动的最后一页时或者是滑动到第一页，通过setCurrentItem(position,false)重新设置ViewPager的位置时。
     *                   每当发生ViewPager重写设置当前位置时，isRestItem = true
     */
    void onItemRestLoader(View view, int position, Object o, boolean isRestItem);
}
