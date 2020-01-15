package com.to.aboomy.pager2;

import android.view.View;
import android.view.ViewGroup;

public interface HolderCreator<T> {
    View onCreateView(ViewGroup parent);
    void onBindView(View itemView, int position, T item);
}
