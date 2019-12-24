package com.to.aboomy.bannersample;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScalePageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2019-08-27
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String url1 = "http://img0.pconline.com.cn/pconline/1506/29/6638168_1754_thumb.jpg";
    private String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557327833941&di=4be9015403fd966f1a32711ade360ea4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F01%2F20171001105830_wWQej.jpeg";
    private String url3 = "http://pic1.win4000.com/wallpaper/2018-07-17/5b4ddcdb0f988.jpg";
    private String url4 = "http://img5.imgtn.bdimg.com/it/u=3048559810,1005075937&fm=26&gp=0.jpg";
    private String url5 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";
    private String url6 = "http://img5.imgtn.bdimg.com/it/u=2382354621,287796080&fm=26&gp=0.jpg";

    private List<String> list = new ArrayList<>();

    private List<String> list1 = new ArrayList<>();

    public Adapter() {
        list.add(url1);
        list.add(url2);
        list.add(url3);
        list.add(url4);
        list.add(url5);
        list.add(url6);

        list1.add(url1);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_banner, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Banner itemView = (Banner) viewHolder.itemView;
        IndicatorView qyIndicator = new IndicatorView(itemView.getContext())
                .setIndicatorColor(Color.BLACK)
                .setIndicatorInColor(Color.WHITE)
                .setGravity(Gravity.CENTER);
        itemView.setPageMargins(dp2px(30f), dp2px(10f), dp2px(30f), dp2px(20f), dp2px(10f));
        itemView.setPageTransformer(true, new ScalePageTransformer(0.8f));
        itemView.setIndicator(qyIndicator);
//        itemView.setAdapter(new BannerAdapter(list));
    }

    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
