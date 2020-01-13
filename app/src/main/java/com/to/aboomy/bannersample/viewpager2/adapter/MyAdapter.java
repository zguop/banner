package com.to.aboomy.bannersample.viewpager2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.pager2.Adapter;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2020-01-13
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Adapter {

    private List<Integer> data = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return onCreateDefViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, position, data.get(position));
    }

    @Override
    public int getItemCount() {
        return getRealCount();
    }

    public void setData(List<Integer> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_round_image, parent, false));
    }

    @Override
    public int getRealCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, Object o) {
        PagerHolder pagerHolder = (PagerHolder) holder;
        Glide.with(context)
                .load(data.get(position))
                .apply(new RequestOptions().transform(new RoundedCorners(UIUtil.dip2px(context, 12))))
                .into(pagerHolder.imageView);
        pagerHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(position + "");
            }
        });
    }

   public static class PagerHolder extends RecyclerView.ViewHolder {
     public    ImageView imageView;

        public PagerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
