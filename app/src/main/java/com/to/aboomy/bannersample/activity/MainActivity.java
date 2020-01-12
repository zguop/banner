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
import com.to.aboomy.bannersample.util.ArrayStringItemSelectDialog;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String[] INDICATOR_STR = {
            "INDICATOR_CIRCLE",
            "INDICATOR_CIRCLE_RECT",
            "INDICATOR_BEZIER",
            "INDICATOR_DASH",
            "INDICATOR_BIG_CIRCLE",
    };


    private List<String> list = new ArrayList<>();
    private Banner banner;

    int style;
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
                .setAutoPlay(false)
                .setHolderCreator(new ImageHolderCreator())
                .setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                        Log.e("aa" , "onPageScrolled "  + position);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa", "onPageSelected " + position);

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

        final TextView updateStyle = findViewById(R.id.updateStyle);
        updateStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ArrayStringItemSelectDialog(MainActivity.this)
                        .setValueStrings(Arrays.asList(INDICATOR_STR))
                        .setChoose(style)
                        .setOnItemClickListener(new ArrayStringItemSelectDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, String value) {
                                updateStyle.setText(value);
                                style = position;
                                indicatorView.setIndicatorStyle(style);
                            }
                        }).show();
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
                    if (!banner.isAutoPlay()) {
                        Toast.makeText(MainActivity.this, "轮播页数需要大于1", Toast.LENGTH_SHORT).show();
                    }
                    updateLoopText();
                }
            }
        });

        updateLoopText();


    }

    private void updateLoopText() {
        if (banner.isAutoPlay()) {
            noLoop.setText("停止自动轮播");
        } else {
            noLoop.setText("开始自动轮播");
        }
    }
}
