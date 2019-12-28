package com.to.aboomy.banner;

import android.content.Context;
import android.view.View;

public interface HolderCreator {
    View createView(final Context context,final int index, Object o);
}
