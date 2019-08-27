package com.to.aboomy.bannersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String       url1 = "http://img0.pconline.com.cn/pconline/1506/29/6638168_1754_thumb.jpg";
    private String       url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557327833941&di=4be9015403fd966f1a32711ade360ea4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F01%2F20171001105830_wWQej.jpeg";
    private String       url3 = "http://pic1.win4000.com/wallpaper/2018-07-17/5b4ddcdb0f988.jpg";
    private String       url4 = "http://img5.imgtn.bdimg.com/it/u=3048559810,1005075937&fm=26&gp=0.jpg";
    private String       url5 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private String       url6 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private List<String> list;
    private Banner       banner;

    private BannerAdapter bannerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        list.add(url1);
        list.add(url2);
        list.add(url3);
        list.add(url4);
        list.add(url5);
        list.add(url6);

        banner = findViewById(R.id.banner);
        bannerAdpter = new BannerAdapter(list);
        IndicatorView qyIndicator = new IndicatorView(this)
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        banner.setIndicator(qyIndicator);
        banner.setAdapter(bannerAdpter);

    }
}
