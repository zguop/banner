package com.to.aboomy.bannersample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.adapter.Adapter;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
        RecyclerView recyclerView = findViewById(R.id.list);

        final Adapter adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<MultiItemEntity> list = new ArrayList<>();

        BannerBean bannerBean = new BannerBean();
        bannerBean.urls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerBean.urls.add(Utils.getRandom());
        }
        list.add(bannerBean);

        for (int i = 0; i < 100; i++) {
            TextBean textBean = new TextBean();
            textBean.text = "--- " + i;
            list.add(textBean);
        }
        adapter.addData(list);


        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerBean b = (BannerBean) adapter.getItem(0);
                b.urls.clear();
                List<String> data = Utils.getData(new Random().nextInt(10));
                b.urls.addAll(data);
                adapter.setData(0, b);
            }
        });

    }

}
