package com.to.aboomy.bannersample.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.OnPageItemClickListener;
import com.to.aboomy.banner.ScaleInTransformer;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager.creator.ImageHolderCreator;

import java.util.Arrays;
import java.util.List;

/**
 * auth aboom
 * date 2020/5/13
 */
public class NestedPagerActivity extends AppCompatActivity {

    final List<Integer> integers = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_pager);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());

    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return integers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_nested, container, false);
            inflate.setBackgroundColor(integers.get(position));
            Banner banner = inflate.findViewById(R.id.banner);
            banner.setHolderCreator(new ImageHolderCreator())
                    .setAutoTurningTime(3000)
                    .setPageMargin(SizeUtils.dp2px(20), SizeUtils.dp2px(10))
                    .setPageTransformer(true, new ScaleInTransformer())
                    .setOnPageClickListener(new OnPageItemClickListener() {
                        @Override
                        public void onPageItemClick(View view, int position) {
                            ToastUtils.showShort(String.valueOf(position));
                        }
                    })
                    .setPages(Utils.getImage(3));


            Banner banner1 = inflate.findViewById(R.id.banner1);
            banner1.setHolderCreator(new ImageHolderCreator())
                    .setAutoTurningTime(3000)
                    .setOnPageClickListener(new OnPageItemClickListener() {
                        @Override
                        public void onPageItemClick(View view, int position) {
                            ToastUtils.showShort(String.valueOf(position));
                        }
                    })
                    .setPages(Utils.getImage(3));
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
