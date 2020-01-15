package com.to.aboomy.pager2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BannerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> items;
    private HolderCreator<T> holderCreator;

    BannerAdapter(HolderCreator<T> holderCreator) {
        items = new ArrayList<>();
        this.holderCreator = holderCreator;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(holderCreator.onCreateView(parent)) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holderCreator.onBindView(holder.itemView,position,items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        // 不是同一个引用才清空列表
        if (data != items) {
            items.clear();
            items.addAll(data);
        }
        notifyDataSetChanged();
    }
}
