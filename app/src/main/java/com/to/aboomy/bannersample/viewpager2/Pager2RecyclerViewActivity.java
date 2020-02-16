package com.to.aboomy.bannersample.viewpager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.adapter.BannerAdapter;
import com.to.aboomy.bannersample.bean.Pager2BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager2.adapter.ImageAdapter;
import com.to.aboomy.pager2Banner.Banner;
import com.to.aboomy.pager2Banner.IndicatorView;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * auth aboom
 * date 2019-08-27
 */
public class Pager2RecyclerViewActivity extends AppCompatActivity {

    private BannerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
        RecyclerView recyclerView = findViewById(R.id.list);

        swipeRefreshLayout = findViewById(R.id.swipe);

        adapter = new BannerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        View inflate = View.inflate(this, R.layout.item_view_pager2_banner, null);
        TextView textView = inflate.findViewById(R.id.text);
        textView.setText("我是被addHeaderView添加进来的");
        adapter.addHeaderView(inflate);
        banner = inflate.findViewById(R.id.banner);
        banner.setIndicator(new IndicatorView(this).setIndicatorColor(Color.GRAY).setIndicatorSelectorColor(Color.WHITE));
        ImageAdapter imageAdapter = new ImageAdapter();
        imageAdapter.replaceData(Utils.getImage(4));
        banner.setAdapter(imageAdapter);


        loadData();

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pager2BannerBean b = (Pager2BannerBean) adapter.getItem(0);
                b.urls.clear();
                int i = new Random().nextInt(4) + 1;
                List<Integer> data = Utils.getImage(i);
                b.urls.addAll(data);
                adapter.setData(0, b);
            }
        });

    }

    private void loadData() {

        List<MultiItemEntity> list = new ArrayList<>();

        Pager2BannerBean bannerBean = new Pager2BannerBean();
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
