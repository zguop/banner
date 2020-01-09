package com.to.aboomy.bannersample.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * banner 背景容器
 */
public class BannerBgContainer extends RelativeLayout {

    private List<BannerBgView> bannerBgViews = new ArrayList<>();


    public BannerBgContainer(Context context) {
        super(context);
    }

    public BannerBgContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerBgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<BannerBgView> getBannerBgViews() {
        return this.bannerBgViews;
    }

    /**
     * 设置
     *
     * @param context
     * @param bgUrlList
     */
    public void setBannerBackBg(Context context, List<Object> bgUrlList) {
        bannerBgViews.clear();
        this.removeAllViews();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, UIUtil.dip2px(context, 240));
        layoutParams.leftMargin = -UIUtil.dip2px(context, 20);
        layoutParams.rightMargin = -UIUtil.dip2px(context, 20);
        for (Object urlImageView : bgUrlList) {
            BannerBgView bannerBgView = new BannerBgView(context);
            bannerBgView.setLayoutParams(layoutParams);
            Glide.with(context).load(urlImageView).into(bannerBgView.getImageView());
            bannerBgViews.add(bannerBgView);
            this.addView(bannerBgView);
        }
        bannerBgViews.get(0).bringToFront();
    }


}
