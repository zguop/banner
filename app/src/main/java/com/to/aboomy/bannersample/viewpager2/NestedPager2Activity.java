package com.to.aboomy.bannersample.viewpager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager2.adapter.ImageAdapter;
import com.to.aboomy.pager2banner.Banner;

import java.util.Arrays;

/**
 * auth aboom
 * date 2020-03-04
 */
public class NestedPager2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_pager2);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new ViewPager2Adapter());
    }


    static class ViewPager2Adapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

        public ViewPager2Adapter() {
            super(R.layout.item_nested, Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW));
        }


        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder baseViewHolder = super.onCreateDefViewHolder(parent, viewType);
            final Banner banner = baseViewHolder.getView(R.id.banner);
            ImageAdapter imageAdapter = new ImageAdapter();
            imageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ToastUtils.showShort(String.valueOf(banner.getCurrentPager()));
                }
            });
            banner.setAdapter(imageAdapter);


            final Banner banner1 = baseViewHolder.getView(R.id.banner1);
            banner1.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            ImageAdapter imageAdapter1 = new ImageAdapter();
            imageAdapter1.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ToastUtils.showShort(String.valueOf(banner1.getCurrentPager()));
                }
            });
            banner1.setAdapter(imageAdapter1);
            return baseViewHolder;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Integer item) {
            helper.itemView.setBackgroundColor(item);
            Banner banner = helper.getView(R.id.banner);
            ImageAdapter adapter = (ImageAdapter) banner.getAdapter();
            adapter.replaceData(Utils.getImage(3));
            Banner banner1 = helper.getView(R.id.banner1);
            ImageAdapter adapter1 = (ImageAdapter) banner1.getAdapter();
            adapter1.replaceData(Utils.getImage(3));


        }
    }

}
