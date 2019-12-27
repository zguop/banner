package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private List<String> list = new ArrayList<>();
    private Banner banner;

    int style = IndicatorView.IndicatorStyle.INDICATOR_CIRCLE;

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
        final IndicatorView qyIndicator = new IndicatorView(this)
                .setIndicatorColor(Color.DKGRAY)
                .setIndicatorSelectorColor(Color.WHITE);
        banner.setIndicator(qyIndicator).setHolderCreator(new ImageHolderCreator()).setPages(list);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(Utils.getRandom());
                banner.setPages(list, banner.getCurrentPosition());
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
                banner.setPages(list, banner.getCurrentPosition());
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

                banner.setPages(list, banner.getCurrentPosition());
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
                qyIndicator.setIndicatorStyle(style);
            }
        });
    }
}
