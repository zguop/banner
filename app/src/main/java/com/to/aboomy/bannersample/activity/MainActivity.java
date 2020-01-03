package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private List<String> list = new ArrayList<>();
    private Banner banner;

    int style = IndicatorView.IndicatorStyle.INDICATOR_CIRCLE;
    private TextView noLoop;

    public int getListRandom() {
        return new Random().nextInt(list.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
        list.add(Utils.getRandom());
        list.add(Utils.getRandom());
        banner = findViewById(R.id.banner);
        final IndicatorView indicatorView = new IndicatorView(this)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE);
        banner.setIndicator(indicatorView)
                .setHolderCreator(new ImageHolderCreator())
                .setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                        Log.e("aa" , "onPageScrolled "  + position);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa" , "onPageSelected "  + position);

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
//                        Log.e("aa" , "onPageScrollStateChanged "  + state);

                    }
                })
                .setPages(list);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(Utils.getRandom());
                banner.setPages(list, banner.getCurrentPager());

                updateLoopText();
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    return;
                }
                int listRandom = getListRandom();
                Toast.makeText(MainActivity.this, "删除第" + listRandom + " 张", Toast.LENGTH_SHORT).show();
                list.remove(listRandom);
                banner.setPages(list, banner.getCurrentPager());

                updateLoopText();
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int size = list.size();
                list.clear();

                for (int i = 0; i < size; i++) {
                    list.add(Utils.getRandom());
                }

                banner.setPages(list, banner.getCurrentPager());

                updateLoopText();
            }
        });

        findViewById(R.id.updateStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (style == 0) {
                    style = IndicatorView.IndicatorStyle.INDICATOR_CIRCLE_RECT;
                } else if (style == 1) {
                    style = IndicatorView.IndicatorStyle.INDICATOR_BEZIER;
                } else if (style == 2) {
                    style = IndicatorView.IndicatorStyle.INDICATOR_CIRCLE;
                }
                indicatorView.setIndicatorStyle(style);
            }
        });

        noLoop = findViewById(R.id.noLoop);

        findViewById(R.id.noLoop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (banner.isAutoPlay()) {
                    banner.setAutoPlay(false);
                    updateLoopText();
                } else {
                    banner.setAutoPlay(true);
                    if(!banner.isAutoPlay()){
                        Toast.makeText(MainActivity.this, "轮播页数需要大于1", Toast.LENGTH_SHORT).show();
                    }
                    updateLoopText();
                }
            }
        });
    }

    private void  updateLoopText(){
        if(banner.isAutoPlay()){
            noLoop.setText("停止自动轮播");
        }else {
            noLoop.setText("开始自动轮播");
        }
    }
}
