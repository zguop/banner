package com.to.aboomy.bannersample;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.to.aboomy.banner.QyPagerAdapter;

import java.util.List;

/**
 * auth aboom
 * date 2018/6/21
 */
public class BannerAdapter extends QyPagerAdapter<Integer> {

    public BannerAdapter(List<Integer> data) {
        super(data);
    }

    @Override
    protected View newView(final Context context, final int realPosition) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageResource(mData.get(realPosition));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,realPosition + "",Toast.LENGTH_LONG).show();
            }
        });
        return iv;
    }
}
