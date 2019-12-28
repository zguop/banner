package com.to.aboomy.bannersample.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.bean.BannerBean;
import com.to.aboomy.bannersample.bean.BannerBean2;
import com.to.aboomy.bannersample.bean.TextBean;
import com.to.aboomy.bannersample.creator.ImageTest1ChildHolderCreator;
import com.youth.banner.loader.ImageLoaderInterface;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

/**
 * auth aboom
 * date 2019-08-27
 */
public class Test1ChildAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public Test1ChildAdapter() {
        super(null);
        addItemType(1, R.layout.item_view_banner);
        addItemType(2, R.layout.item_text);
        addItemType(3, R.layout.item_view_banner2);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        if (item.getItemType() == 1) {
            BannerBean bannerBean = (BannerBean) item;
            Banner itemView = (Banner) helper.itemView;
            IndicatorView qyIndicator = new IndicatorView(itemView.getContext())
                    .setIndicatorColor(Color.BLACK)
                    .setIndicatorSelectorColor(Color.WHITE);
            itemView.setIndicator(qyIndicator)
                    .setHolderCreator(new ImageTest1ChildHolderCreator())
                    .setPages(bannerBean.urls, itemView.getCurrentPosition());
        } else if (item.getItemType() == 3) {
            BannerBean2 bannerBean2 = (BannerBean2) item;
            com.youth.banner.Banner itemView = (  com.youth.banner.Banner) helper.itemView;
            itemView.setImageLoader(new ImageLoaderInterface() {
                @Override
                public void displayImage(final Context context, Object path, View inflate) {
                    ImageView imageView = inflate.findViewById(R.id.img);
                    Glide.with(context)
                            .load(path)
                            .apply(new RequestOptions().transform(new RoundedCorners(UIUtil.dip2px(context, 12))))
                            .into(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,   "点击了。", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public View createImageView(Context context) {
                    View inflate = View.inflate(context, R.layout.item_round_image, null);
                    return inflate;
                }
            });
            itemView.setDelayTime(2500);
            itemView.setImages(bannerBean2.urls);
            itemView.start();
        } else {
            TextBean textBean = (TextBean) item;
            helper.setText(R.id.text, textBean.text);
        }
    }
}
