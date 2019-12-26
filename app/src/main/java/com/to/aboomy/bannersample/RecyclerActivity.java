package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

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
                int randomNum = Utils.getRandomNum();
                if(randomNum == 0){
                    randomNum = 1;
                }
                for (int i = 0; i < randomNum; i++) {
                    b.urls.add(Utils.getRandom());
                }
                adapter.setData(0, b);
            }
        });

    }

}
