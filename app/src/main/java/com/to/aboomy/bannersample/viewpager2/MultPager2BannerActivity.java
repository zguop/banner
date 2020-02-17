package com.to.aboomy.bannersample.viewpager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.pager2banner.Banner;
import com.to.aboomy.pager2banner.IndicatorView;
import com.to.aboomy.pager2banner.ScaleInTransformer;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * auth aboom
 * date 2020-02-13
 */
public class MultPager2BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_pager2);
        initBanner1();
        initBanner2();
    }


    private void initBanner1() {
        Banner banner = findViewById(R.id.banner1);
        banner.setIndicator(new IndicatorView(this)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE))
                .setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10))
                .setOuterPageChangeListener(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa", "initBanner1 onPageSelected " + position);

                    }
                })
                .setAdapter(new ImageAdapter(Utils.getImage(2)));
    }

    private void initBanner2() {
        Banner banner = findViewById(R.id.banner2);
        banner.setAutoTurningTime(3000)
                .setIndicator(((IndicatorView) findViewById(R.id.indicator2))
                        .setIndicatorColor(Color.GRAY)
                        .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_BEZIER)
                        .setIndicatorSelectorColor(Color.RED), false)
                .setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 10))
                .setPageTransformer(new ScaleInTransformer())
                .setOuterPageChangeListener(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa", "initBanner2 onPageSelected " + position);
                    }
                })
                .setAdapter(new ImageAdapter(Utils.getImage(2)));
    }


    static class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Integer> items;

        ImageAdapter(List<Integer> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Glide.with(imageViewHolder.image)
                    .load(items.get(position))
                    .apply(new RequestOptions()
                            .transform(new RoundedCorners(SizeUtils.dp2px(10))))
                    .into(imageViewHolder.image);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
        }
    }
}
