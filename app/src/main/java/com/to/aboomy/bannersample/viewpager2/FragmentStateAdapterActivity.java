package com.to.aboomy.bannersample.viewpager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.SizeUtils;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager2.adapter.FragmentAdapter;
import com.to.aboomy.pager2banner.Banner;
import com.to.aboomy.pager2banner.IndicatorView;
import com.to.aboomy.pager2banner.ScaleInTransformer;

/**
 * auth aboom
 * date 2020/6/6
 */
public class FragmentStateAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_adapter);

        Banner banner = findViewById(R.id.banner);
        final IndicatorView indicatorView = new IndicatorView(this)
                .setIndicatorRatio(1f)
                .setIndicatorRadius(2f)
                .setIndicatorSelectedRatio(3)
                .setIndicatorSelectedRadius(2f)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE);


        FragmentAdapter fragmentAdapter = new FragmentAdapter(this);

        banner.setAutoPlay(false)
                .setIndicator(indicatorView)
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setPagerScrollDuration(800)
                .setPageMargin(SizeUtils.dp2px(40), SizeUtils.dp2px(10))
                .addPageTransformer(new ScaleInTransformer())


                .setOuterPageChangeListener(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa", "onPageSelected position " + position);
                    }
                })
                .setAdapter(fragmentAdapter);
        fragmentAdapter.addData(Utils.getImage(2));





    }
}
