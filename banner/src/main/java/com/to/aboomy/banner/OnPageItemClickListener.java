package com.to.aboomy.banner;

import android.view.View;

public interface OnPageItemClickListener {
    /**
     * 作为一个扩展接口！！！
     * 它做了一件简单的事情，帮itemView设置点击事件。但是目前来说在HolderCreator直接设置事件更加方便。
     * 注意：如果HolderCreator中itemVIew设置了view的事件，则会覆盖HolderCreator中ItemView的点击事件，子View不受影响
     * <p>
     * As an extension interface
     * It does one simple thing, it sets up the click event for the itemView. But for now it's easier to set up events directly in HolderCreator.
     * Note: if the itemVIew in HolderCreator sets the event of the view, the click event of the itemVIew in HolderCreator is overridden, and the sub-view is not affected
     *
     * @param view     item view
     * @param position current position
     */
    void onPageItemClick(View view, int position);
}
