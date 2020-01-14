package com.to.aboomy.pager2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * auth aboom
 * date 2020-01-13
 */
public interface Adapter<H extends RecyclerView.ViewHolder> {
    RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);
    void onBindViewHolder(H holder, int position);
}
