package com.to.aboomy.pager2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * auth aboom
 * date 2020-01-13
 */
public interface Adapter {

    RecyclerView.ViewHolder onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType);

    int getRealCount();

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Object o);

}
