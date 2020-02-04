package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.adapter.Adapter;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * auth aboom
 * date 2019-08-27
 */
public class RecyclerActivity extends AppCompatActivity {

    private Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
        RecyclerView recyclerView = findViewById(R.id.list);

        swipeRefreshLayout = findViewById(R.id.swipe);

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        View inflate = View.inflate(this, R.layout.item_view_banner, null);
        TextView textView = inflate.findViewById(R.id.text);
        textView.setText("我是被addHeaderView添加进来的");
        adapter.addHeaderView(inflate);
        banner = inflate.findViewById(R.id.banner);
        banner.setHolderCreator(new ImageHolderCreator());
        banner.setIndicator(new IndicatorView(this).setIndicatorColor(Color.GRAY).setIndicatorSelectorColor(Color.WHITE));
        banner.setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("aa" , " position " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setPages(Utils.getImage(4));
        loadData();

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerBean b = (BannerBean) adapter.getItem(0);
                b.urls.clear();
                int i = new Random().nextInt(5);
                if (i == 0) {
                    i = 3;
                }
                List<Integer> data = Utils.getImage(i);
                b.urls.addAll(data);
                adapter.setData(0, b);
            }
        });

    }

    private void loadData() {

        List<MultiItemEntity> list = new ArrayList<>();

        BannerBean bannerBean = new BannerBean();
        bannerBean.urls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerBean.urls.add(Utils.getRandomImage());
        }
        list.add(bannerBean);
        for (int i = 0; i < 100; i++) {
            TextBean textBean = new TextBean();
            textBean.text = "--- " + i;
            list.add(textBean);
        }
        adapter.replaceData(list);

        swipeRefreshLayout.setRefreshing(false);

    }

}
