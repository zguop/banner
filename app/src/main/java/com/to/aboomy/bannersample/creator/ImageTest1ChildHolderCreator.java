package com.to.aboomy.bannersample.creator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.bannersample.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

/**
 * auth aboom
 * date 2019-12-28
 */
public class ImageTest1ChildHolderCreator implements HolderCreator {
    @NonNull
    @Override
    public View createView(final Context context, final int index, Object o) {
        View inflate = View.inflate(context, R.layout.item_round_image, null);
        ImageView imageView = inflate.findViewById(R.id.img);
        Glide.with(context)
                .load(o)
                .apply(new RequestOptions().transform(new RoundedCorners(UIUtil.dip2px(context, 12))))
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(index + "");
            }
        });
        return inflate;
    }
}
