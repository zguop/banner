package com.to.aboomy.bannersample;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * auth aboom
 * date 2018/6/21
 */
public class BannerAdapter extends com.to.aboomy.banner.BannerAdapter<String> {

    public BannerAdapter(List<String> data) {
        super(data);
    }

    @Override
    protected View newView(final Context context, final int realPosition, String t) {
        ImageView iv = new ImageView(context);
        iv.setPadding(80,80,80,80);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(iv).load(t).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, realPosition + "", Toast.LENGTH_LONG).show();
            }
        });
        return iv;
    }
}
