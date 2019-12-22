package com.to.aboomy.banner;

import android.content.Context;
import android.view.View;

/**
 * auth aboom
 * date 2019-12-21
 */
public interface HolderCreator<T> {
    View createView(Context context);
}
