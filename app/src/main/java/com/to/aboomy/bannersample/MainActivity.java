package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String       url  = "http://hbimg.b0.upaiyun.com/49c9355cf670b5f01a7dd750c40f6a2ee8fbf60d1bbb5-kdsjmn_fw658";
    private String       url1 = "http://img0.pconline.com.cn/pconline/1506/29/6638168_1754_thumb.jpg";
    private String       url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557327833941&di=4be9015403fd966f1a32711ade360ea4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F01%2F20171001105830_wWQej.jpeg";
    private String       url3 = "http://pic1.win4000.com/wallpaper/2018-07-17/5b4ddcdb0f988.jpg";
    private String       url4 = "http://img5.imgtn.bdimg.com/it/u=3048559810,1005075937&fm=26&gp=0.jpg";
    private String       url5 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private List<String> list;
    private Banner       banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        list.add(url);
        list.add(url1);
        list.add(url2);
        list.add(url3);
        list.add(url5);

        banner = findViewById(R.id.banner);
//        banner.setPageMargins(40, 40, 40, 40, 20);
//        banner.setPageTransformer(true, new ScalePageTransformer(0.8f));
        final BannerAdapter bannerAdpter = new BannerAdapter(list);
        IndicatorView qyIndicator = new IndicatorView(this)
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        banner.setIndicator(qyIndicator);
        banner.setAdapter(bannerAdpter);

        findViewById(R.id.view1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add("http://img5.imgtn.bdimg.com/it/u=2249778075,2766567468&fm=26&gp=0.jpg");
                bannerAdpter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.view2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.add(url1);
                list.add(url2);

                bannerAdpter.notifyDataSetChanged();

            }
        });
        findViewById(R.id.view3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                bannerAdpter.notifyDataSetChanged();
            }
        });
    }

    private void setAdapter() {

    }
}
