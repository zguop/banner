package com.to.aboomy.bannersample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //    private String       url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557327833941&di=4be9015403fd966f1a32711ade360ea4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F01%2F20171001105830_wWQej.jpeg";
    private String url4 = "http://img5.imgtn.bdimg.com/it/u=3048559810,1005075937&fm=26&gp=0.jpg";
    private String url5 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private String url6 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private List<String> list;
    private Banner banner;


    public static final String url1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3612267661,1332194385&fm=26&gp=0.jpg";
    public static final String url2 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2076697566,2577708113&fm=26&gp=0.jpg";
    private List<String> list2;

//    private BannerAdapter bannerAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
//        list.add(url2);
        list.add(url4);
//        list.add(url5);
        list.add(url6);

        list2 = new ArrayList<>();
        list2.add(url1);
//        list2.add(url2);
//        list2.add(url5);

        banner = findViewById(R.id.banner);
//        bannerAdpter = new BannerAdapter(list);
        IndicatorView qyIndicator = new IndicatorView(this)
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        banner.setIndicator(qyIndicator).setHolderCreator(new HolderCreator() {
            @Override
            public View createView(final Context context, final int index, Object o) {

                ImageView iv = new ImageView(context);
                iv.setPadding(80, 80, 80, 80);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(iv).load(o).into(iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Toast.makeText(context, index + "", Toast.LENGTH_LONG).show();

                    }
                });
                return iv;
            }
        }).setPages(null);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setPages(list, banner.getCurrentPosition());
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banner.setPages(list2);
            }
        });

    }
}
