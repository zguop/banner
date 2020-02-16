package com.to.aboomy.bannersample.adapter;

import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.Pager2BannerBean;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.viewpager.creator.ImageHolderCreator;
import com.to.aboomy.bannersample.viewpager2.adapter.ImageAdapter;

/**
 * auth aboom
 * date 2019-08-27
 */
public class BannerAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public BannerAdapter() {
        super(null);
        addItemType(1, R.layout.item_view_banner);
        addItemType(2, R.layout.item_text);
        addItemType(3, R.layout.item_view_pager2_banner);
    }


    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateDefViewHolder(parent, viewType);
        if (viewType == 1) {
            Banner banner = baseViewHolder.getView(R.id.banner);
            IndicatorView indicatorView = new IndicatorView(mContext)
                    .setIndicatorColor(Color.BLACK)
                    .setIndicatorSelectorColor(Color.WHITE);
            banner.setIndicator(indicatorView)
                    .setHolderCreator(new ImageHolderCreator());
        }
        if (viewType == 3) {
            com.to.aboomy.pager2Banner.Banner banner = baseViewHolder.getView(R.id.banner);
            com.to.aboomy.pager2Banner.IndicatorView indicatorView = new com.to.aboomy.pager2Banner.IndicatorView(mContext)
                    .setIndicatorColor(Color.BLACK)
                    .setIndicatorSelectorColor(Color.WHITE);
            ImageAdapter imageAdapter = new ImageAdapter();
            banner.setIndicator(indicatorView)
                    .setAdapter(imageAdapter);
        }

        return baseViewHolder;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        if (item.getItemType() == 1) {
            BannerBean bannerBean = (BannerBean) item;
            Banner itemView = helper.getView(R.id.banner);
            itemView.setPages(bannerBean.urls, itemView.getCurrentPager());
            helper.setText(R.id.text, "我是recyclerView的一个item");
        } else if (item.getItemType() == 3) {
            Pager2BannerBean bannerBean = (Pager2BannerBean) item;
            com.to.aboomy.pager2Banner.Banner itemView = helper.getView(R.id.banner);
            ImageAdapter adapter = (ImageAdapter) itemView.getAdapter();
            adapter.replaceData(bannerBean.urls);
            helper.setText(R.id.text, "我是recyclerView的一个item");
        } else {
            TextBean textBean = (TextBean) item;
            helper.setText(R.id.text, textBean.text);
        }
    }
}
