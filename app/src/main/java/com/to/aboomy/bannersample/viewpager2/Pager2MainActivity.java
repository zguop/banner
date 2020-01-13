package com.to.aboomy.bannersample.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager2.adapter.MyAdapter;
import com.to.aboomy.pager2.Adapter;
import com.to.aboomy.pager2.Banner;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * auth aboom
 * date 2020-01-13
 */
public class Pager2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager2);
        Banner banner = findViewById(R.id.banner);
        banner.setAutoPlay(true);

        MyAdapter adapter = new MyAdapter();
        final List<Integer> image = Utils.getImage(2);
        adapter.setData(image);
        banner.setAdapter(new Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyAdapter.PagerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
            }

            @Override
            public int getRealCount() {
                return image.size();
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, Object o) {
                MyAdapter.PagerHolder pagerHolder = (MyAdapter.PagerHolder) holder;
                Glide.with(holder.itemView.getContext())
                        .load(o)
                        .apply(new RequestOptions())
                        .into(pagerHolder.imageView);
                pagerHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort(position + "");
                    }
                });
            }
        })
                .setPages(image);


    }
}
