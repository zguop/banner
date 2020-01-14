package com.to.aboomy.bannersample.viewpager2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.banner.IndicatorView;
import com.to.aboomy.banner.ScaleInTransformer;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.util.Utils;
import com.to.aboomy.bannersample.viewpager2.adapter.MyAdapter;
import com.to.aboomy.pager2.Banner;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * auth aboom
 * date 2020-01-13
 */
public class Pager2MainActivity extends AppCompatActivity implements HolderCreator {


    private boolean is = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager2);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        final Banner banner = findViewById(R.id.banner);
        banner.setAutoPlay(true)
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
//                .setPageTransformer(new com.to.aboomy.pager2.ScaleInTransformer())
//                .setPageMargin(SizeUtils.dp2px(20), SizeUtils.dp2px(14))
        ;
        final MyAdapter adapter = new MyAdapter();
        List<String> data = Utils.getData(2);


        final List<Integer> image = Utils.getImage(2);
        adapter.setData(image);
        banner.setAdapter(adapter);
//                .setAdapter(new Adapter() {
//                    @Override
//                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        return new MyAdapter.PagerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_image, parent, false));
//                    }
//
//                    @Override
//                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//                        MyAdapter.PagerHolder pagerHolder = (MyAdapter.PagerHolder) holder;
//                        Glide.with(holder.itemView.getContext())
//                                .load(image.get(position))
//                                .apply(new RequestOptions()
//                                        .transform(new RoundedCorners(UIUtil.dip2px(Pager2MainActivity.this, 10))))
//                                .into(pagerHolder.imageView);
//                        pagerHolder.imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ToastUtils.showShort(position + "");
//                            }
//                        });
//                    }
//                })
//                .setPages(image);


        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                adapter.setData(Utils.getImage(4));


            }
        });


        findViewById(R.id.update2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MyAdapter myAdapter = new MyAdapter();
                    myAdapter.setData(Utils.getImage(5));
                    banner.setAdapter(myAdapter);
                }
        });

        initBanner1();


    }

    private void initBanner1() {
        com.to.aboomy.banner.Banner banner = findViewById(R.id.banner1);
        banner.setHolderCreator(this)
                .setOuterPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        Log.e("aa", "==================== banner1 onPageSelected " + i);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                })
                .setIndicator(new IndicatorView(this)
                        .setIndicatorColor(Color.GRAY)
                        .setIndicatorSelectorColor(Color.WHITE))
                .setPageMargin(UIUtil.dip2px(this, 20), UIUtil.dip2px(this, 14))
                .setPageTransformer(true, new ScaleInTransformer())

                .setPages(Utils.getImage(2));
    }


    @Override
    public View createView(final Context context, final int index, Object o) {
        View view = View.inflate(context, R.layout.item_banner_image, null);
        ImageView image = view.findViewById(R.id.img);
        Glide.with(image).load(o).apply(new RequestOptions().transform(new RoundedCorners(UIUtil.dip2px(this, 10)))).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, index + "", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
