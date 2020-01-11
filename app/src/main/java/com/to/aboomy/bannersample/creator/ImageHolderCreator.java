package com.to.aboomy.bannersample.creator;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.to.aboomy.banner.HolderCreator;

/**
 * auth aboom
 * date 2019-12-26
 */
public class ImageHolderCreator implements HolderCreator {
    @Override
    public View createView(final Context context, final int index, Object o) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(iv).load(o).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(index + "");
            }
        });
        return iv;
    }
}
