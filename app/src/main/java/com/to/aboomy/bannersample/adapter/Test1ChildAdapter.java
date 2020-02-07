package com.to.aboomy.bannersample.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.viewpager.creator.ImageTest1ChildHolderCreator;


/**
 * auth aboom
 * date 2019-08-27
 */
public class Test1ChildAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public Test1ChildAdapter() {
        super(null);
        addItemType(1, R.layout.item_view_banner);
        addItemType(2, R.layout.item_text);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        if (item.getItemType() == 1) {
            BannerBean bannerBean = (BannerBean) item;
            Banner itemView = helper.getView(R.id.banner);
            IndicatorView qyIndicator = new IndicatorView(mContext)
                    .setIndicatorColor(Color.BLACK)
                    .setIndicatorSelectorColor(Color.WHITE);
            itemView.setIndicator(qyIndicator)
                    .setHolderCreator(new ImageTest1ChildHolderCreator());
            itemView.setPages(bannerBean.urls, itemView.getCurrentPager());
        } else {
            TextBean textBean = (TextBean) item;
            helper.setText(R.id.text, textBean.text);
        }
    }
}
