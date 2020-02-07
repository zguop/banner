package com.to.aboomy.bannersample.viewpager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.ArrayStringItemSelectDialog;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager2.adapter.ImageAdapter;
import com.to.aboomy.pager2.Banner;
import com.to.aboomy.pager2.HolderRestLoader;
import com.to.aboomy.pager2.IndicatorView;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * auth aboom
 * date 2020-01-13
 */
public class Pager2MainActivity extends AppCompatActivity {

    private static final String[] INDICATOR_STR = {
            "INDICATOR_CIRCLE",
            "INDICATOR_CIRCLE_RECT",
            "INDICATOR_BEZIER",
            "INDICATOR_DASH",
            "INDICATOR_BIG_CIRCLE",
    };

    private Banner banner;
    private TextView noLoop;
    private int style;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager2);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        banner = findViewById(R.id.banner);
        final IndicatorView indicatorView = new IndicatorView(this)
//                .setIndicatorRatio(1.5f)
//                .setIndicatorSelectedRadius(4)
//                .setIndicatorRadius(5.5f)
//                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_BEZIER)
                .setIndicatorColor(Color.GRAY)
                .setIndicatorSelectorColor(Color.WHITE);

        banner.setAutoPlay(false)
                .setIndicator(indicatorView)
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setHolderRestLoader(new HolderRestLoader() {
                    @Override
                    public void onItemRestLoader(int position, boolean isRestItem) {

                    }
                })
                .setOuterPageChangeListener(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa", " wai onPageSelected " + position);
                    }
                })
        ;
        final ImageAdapter adapter = new ImageAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //onItemClick回调position是holder.getAdapterPosition()的，
                //holder.getAdapterPosition()是轮播中当前位置，可以通过banner.getCurrentPager()获取真实位置。
                ToastUtils.showShort(String.valueOf(banner.getCurrentPager()));
            }
        });
        adapter.addData(Utils.getRandom());
        adapter.addData(Utils.getRandom());
        banner.setAdapter(adapter);


        /*--------------- 下面是按钮点击事件 ---------------*/

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData(Utils.getRandom());
                updateLoopText();
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getData().size() == 0) {
                    return;
                }
                int listRandom = new Random().nextInt(adapter.getData().size());
                Toast.makeText(Pager2MainActivity.this, "删除第" + listRandom + " 张", Toast.LENGTH_SHORT).show();
                adapter.remove(listRandom);
                updateLoopText();
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Object> data = adapter.getData();
                final int size = data.size();
                data.clear();
                for (int i = 0; i < size; i++) {
                    data.add(Utils.getRandom());
                }
                adapter.replaceData(data);
                updateLoopText();
            }
        });

        final TextView updateStyle = findViewById(R.id.updateStyle);
        updateStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ArrayStringItemSelectDialog(Pager2MainActivity.this)
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
                        Toast.makeText(Pager2MainActivity.this, "轮播页数需要大于1", Toast.LENGTH_SHORT).show();
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
